package com.uisrael.hospedaje.proyecto.entity;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "tbl_reserva")
public class Reserva {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idReserva;
	
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idCliente", nullable = false)
    private Cliente cliente;
    
    @ManyToOne
    @JoinColumn(name = "idMascota", nullable = false)
    private Mascota mascota;
    
    @ManyToMany
    @JoinTable(
        name = "reserva_servicio",
        joinColumns = @JoinColumn(name = "idReserva"),
        inverseJoinColumns = @JoinColumn(name = "idServicio")
    )
    private List<Servicio> servicios = new ArrayList<>();
    
    @Column(nullable = false)
    private Integer cantidad;
    
    @Column(nullable = false)
    private Date fechaInicio;
    
    @Column(nullable = false)
    private Date fechaFin;
    
    @Column(nullable = false)
    private String estado;
}
