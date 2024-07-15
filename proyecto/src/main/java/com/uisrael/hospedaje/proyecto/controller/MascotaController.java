package com.uisrael.hospedaje.proyecto.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.uisrael.hospedaje.proyecto.dto.MascotaDTO;
import com.uisrael.hospedaje.proyecto.entity.Cliente;
import com.uisrael.hospedaje.proyecto.entity.Mascota;
import com.uisrael.hospedaje.proyecto.entity.TipoMascota;
import com.uisrael.hospedaje.proyecto.service.ClienteService;
import com.uisrael.hospedaje.proyecto.service.MascotaService;
import com.uisrael.hospedaje.proyecto.service.TipoMascotaService;

@RestController
@RequestMapping("/api/v1/hospedaje/mascota")
public class MascotaController {

	
	@Autowired
	private MascotaService mascotaService;
	
	@Autowired
    private ClienteService clienteService;
	
	@Autowired
    private TipoMascotaService tipoMascotaService;
	
	/*@GetMapping
	private List<Mascota> getAllMascotas(){
		return mascotaService.getAllMascotas();
	}*/
	@GetMapping
    public ResponseEntity<List<MascotaDTO>> obtenerTodasLasMascotas() {
        List<Mascota> mascotas = mascotaService.getAllMascotas();
        List<MascotaDTO> mascotasDTO = new ArrayList<>();
        
        for (Mascota mascota : mascotas) {
            MascotaDTO mascotaDTO = new MascotaDTO(
                    mascota.getIdMascota(),
                    mascota.getNombre(),
                    mascota.getRaza(),
                    mascota.getFechaNacimiento(),
                    mascota.getComentario(),
                    mascota.getTipoMascota().getTipoMascotaId(),
                    mascota.getCliente().getIdCliente()
            );
            mascotasDTO.add(mascotaDTO);
        }
        return new ResponseEntity<>(mascotasDTO, HttpStatus.OK);
	}

	///////////// mascota por id 
	
