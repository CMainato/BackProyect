package com.uisrael.hospedaje.proyecto.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uisrael.hospedaje.proyecto.dto.ClienteDTO;
import com.uisrael.hospedaje.proyecto.entity.Cliente;
import com.uisrael.hospedaje.proyecto.repository.IClienteRepository;

@Service
public class ClienteService {

	@Autowired
	private IClienteRepository clienteRepository;
	
	//lista de clientes
	/*public List<Cliente> getAllClientes(){
		return clienteRepository.findAll();
	}*/
	public List<ClienteDTO> getAllClientesDTO() {
	    List<Cliente> clientes = clienteRepository.findAll();
	    return clientes.stream()
	            .map(this::convertToDTO)
	            .collect(Collectors.toList());
	}
	
	//obtencion de cliente por id
	public Optional<Cliente> getClienteById(Long id){
		return clienteRepository.findById(id);
	}
		
	//guardar cliente
	public Cliente saveCliente(Cliente cliente) {
		return clienteRepository.save(cliente);
	}

	//eliminar cliente
    public void deleteCliente(Long id) {
	   clienteRepository.deleteById(id);
    }
    
    //----------------- Método para buscar clientes por nombre
    /*public List<Cliente> buscarClientesCedula(String cedula) {
        return clienteRepository.findByCedula(cedula);
    }*/
 // Servicio
    public List<ClienteDTO> buscarClientesCedula(String cedula) {
        List<Cliente> clientes = clienteRepository.findByCedula(cedula);
        return clientes.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private ClienteDTO convertToDTO(Cliente cliente) {
        ClienteDTO clienteDTO = new ClienteDTO();
        clienteDTO.setIdCliente(cliente.getIdCliente());
        clienteDTO.setNombre(cliente.getNombre());
        clienteDTO.setApellido(cliente.getApellido());
        clienteDTO.setCedula(cliente.getCedula());
        clienteDTO.setEmail(cliente.getEmail());
        clienteDTO.setTelefono(cliente.getTelefono());
        clienteDTO.setDireccion(cliente.getDireccion());
        return clienteDTO;
    }

    
}
