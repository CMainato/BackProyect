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

import com.uisrael.hospedaje.proyecto.entity.TipoMascota;
import com.uisrael.hospedaje.proyecto.service.TipoMascotaService;

@RestController
@RequestMapping("/api/v1/hospedaje/tipo_mascota")
public class TipoMascotaController {

	
	@Autowired
	private TipoMascotaService tipoMascotaService;
	
	@GetMapping
	public List<TipoMascota> getAllTipoMascotas(){
		return tipoMascotaService.getAllTiposMascota();
	}
	
	@GetMapping("/{id}")
	public Optional<TipoMascota> getTipoMascotaById(@PathVariable("id") Long id) {
        return tipoMascotaService.getTipoMascotaById(id);
    }
	
	@PostMapping
    public TipoMascota createTipoMascota(@RequestBody TipoMascota tipoMascota) {
        return tipoMascotaService.saveTipoMascota(tipoMascota);
    }

    @DeleteMapping("/{id}")
    public void deleteDepartamento(@PathVariable("id") Long id) {
    	tipoMascotaService.deteTipoMascota(id);
    }
}
