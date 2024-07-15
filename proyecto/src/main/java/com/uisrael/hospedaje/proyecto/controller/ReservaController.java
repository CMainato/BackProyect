package com.uisrael.hospedaje.proyecto.controller;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.uisrael.hospedaje.proyecto.dto.ReservasDTO;
import com.uisrael.hospedaje.proyecto.entity.Cliente;
import com.uisrael.hospedaje.proyecto.entity.Mascota;
import com.uisrael.hospedaje.proyecto.entity.Reserva;
import com.uisrael.hospedaje.proyecto.entity.Servicio;
import com.uisrael.hospedaje.proyecto.repository.IClienteRepository;
import com.uisrael.hospedaje.proyecto.repository.IMascotaRepository;
import com.uisrael.hospedaje.proyecto.repository.IServicioRepository;
import com.uisrael.hospedaje.proyecto.service.ReservaService;

@RestController
@RequestMapping("/api/v1/hospedaje/reserva")
public class ReservaController {

	@Autowired
	private ReservaService reservaService;
	
	@Autowired
	private IClienteRepository clienteRepository;
	
	@Autowired
	private IMascotaRepository mascotaRepository;
	
	@Autowired
	private IServicioRepository servicioRepository;
	
	
	
	/*@GetMapping
	public List<Reserva> getAllReservas(){
		return reservaService.getAllReservas();
	}*/
	//------------------ TODAS LAS RESERVAS
	@GetMapping
    public ResponseEntity<List<ReservasDTO>> getAllReservas() {
        List<ReservasDTO> reservasDTO = reservaService.getAllReservasDTO();
        return ResponseEntity.ok().body(reservasDTO);
    }
	
	//----------------------- obtener por id de reserva
	@GetMapping("/{id}")
    public ResponseEntity<ReservasDTO> getReservaById(@PathVariable("id") Long id) {
        Optional<Reserva> optionalReserva = reservaService.getReservaById(id);
        if (optionalReserva.isPresent()) {
            ReservasDTO reservaDTO = reservaService.convertToDTO(optionalReserva.get());
            return ResponseEntity.ok().body(reservaDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
	
	//--------------------------------- RESERVA POR ID DE CLIENTE
	// Endpoint para obtener reservas por ID de cliente
    @GetMapping("/cliente/{idCliente}")
    	public ResponseEntity<List<ReservasDTO>> getReservasByClienteId(@PathVariable Long idCliente) {
    	    List<ReservasDTO> reservasDTO = reservaService.getReservasByClienteId(idCliente);
    	    return ResponseEntity.ok().body(reservasDTO);
    	}
	
	//---------------------------- CREAR RESERVA 
	@PostMapping
	public ResponseEntity<ReservasDTO>  createReserva(@RequestBody ReservasDTO reservaDTO) {
	    //Reserva reserva = convertToEntity(reservaDTO);
	    //return reservaService.crearReserva(reservaDTO);
		ReservasDTO nuevaReserva = reservaService.crearReserva(reservaDTO);
        return new ResponseEntity<>(nuevaReserva, HttpStatus.CREATED);
	}

	private Reserva convertToEntity(ReservasDTO reservaDTO) {
	    // Convertir java.util.Date a java.sql.Date
	    Date sqlFechaInicio = new Date(reservaDTO.getFechaInicio().getTime());
	    Date sqlFechaFin = new Date(reservaDTO.getFechaFin().getTime());

	    // Obtener y asignar cliente desde el repositorio
	    Cliente cliente = clienteRepository.findById(reservaDTO.getIdCliente())
	            .orElseThrow(() -> new IllegalArgumentException("Cliente no encontrado con ID: " + reservaDTO.getIdCliente()));

	    // Obtener y asignar mascota desde el repositorio
	    Mascota mascota = mascotaRepository.findById(reservaDTO.getIdMascota())
	            .orElseThrow(() -> new IllegalArgumentException("Mascota no encontrada con ID: " + reservaDTO.getIdMascota()));

	    // Obtener y asignar servicios desde el repositorio
	    List<Servicio> servicios = servicioRepository.findAllById(reservaDTO.getServiciosIds());

	    Reserva reserva = new Reserva();
	    reserva.setCliente(cliente);
	    reserva.setMascota(mascota);
	    reserva.setServicios(servicios);
	    reserva.setCantidad(reservaDTO.getCantidad());
	    reserva.setFechaInicio(sqlFechaInicio);
	    reserva.setFechaFin(sqlFechaFin);
	    reserva.setEstado(reservaDTO.getEstado());

	    return reserva;
	}

	/******  ------------    EDITAR  ------------ *************/
	@PutMapping("/{id}")
    public ResponseEntity<ReservasDTO> updateReserva(@PathVariable("id") Long id, @RequestBody ReservasDTO reservaDTO) {
        // Verificar si la reserva con el ID proporcionado existe
        Optional<Reserva> optionalReserva = reservaService.getReservaById(id);
        if (optionalReserva.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        // Convertir DTO a entidad Reserva
        Reserva reservaToUpdate = convertToEntity(reservaDTO);

        // Establecer el ID de la reserva a actualizar
        reservaToUpdate.setIdReserva(id);

        // Actualizar la reserva en el servicio
        ReservasDTO updatedReservaDTO = reservaService.updateReserva(reservaToUpdate);

        // Devolver la respuesta con la reserva actualizada
        return ResponseEntity.ok().body(updatedReservaDTO);
    }
	
	

	/******  ------------    ELIMINAR  ------------ *************/
    @DeleteMapping("/{id}")
    public void deleteReserva(@PathVariable("id") Long id) {
    	reservaService.deleteReserva(id);
    }
}
