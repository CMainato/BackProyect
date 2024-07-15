package com.uisrael.hospedaje.proyecto.service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uisrael.hospedaje.proyecto.entity.Cliente;
import com.uisrael.hospedaje.proyecto.entity.Mascota;
import com.uisrael.hospedaje.proyecto.entity.Reserva;
import com.uisrael.hospedaje.proyecto.entity.Servicio;
import com.uisrael.hospedaje.proyecto.repository.IClienteRepository;
import com.uisrael.hospedaje.proyecto.repository.IMascotaRepository;
import com.uisrael.hospedaje.proyecto.repository.IReservaRepository;
import com.uisrael.hospedaje.proyecto.repository.IServicioRepository;
import com.uisrael.hospedaje.proyecto.dto.*;


@Service
public class ReservaService {

	@Autowired
	private IReservaRepository reservaRepository;
	
	@Autowired
	private IClienteRepository clienteRepository;
	
	@Autowired
	private IMascotaRepository mascotaRepository;
	
	@Autowired
	private IServicioRepository servicioRepository;
	
	
	//lista de reservas
	/*public List<Reserva> getAllReservas(){
		return reservaRepository.findAll();
	}*/
	/*public List<ReservasDTO> getAllReservas() {
        List<Reserva> reservas = reservaRepository.findAll();
        List<ReservasDTO> reservaDTOs = new ArrayList<>();

        for (Reserva reserva : reservas) {
            ReservasDTO reservaDTO = convertToDTO(reserva);
            reservaDTOs.add(reservaDTO);
        }

        return reservaDTOs;
    }*/
	public List<ReservasDTO> getAllReservasDTO() {
        List<Reserva> reservas = reservaRepository.findAll();
        return reservas.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
	
	public ReservasDTO convertToDTO(Reserva reserva) {
		ReservasDTO reservaDTO = new ReservasDTO();
	    reservaDTO.setIdReserva(reserva.getIdReserva());
	    reservaDTO.setIdCliente(convertToClienteDTO(reserva.getCliente()).getIdCliente());
	    reservaDTO.setIdMascota(convertToMascotaDTO(reserva.getMascota()).getIdMascota());
	    reservaDTO.setServicios(convertToServicioDTOList(reserva.getServicios())); // Aquí convertimos la lista de Servicio a ServicioDTO
	    reservaDTO.setCantidad(reserva.getCantidad());
	    reservaDTO.setFechaInicio(reserva.getFechaInicio());
	    reservaDTO.setFechaFin(reserva.getFechaFin());
	    reservaDTO.setEstado(reserva.getEstado());
	    return reservaDTO;
    }
	
	private List<ServicioDTO> convertToServicioDTOList(List<Servicio> servicios) {
        return servicios.stream()
                .map(this::convertToServicioDTO)
                .collect(Collectors.toList());
    }

	private ServicioDTO convertToServicioDTO(Servicio servicio) {
	    ServicioDTO servicioDTO = new ServicioDTO();
	    servicioDTO.setIdServicio(servicio.getIdServicio());
	    servicioDTO.setNombre(servicio.getNombre());
	    servicioDTO.setDescripcion(servicio.getDescripcion());
	    servicioDTO.setPrecio(servicio.getPrecio()); // Ajustar según los campos de ServicioDTO
	    // Añadir más campos según sea necesario
	    return servicioDTO;
	}
	
	private ClienteDTO convertToClienteDTO(Cliente cliente) {
	    ClienteDTO clienteDTO = new ClienteDTO();
	    clienteDTO.setIdCliente(cliente.getIdCliente());
	    clienteDTO.setNombre(cliente.getNombre());
	    clienteDTO.setApellido(cliente.getApellido());
	    clienteDTO.setCedula(cliente.getCedula());
	    clienteDTO.setEmail(cliente.getEmail());
	    clienteDTO.setTelefono(cliente.getTelefono());
	    return clienteDTO;
	}
	
	private MascotaDTO convertToMascotaDTO(Mascota mascota) {
	    MascotaDTO mascotaDTO = new MascotaDTO();
	    mascotaDTO.setIdMascota(mascota.getIdMascota());
	    mascotaDTO.setNombre(mascota.getNombre());
	    //mascotaDTO.setEdad(mascota.getEdad());
	    // Añadir más campos según sea necesario
	    return mascotaDTO;
	}
	
	/*private List<ServicioDTO> convertToServicioDTOList(List<Servicio> servicios) {
	    List<ServicioDTO> servicioDTOs = new ArrayList<>();
	    for (Servicio servicio : servicios) {
	        ServicioDTO servicioDTO = new ServicioDTO();
	        servicioDTO.setIdServicio(servicio.getIdServicio());
	        servicioDTO.setNombre(servicio.getNombre());
	        servicioDTO.setDescripcion(servicio.getDescripcion());
	        
	        // Añadir más campos según sea necesario
	        servicioDTOs.add(servicioDTO);
	    }
	    return servicioDTOs;
	}*/

	
	//--------------------------------lista de reservas mediante Id
	public Optional<Reserva> getReservaById(Long id){
		return reservaRepository.findById(id);
	}
	
	//-------------------------------guardar reservas
	public Reserva saveReservas(Reserva reserva) {
		return reservaRepository.save(reserva);
	}
	
	//----------------------------------- BUSQUEDA DE RESERVAS POR ID DE CLIENTE
	// Método para buscar reservas por ID de cliente
    /*public List<Reserva> getReservasByClienteId(Long idCliente) {
        return reservaRepository.findByClienteId(idCliente);
    }*/
	public List<ReservasDTO> getReservasByClienteId(Long idCliente) {
	    List<Reserva> reservas = reservaRepository.findByClienteId(idCliente);
	    return reservas.stream()
	                  .map(this::convertToDTO)
	                  .collect(Collectors.toList());
	}
	
	public ReservasDTO crearReserva(ReservasDTO request) {
        Cliente cliente = clienteRepository.findById(request.getIdCliente())
                .orElseThrow(() -> new IllegalArgumentException("Cliente no encontrado"));

        Mascota mascota = mascotaRepository.findById(request.getIdMascota())
                .orElseThrow(() -> new IllegalArgumentException("Mascota no encontrada"));

        List<Servicio> servicios = servicioRepository.findAllById(request.getServiciosIds());

        java.util.Date utilFechaInicio = request.getFechaInicio();
        java.util.Date utilFechaFin = request.getFechaFin();
        // Convierte java.util.Date a java.sql.Date
        Date sqlFechaInicio = new Date(utilFechaInicio.getTime());
        Date sqlFechaFin = new Date(utilFechaFin.getTime());

        Reserva reserva = new Reserva();
        reserva.setCliente(cliente);
        reserva.setMascota(mascota);
        reserva.setServicios(servicios);
        reserva.setCantidad(request.getCantidad());
        reserva.setFechaInicio(sqlFechaInicio);
        reserva.setFechaFin(sqlFechaFin);
        reserva.setEstado(request.getEstado());

        reserva = reservaRepository.save(reserva);

        // Convertir la entidad Reserva a ReservasDTO antes de devolverla
        return convertToDTO(reserva);
    }

	//------------------------ PARA EDITAR LA RESERVA
	public ReservasDTO updateReserva(Reserva reserva) {
        // Implementar la lógica para actualizar la reserva
        Reserva updatedReserva = reservaRepository.save(reserva);
        return convertToDTO(updatedReserva);
    }
	
	//eliminar reservas
	public void deleteReserva(Long id) {
		reservaRepository.deleteById(id);
	}
}
