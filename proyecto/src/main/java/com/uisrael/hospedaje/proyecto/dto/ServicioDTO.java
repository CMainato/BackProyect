package com.uisrael.hospedaje.proyecto.dto;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class ServicioDTO {
	
	private Long idServicio;
	private String nombre;
	private String descripcion;
	private BigDecimal precio;
}
