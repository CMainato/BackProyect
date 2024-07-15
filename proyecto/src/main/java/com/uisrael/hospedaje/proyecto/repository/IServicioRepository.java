package com.uisrael.hospedaje.proyecto.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.uisrael.hospedaje.proyecto.entity.Servicio;

@Repository
public interface IServicioRepository extends JpaRepository<Servicio, Long>{

}
