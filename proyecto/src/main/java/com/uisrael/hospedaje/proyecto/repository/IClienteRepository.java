package com.uisrael.hospedaje.proyecto.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.uisrael.hospedaje.proyecto.entity.Cliente;

@Repository
public interface IClienteRepository extends JpaRepository<Cliente, Long>{

	// Método para buscar clientes por nombre
    List<Cliente> findByCedula(String cedula);
}
