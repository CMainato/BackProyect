package com.uisrael.hospedaje.proyecto.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uisrael.hospedaje.proyecto.dto.MascotaDTO;
import com.uisrael.hospedaje.proyecto.entity.Mascota;
import com.uisrael.hospedaje.proyecto.repository.IMascotaRepository;

@Service
public class MascotaService {

	@Autowired
	private IMascotaRepository mascotaRepository;
	
	//listar mascotas
	public List<Mascota> getAllMascotas(){
		return mascotaRepository.findAll();
	}
	
	//obtencino de mascota por id
	public Optional<Mascota> getMascotaById(Long Id){
		//return mascotaRepository.findById(Id);
		Optional<Mascota> mascotaOptional = mascotaRepository.findById(Id);
        return Optional.ofNullable(mascotaOptional.orElse(null)); // O retorna otra cosa según tu lógica de negocio
	}
	
	//guardar mascota 
	public Mascota saveMascota(Mascota mascota) {
		return mascotaRepository.save(mascota);
	}
	
	//eliminar mascota
	public void deleteMascota(Long id) {
		mascotaRepository.deleteById(id);
	}
	
	//obtener mascotas por id de cliente
	/*public List<Mascota> getMascotasByIdCliente(Long idCliente) {
		 return mascotaRepository.findByCliente_IdCliente(idCliente);
    }*/
	public List<MascotaDTO> getMascotasByIdCliente(Long idCliente) {
        List<Mascota> mascotas = mascotaRepository.findByCliente_IdCliente(idCliente);
        return mascotas.stream()
                       .map(this::convertToDto)
                       .collect(Collectors.toList());
    }

    private MascotaDTO convertToDto(Mascota mascota) {
        MascotaDTO dto = new MascotaDTO();
        dto.setIdMascota(mascota.getIdMascota());
        dto.setNombre(mascota.getNombre());
        dto.setRaza(mascota.getRaza());
        dto.setFechaNacimiento(mascota.getFechaNacimiento());
        dto.setComentario(mascota.getComentario());
        dto.setTipoMascotaId(mascota.getTipoMascota().getTipoMascotaId());
        dto.setIdCliente(mascota.getCliente().getIdCliente());
        return dto;
    }
}
