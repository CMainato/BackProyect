package com.uisrael.hospedaje.proyecto.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.uisrael.hospedaje.proyecto.entity.Reserva;

@Repository
public interface IReservaRepository extends JpaRepository<Reserva, Long>{
	// Método para buscar reservas por ID de cliente
	@Query("SELECT r FROM Reserva r WHERE r.cliente.idCliente = :idCliente")
    List<Reserva> findByClienteId(Long idCliente);
}
