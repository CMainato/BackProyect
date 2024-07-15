package com.uisrael.hospedaje.proyecto.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.uisrael.hospedaje.proyecto.entity.Mascota;

@Repository
public interface IMascotaRepository extends JpaRepository<Mascota, Long>{
	//para listar las mascotas por id de cliente
	 List<Mascota> findByCliente_IdCliente(Long idCliente);
}
