package com.example.controllers;

import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.ui.Model;

import com.example.entities.Correo;
import com.example.entities.Departamento;
import com.example.entities.Empleado;
import com.example.entities.Telefono;
import com.example.services.CorreoService;
import com.example.services.DepartamentoService;
import com.example.services.EmpleadoService;
import com.example.services.TelefonoService;


@Controller
@RequestMapping("/")
public class MainController {
    
    private static final Logger LOG = Logger.getLogger("MainController");

    @Autowired
    private EmpleadoService empleadoService;

    @Autowired
    private DepartamentoService departamentoService;

    @Autowired
    private TelefonoService telefonoService;

    @Autowired
    private CorreoService correoService;

    //Listar empleados

    @GetMapping("/listar")
    public ModelAndView listar() {

        List<Empleado> empleados = empleadoService.findAll();
        
        ModelAndView mav = new ModelAndView("views/listarEmpleados");
        mav.addObject("empleados", empleados);

        return mav;
    }

    // Formulario de alta de un Empleado

    @GetMapping("/frmAltaEmpleado")
    public String formularioAltaEmpleado(Model model) {

        List<Departamento> departamentos = departamentoService.findAll();

        Empleado empleado = new Empleado();

        model.addAttribute("empleado", empleado);

        model.addAttribute("departamentos", departamentos);

        return "views/formularioAltaEmpleado";
    
    }

    // Recepción de los datos de los controles del formulario

    @PostMapping("/altaModificacionEmpleado")
    public String altaEmpleado(@ModelAttribute Empleado empleado,
                @RequestParam(name = "numerosTelefonos") String telefonosRecibidos,
                @RequestParam(name = "direccionesCorreos") String correosRecibidos) {

                    LOG.info("telefonos recibidos" + telefonosRecibidos);

                    LOG.info("correos recibidos" + correosRecibidos);

                    empleadoService.save(empleado);

                    List<String> listadoNumerosTelefonos = null;

                    if(telefonosRecibidos != null){
                        String[] arrayTelefonos = telefonosRecibidos.split(";");

                        listadoNumerosTelefonos = Arrays.asList(arrayTelefonos);
                    }

                    if(listadoNumerosTelefonos != null) {
                        //Borrar telefonos de empleado si hay que insertar nuevos

                        telefonoService.deleteByEmpleado(empleado);
                        listadoNumerosTelefonos.stream().forEach(n -> {
                                    Telefono telefonoObject = Telefono
                            .builder()
                            .numero(n)
                            .empleado(empleado)
                            .build();

                            telefonoService.save(telefonoObject);
                        });
                    }

                    List<String> listadoDireccionesCorreos = null;

                    if(correosRecibidos != null){
                        String[] arrayCorreos = correosRecibidos.split(";");

                        listadoDireccionesCorreos = Arrays.asList(arrayCorreos);
                    }

                    if(listadoDireccionesCorreos != null) {
                        //Borrar correos de empleado si hay que insertar nuevos

                        correoService.deleteByEmpleado(empleado);
                        listadoDireccionesCorreos.stream().forEach(n -> {
                                    Correo correoObject = Correo
                            .builder()
                            .email(n)
                            .empleado(empleado)
                            .build();

                            correoService.save(correoObject);
                        });
                    }

                    return "redirect:/listar";

                }

/**
 * Muestra el formulario para Actualizar un Empleado
 */
                                
 @GetMapping("/frmActualizar/{id}")
 public String frmActualizarEmpleado(@PathVariable(name = "id") int idEmpleado, Model model){

    Empleado empleado = empleadoService.findById(idEmpleado);

    List<Telefono> todosTelefonos = telefonoService.findAll();
    List<Telefono> telefonosDelEmpleado = todosTelefonos.stream()
    .filter(telefono -> telefono.getEmpleado().getId() == idEmpleado)
    .collect(Collectors.toList());

    String numerosDeTelefono = telefonosDelEmpleado.stream()
    .map(telefono -> telefono.getNumero())
    .collect(Collectors.joining(";"));

    List<Correo> todosCorreos = correoService.findAll();
    List<Correo> correosDelEmpleado = todosCorreos.stream()
    .filter(correo -> correo.getEmpleado().getId() == idEmpleado)
    .collect(Collectors.toList());

    String direccionesDeCorreo = correosDelEmpleado.stream()
    .map(correo -> correo.getEmail())
    .collect(Collectors.joining(";"));

    List<Departamento> departamentos = departamentoService.findAll();

    model.addAttribute("empleado", empleado);
    model.addAttribute("telefonos", numerosDeTelefono);
    model.addAttribute("correos", direccionesDeCorreo);
    model.addAttribute("departamentos", departamentos);

    return "views/formularioAltaEmpleado";

 }

 @GetMapping("/borrar/{id}")
 public String borrarEmpleado(@PathVariable(name = "id") int idEmpleado){

    empleadoService.delete(empleadoService.findById(idEmpleado));

    return "redirect:/listar";
 }


 @GetMapping("/detalles/{id}")
 public String detallesEmpleado(@PathVariable(name = "id") int id, Model model){

    Empleado empleado = empleadoService.findById(id);

    List<Telefono> telefonos = telefonoService.findByEmpleado(empleado);

    List<Correo> correos = correoService.findByEmpleado(empleado);

    List<String> numerosTelefono = telefonos.stream()
        .map(t -> t.getNumero()).toList();

    List<String> direccionesCorreo = correos.stream()
    .map(c -> c.getEmail()).toList();

    model.addAttribute("telefonos", numerosTelefono);
    model.addAttribute("correos", direccionesCorreo);
    model.addAttribute("empleado", empleado);

    return "views/detallesEmpleado";
 }
}
