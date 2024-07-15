package com.uisrael.hospedaje.proyecto.dto;

import java.util.Date;

import lombok.Data;

@Data
public class MascotaDTO {
	private Long idMascota;
    private String nombre;
    private String raza;
    private Date fechaNacimiento;
    private String comentario;
    private Long tipoMascotaId;
    private Long idCliente;
    
 // Constructor con todos los campos
    public MascotaDTO(Long idMascota, String nombre, String raza, Date fechaNacimiento, String comentario, Long idTipoMascota, Long idCliente) {
        this.idMascota = idMascota;
        this.nombre = nombre;
        this.raza = raza;
        this.fechaNacimiento = fechaNacimiento;
        this.comentario = comentario;
        this.tipoMascotaId = idTipoMascota;
        this.idCliente = idCliente;
    }
    
 // Constructor sin argumentos generado por Lombok
    public MascotaDTO() {
    }
}
