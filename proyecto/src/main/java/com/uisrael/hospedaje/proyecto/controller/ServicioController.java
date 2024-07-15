package com.uisrael.hospedaje.proyecto.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.uisrael.hospedaje.proyecto.entity.Servicio;
import com.uisrael.hospedaje.proyecto.service.ServicioService;

@RestController
@RequestMapping("/api/v1/hospedaje/servicio")
public class ServicioController {

	@Autowired
	private ServicioService servicioService;
	
	@GetMapping
	public List<Servicio> getAllServicios(){
		return servicioService.getAllServicios();
	}
	
	@GetMapping("/{id}")
	public Optional<Servicio> getTServicioById(@PathVariable("id") Long id) {
        return servicioService.getServicioById(id);
    }
	
	@PostMapping
    public Servicio createServicio(@RequestBody Servicio servicio) {
        return servicioService.saveServicio(servicio);
    }

    @DeleteMapping("/{id}")
    public void deleteServicio(@PathVariable("id") Long id) {
    	servicioService.deleteServicio(id);
    }
	
	
	
	
	
}
