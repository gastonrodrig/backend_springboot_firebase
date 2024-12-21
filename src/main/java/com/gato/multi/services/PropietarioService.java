package com.gato.multi.services;

import com.gato.multi.dtos.Propietario.PropietarioCreateDto;
import com.gato.multi.dtos.Propietario.PropietarioUpdateDto;
import com.gato.multi.models.Propietario;
import com.gato.multi.repositories.PropietarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class PropietarioService {
  
  @Autowired
  private PropietarioRepository propietarioRepository;
  
  // Crear un propietario
  public Propietario create(PropietarioCreateDto dto) {
    Propietario propietario = new Propietario(
      dto.getNombre(),
      dto.getTelefono(),
      dto.getEmail(),
      dto.getDireccion()
    );
    return propietarioRepository.save(propietario);
  }
  
  // Obtener todos los propietarios
  public List<Propietario> getAll() {
    return propietarioRepository.findAll();
  }
  
  // Obtener un propietario por ID
  public Propietario getOne(String id) {
    return propietarioRepository.findById(id)
      .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Propietario no encontrado con ID: " + id));
  }
  
  // Actualizar un propietario
  public Propietario update(String id, PropietarioUpdateDto dto) {
    Propietario propietario = propietarioRepository.findById(id)
      .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Propietario no encontrado con ID: " + id));
    
    propietario.setNombre(dto.getNombre());
    propietario.setTelefono(dto.getTelefono());
    propietario.setEmail(dto.getEmail());
    propietario.setDireccion(dto.getDireccion());
    
    return propietarioRepository.save(propietario);
  }
  
  // Eliminar un propietario
  public Propietario delete(String id) {
    Propietario propietario = propietarioRepository.findById(id)
      .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Propietario no encontrado con ID: " + id));
    
    propietarioRepository.delete(propietario);
    return propietario;
  }
}
