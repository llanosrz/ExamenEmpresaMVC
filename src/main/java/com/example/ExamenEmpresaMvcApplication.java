package com.example;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.dao.TelefonoDao;
import com.example.services.CorreoService;
import com.example.services.DepartamentoService;
import com.example.services.EmpleadoService;
import com.example.services.TelefonoService;

@SpringBootApplication
public class ExamenEmpresaMvcApplication implements CommandLineRunner {

	private EmpleadoService empleadoService;

	private DepartamentoService departamentoService;

	private TelefonoService telefonoService;

	private CorreoService correoService;

	
	public static void main(String[] args) {
		SpringApplication.run(ExamenEmpresaMvcApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		


	}

}