	/*@GetMapping("/{id}")
    public Optional<Mascota> getMascotaById(@PathVariable("id") Long id) {
        return mascotaService.getMascotaById(id);
    }*/
	// Endpoint para obtener una mascota por su ID
    @GetMapping("/{id}")
    public ResponseEntity<MascotaDTO> obtenerMascota(@PathVariable Long id) {
    	Optional<Mascota> mascotaOptional = mascotaService.getMascotaById(id);
        
        if (mascotaOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Mascota mascota = mascotaOptional.get();
        MascotaDTO mascotaDTO = convertirAMascotaDTO(mascota);
        return ResponseEntity.ok().body(mascotaDTO);
    }
 // Método para convertir una Mascota a MascotaDTO
    private MascotaDTO convertirAMascotaDTO(Mascota mascota) {
        MascotaDTO dto = new MascotaDTO();
        dto.setIdMascota(mascota.getIdMascota());
        dto.setNombre(mascota.getNombre());
        dto.setRaza(mascota.getRaza());
        dto.setFechaNacimiento(mascota.getFechaNacimiento());
        dto.setComentario(mascota.getComentario());
        dto.setTipoMascotaId(mascota.getTipoMascota().getTipoMascotaId()); // Suponiendo que tienes un método getIdTipoMascota() en TipoMascota
        dto.setIdCliente(mascota.getCliente().getIdCliente()); // Suponiendo que tienes un método getIdCliente() en Cliente
        return dto;
    }
    
    
	
	@GetMapping("/cliente/{id}")
    public ResponseEntity<List<MascotaDTO>> obtenerMascotasPorIdCliente(@PathVariable Long id) {
        List<MascotaDTO> mascotas = mascotaService.getMascotasByIdCliente(id);
        
        if (!mascotas.isEmpty()) {
            return ResponseEntity.ok().body(mascotas);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
	/*@GetMapping("/cliente/{id}")
    public ResponseEntity<List<Mascota>> obtenerMascotasPorIdCliente(@PathVariable Long id) {
        List<Mascota> mascotas = mascotaService.getMascotasByIdCliente(id);
        return ResponseEntity.ok().body(mascotas);
    }*/

    /*@PostMapping
    public Mascota createMascota(@RequestBody Mascota mascota) {
        return mascotaService.saveMascota(mascota);
    }*/
	
	// crear una mascota
    @PostMapping
    public ResponseEntity<Mascota> crearMascota(@RequestBody Mascota mascotaRequest) {
        // Obtener el cliente por su id
    	Optional<Cliente> clienteOptional = clienteService.getClienteById(mascotaRequest.getCliente().getIdCliente());
        
        // Verificar si el cliente existe en el Optional
        if (clienteOptional.isEmpty()) {
        	return ResponseEntity.notFound().build(); // Devolver respuesta 404 Not Found
        }

     // Obtener el tipo de mascota por su id
        Optional<TipoMascota> tipoMascotaOptional = tipoMascotaService.getTipoMascotaById(mascotaRequest.getTipoMascota().getTipoMascotaId());
        if (tipoMascotaOptional.isEmpty()) {
            return ResponseEntity.notFound().build(); // Tipo de mascota no encontrado
        }
        // Crear una nueva instancia de Mascota y asignar los valores
        Mascota mascota = new Mascota();
        mascota.setNombre(mascotaRequest.getNombre());
        mascota.setRaza(mascotaRequest.getRaza());
        mascota.setFechaNacimiento(mascotaRequest.getFechaNacimiento());
        mascota.setComentario(mascotaRequest.getComentario());
     // Asignar el tipo de mascota persistente
        mascota.setTipoMascota(tipoMascotaOptional.get());
        
     // Obtener el cliente del Optional y asignarlo a la mascota
        Cliente cliente = clienteOptional.get(); // Extraer el cliente del Optional
        mascota.setCliente(cliente); // Asignar el cliente obtenido
        
        // Guardar la mascota utilizando el servicio
        Mascota nuevaMascota = mascotaService.saveMascota(mascota);
        
        return new ResponseEntity<>(nuevaMascota, HttpStatus.CREATED);
    }
    
    //---------------------actualizar una mascota 
    @PostMapping("/update/{id}")
    public ResponseEntity<MascotaDTO> actualizarMascota(@PathVariable Long id, @RequestBody MascotaDTO mascotaActualizada) {
        Optional<Mascota> mascotaExistenteOptional = mascotaService.getMascotaById(id);
        if (mascotaExistenteOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        java.util.Date fechaUtil = mascotaActualizada.getFechaNacimiento();
        java.sql.Date fechaSql = new java.sql.Date(fechaUtil.getTime());
        
        Mascota mascotaExistente = mascotaExistenteOptional.get();

        // Actualizar los campos de la mascota existente con los datos de la mascota actualizada
        mascotaExistente.setNombre(mascotaActualizada.getNombre());
        mascotaExistente.setRaza(mascotaActualizada.getRaza());
        mascotaExistente.setFechaNacimiento(fechaSql);
        mascotaExistente.setComentario(mascotaActualizada.getComentario());

        // Obtener y asignar el cliente actualizado
        Optional<Cliente> clienteOptional = clienteService.getClienteById(mascotaActualizada.getIdCliente());
        if (clienteOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        mascotaExistente.setCliente(clienteOptional.get());

        // Obtener y asignar el tipo de mascota actualizado
        Optional<TipoMascota> tipoMascotaOptional = tipoMascotaService.getTipoMascotaById(mascotaActualizada.getTipoMascotaId());
        if (tipoMascotaOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        mascotaExistente.setTipoMascota(tipoMascotaOptional.get());

     // Guardar y convertir la mascota actualizada a DTO antes de devolverla
        Mascota mascotaActualizadaEnBD = mascotaService.saveMascota(mascotaExistente);
        MascotaDTO mascotaActualizadaDTO = convertirAMascotaDTO(mascotaActualizadaEnBD);
        return ResponseEntity.ok().body(mascotaActualizadaDTO);
    }

    //---------------------------------ELIMINAR LA MASCOTA
    @DeleteMapping("/{id}")
    public void deleteMascota(@PathVariable("id") Long id) {
    	mascotaService.deleteMascota(id);
    }
}
