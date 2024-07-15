package com.uisrael.hospedaje.proyecto.entity;

import java.sql.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;

@Data
@Entity
@Table(name = "tbl_mascota")
public class Mascota {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idMascota;
	private String nombre;
	private String raza;
	@Temporal(TemporalType.DATE)
	private Date fechaNacimiento;
	private String comentario;
	
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tipoMascotaId", nullable = false)
    private TipoMascota tipoMascota;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idCliente", nullable = false)
    private Cliente cliente;
}
