package com.example.services;

import java.util.List;

import com.example.entities.Empleado;
import com.example.entities.Telefono;

public interface TelefonoService {
    List<Telefono> findAll();
    Telefono findById(int idTelefono);
    void save(Telefono telefono);
    void deleteById(int idTelefono);
    void deleteByEmpleado(Empleado empleado);
    List<Telefono> findByEmpleado(Empleado empleado);
}
