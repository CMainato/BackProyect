package com.uisrael.hospedaje.proyecto.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name="tbl_cliente")
public class Cliente {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idCliente;
	private String nombre;
	private String apellido;
	private String cedula;
	private String email;
	private String telefono;
	private String direccion;
	
	@OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL)
	private List<Mascota> mascotas = new ArrayList<>();
	
}
