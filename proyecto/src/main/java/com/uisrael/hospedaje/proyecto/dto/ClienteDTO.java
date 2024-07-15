package com.uisrael.hospedaje.proyecto.dto;

import java.util.List;

import lombok.Data;

@Data
public class ClienteDTO {

	private Long idCliente;
    private String nombre;
    private String apellido;
    private String cedula;
    private String email;
    private String telefono;
    private String direccion;
    private List<MascotaDTO> mascotas;
}
