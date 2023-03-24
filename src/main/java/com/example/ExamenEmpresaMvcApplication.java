package com.example;

import java.time.LocalDate;
import java.time.Month;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.entities.Correo;
import com.example.entities.Departamento;
import com.example.entities.Empleado;
import com.example.entities.Telefono;
import com.example.entities.Empleado.Genero;
import com.example.services.CorreoService;
import com.example.services.DepartamentoService;
import com.example.services.EmpleadoService;
import com.example.services.TelefonoService;

@SpringBootApplication
public class ExamenEmpresaMvcApplication implements CommandLineRunner {

	@Autowired
	private EmpleadoService empleadoService;

	@Autowired
	private DepartamentoService departamentoService;

	@Autowired
	private TelefonoService telefonoService;

	@Autowired
	private CorreoService correoService;

	
	public static void main(String[] args) {
		SpringApplication.run(ExamenEmpresaMvcApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		// Agregamos registros de muestra para Departamento, Empleado, Teléfono y Correo

		departamentoService.save(
			Departamento.builder()
			.nombre("RRHH")
			.build()
		);

		departamentoService.save(
			Departamento.builder()
			.nombre("Informática")
			.build()
		);

		empleadoService.save(
			Empleado.builder()
			.nombre("Llanos")
			.apellidos("Ruiz Moreno")
			.genero(Genero.MUJER)
			.fechaAlta(LocalDate.of(2023, Month.FEBRUARY, 23))
			.departamento(departamentoService.findById(1))
			.build()
		);

		telefonoService.save(
			Telefono.builder()
			.id(1)
			.numero("968654785")
			.empleado(empleadoService.findById(1))
			.build()
		);

		telefonoService.save(
			Telefono.builder()
			.id(2)
			.numero("678987654")
			.empleado(empleadoService.findById(1))
			.build()
		);

		correoService.save(
			Correo.builder()
			.id(1)
			.email("llanos@gmail.com")
			.empleado(empleadoService.findById(1))
			.build()
		);

	}

}
