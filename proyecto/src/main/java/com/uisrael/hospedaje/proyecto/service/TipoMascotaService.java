package com.uisrael.hospedaje.proyecto.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uisrael.hospedaje.proyecto.entity.TipoMascota;
import com.uisrael.hospedaje.proyecto.repository.ITipoMascotaRepository;

@Service
public class TipoMascotaService {

	@Autowired
	private ITipoMascotaRepository tipoMascotaRepository;
	
	//listar tipos de mascota 
	public List<TipoMascota> getAllTiposMascota(){
		return tipoMascotaRepository.findAll();
	}
	
	//obtencion de tipos de cliente por id
	public Optional<TipoMascota> getTipoMascotaById(Long id){
		return tipoMascotaRepository.findById(id);
	}
	
	//guardar tipo mascota 
	public TipoMascota saveTipoMascota(TipoMascota tipoMascota) {
		return tipoMascotaRepository.save(tipoMascota);
	}
	
	//eliminar tipo mascota
	public void deteTipoMascota(Long id) {
		tipoMascotaRepository.deleteById(id);
	}
}
