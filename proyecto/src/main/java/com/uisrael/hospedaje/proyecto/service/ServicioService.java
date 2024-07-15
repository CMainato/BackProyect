package com.uisrael.hospedaje.proyecto.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uisrael.hospedaje.proyecto.entity.Servicio;
import com.uisrael.hospedaje.proyecto.repository.IServicioRepository;

@Service
public class ServicioService {

	@Autowired
	private IServicioRepository servicioRepository;
	
	//listar servicios
	public List<Servicio> getAllServicios(){
		return servicioRepository.findAll();
	}
	
	//obtencion de servicio por id
	public Optional<Servicio> getServicioById(Long id){
		return servicioRepository.findById(id);
	}
	
	//guardar servicio
	public Servicio saveServicio(Servicio servicio) {
		return servicioRepository.save(servicio);
	}
	
	//eliminar servicio
	public void deleteServicio(Long id) {
		servicioRepository.deleteById(id);
	}
}
