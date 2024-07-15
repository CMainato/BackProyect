package com.uisrael.hospedaje.proyecto.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "tbl_tipoMascota")
public class TipoMascota {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long tipoMascotaId;
	private String nombreTipoMascota;
}
