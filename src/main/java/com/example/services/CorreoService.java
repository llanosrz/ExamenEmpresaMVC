package com.example.services;

import java.util.List;

import com.example.entities.Correo;
import com.example.entities.Empleado;

public interface CorreoService {
    List<Correo> findAll();
    Correo findById(int idCorreo);
    void save(Correo correo);
    void deleteById(int idCorreo);
    void deleteByEmpleado(Empleado empleado);
    List<Correo> findByEmpleado(Empleado empleado);
}
