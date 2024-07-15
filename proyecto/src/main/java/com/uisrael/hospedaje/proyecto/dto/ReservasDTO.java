package com.uisrael.hospedaje.proyecto.dto;

import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class ReservasDTO {

	private Long idReserva;
	private Long idCliente;
    private Long idMascota;
    private List<Long> serviciosIds;  // Lista de IDs de servicios
    private List<ServicioDTO> servicios;  // Lista de objetos ServicioDTO
    private Integer cantidad;
    private Date fechaInicio;
    private Date fechaFin;
    private String estado;
}
