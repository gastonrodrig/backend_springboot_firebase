package com.gato.multi.services;

import com.gato.multi.dtos.Propietario.PropietarioCreateDto;
import com.gato.multi.dtos.Propietario.PropietarioUpdateDto;
import com.gato.multi.models.Propietario;
import com.gato.multi.utils.FirestoreUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PropietarioService {
  
  private static final String COLLECTION_NAME = "Propietario";
  
  // Crear un propietario
  public Propietario create(PropietarioCreateDto dto) {
    Propietario propietario = new Propietario(
      dto.getNombre(),
      dto.getTelefono(),
      dto.getEmail(),
      dto.getDireccion()
    );
    
    // Guardar propietario en Firestore con ID generado automáticamente
    String generatedId = FirestoreUtils.saveDocumentWithGeneratedId(COLLECTION_NAME, propietario);
    propietario.setId(generatedId); // Asignar el ID generado al modelo
    
    return propietario;
  }
  
  // Obtener todos los propietarios
  public List<Propietario> getAll() {
    return FirestoreUtils.fetchAllDocuments(COLLECTION_NAME, Propietario.class);
  }
  
  // Obtener un propietario por ID
  public Propietario getOne(String propietarioId) {
    return FirestoreUtils.fetchDocument(COLLECTION_NAME, propietarioId, Propietario.class);
  }
  
  // Actualizar un propietario
  public Propietario update(String propietarioId, PropietarioUpdateDto dto) {
    // Validar que el propietario exista
    Propietario propietario = getOne(propietarioId);
    
    // Actualizar los campos
    propietario.setNombre(dto.getNombre());
    propietario.setTelefono(dto.getTelefono());
    propietario.setEmail(dto.getEmail());
    propietario.setDireccion(dto.getDireccion());
    
    // Guardar cambios en Firestore usando el mismo ID
    FirestoreUtils.updateDocument(COLLECTION_NAME, propietarioId, propietario);
    
    return propietario;
  }
  
  // Eliminar un propietario
  public boolean delete(String propietarioId) {
    // Validar que el propietario exista
    FirestoreUtils.validateDocumentExists(COLLECTION_NAME, propietarioId);
    
    // Eliminar propietario en Firestore
    FirestoreUtils.deleteDocument(COLLECTION_NAME, propietarioId);
    
    return true;
  }
}
