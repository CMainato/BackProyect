package com.uisrael.hospedaje.proyecto.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.uisrael.hospedaje.proyecto.dto.ClienteDTO;
import com.uisrael.hospedaje.proyecto.entity.Cliente;
import com.uisrael.hospedaje.proyecto.service.ClienteService;

@RestController
@RequestMapping("/api/v1/hospedaje/cliente")
public class ClienteController {
	@Autowired
	private ClienteService clienteService;
	
	//-------------------------------- LISTAR CLIENTES
	/*@GetMapping
	public List<Cliente> getAllClientes(){
		return clienteService.getAllClientes();
	}*/
	@GetMapping
    public ResponseEntity<List<ClienteDTO>> getAllClientes() {
        List<ClienteDTO> clientesDTO = clienteService.getAllClientesDTO();
        return ResponseEntity.ok().body(clientesDTO);
    }
	//-------------------------------- LISTAR CLIENTES POR ID 
	/*
	@GetMapping("/{id}")
	public Optional<Cliente> getClienteById(@PathVariable("id") Long id){
		return clienteService.getClienteById(id);
	}*/
	
	@GetMapping("/{id}")
    public ResponseEntity<ClienteDTO> getClienteById(@PathVariable("id") Long id) {
        Optional<Cliente> optionalCliente = clienteService.getClienteById(id);
        if (optionalCliente.isPresent()) {
            Cliente cliente = optionalCliente.get();
            ClienteDTO clienteDTO = new ClienteDTO();
            BeanUtils.copyProperties(cliente, clienteDTO); // Utilizando BeanUtils para copiar propiedades
            return ResponseEntity.ok().body(clienteDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
	
	@PostMapping
    public Cliente createCliente(@RequestBody Cliente cliente) {
        return clienteService.saveCliente(cliente);
    }
	
	@DeleteMapping("/{id}")
    public void deleteCliente(@PathVariable("id") Long id) {
		clienteService.deleteCliente(id);
    }
	
	// -------------------- Endpoint para buscar clientes por nombre
    /*@GetMapping("/buscar_cedula")
    public List<Cliente> buscarClientesPorNombre(@RequestParam String cedula) {
        return clienteService.buscarClientesCedula(cedula);
    }*/
	@GetMapping("/buscar_cedula")
    public ResponseEntity<List<ClienteDTO>> buscarClientesPorCedula(@RequestParam String cedula) {
        List<ClienteDTO> clientesDTO = clienteService.buscarClientesCedula(cedula);
        return ResponseEntity.ok().body(clientesDTO);
    }
	
}
