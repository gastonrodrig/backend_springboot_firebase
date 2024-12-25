package com.gato.multi.services;

import com.gato.multi.dtos.Gato.GatoCreateDto;
import com.gato.multi.dtos.Gato.GatoUpdateDto;
import com.gato.multi.models.Gato;
import com.gato.multi.models.Multimedia;
import com.gato.multi.services.Firebase.FirebaseStorageService;
import com.gato.multi.utils.FirestoreUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class GatoService {
  
  private static final String COLLECTION_NAME = "Gato";
  private static final String MULTIMEDIA_COLLECTION = "Multimedia";
  private static final String PROPIETARIO_COLLECTION = "Propietario";
  
  private final FirebaseStorageService firebaseStorageService;
  
  public GatoService(FirebaseStorageService firebaseStorageService) {
    this.firebaseStorageService = firebaseStorageService;
  }
  
  // Crear un gato
  public Gato create(GatoCreateDto dto, MultipartFile file) {
    // Validar la existencia del propietario
    FirestoreUtils.validateDocumentExists(PROPIETARIO_COLLECTION, dto.getPropietario_id());
    
    // Subir la imagen y registrar la multimedia (si existe)
    String multimediaId = null;
    if (file != null && !file.isEmpty()) {
      try {
        String imageUrl = firebaseStorageService.uploadFile("Gato", file);
        Multimedia multimedia = new Multimedia(file.getOriginalFilename(), file.getContentType(), imageUrl);
        multimediaId = FirestoreUtils.saveDocumentWithGeneratedId(MULTIMEDIA_COLLECTION, multimedia);
      } catch (IOException e) {
        throw new RuntimeException("Error subiendo la imagen a Firebase Storage: " + e.getMessage(), e);
      }
    }
    
    // Crear el objeto Gato
    Gato gato = new Gato();
    gato.setNombre(dto.getNombre());
    gato.setTamano(dto.getTamanio());
    gato.setPropietario(dto.getPropietario_id());
    gato.setMultimedia(multimediaId);
    
    String gatoId = FirestoreUtils.saveDocumentWithGeneratedId(COLLECTION_NAME, gato);
    gato.setId(gatoId); // Asignar el ID generado al objeto
    
    return gato;
  }
  
  // Obtener todos los gatos
  public List<Gato> getAll() {
    return FirestoreUtils.fetchAllDocuments(COLLECTION_NAME, Gato.class);
  }
  
  // Obtener un gato por ID
  public Gato getOne(String id) {
    return FirestoreUtils.fetchDocument(COLLECTION_NAME, id, Gato.class);
  }
  
  // Actualizar un gato
  public Gato update(String id, GatoUpdateDto dto, MultipartFile newFile) {
    // Obtener el gato existente
    Gato existingGato = getOne(id);
    
    // Validar la existencia del propietario
    FirestoreUtils.validateDocumentExists(PROPIETARIO_COLLECTION, dto.getPropietario_id());
    
    // Manejar la multimedia si hay un nuevo archivo
    String newMultimediaId = existingGato.getMultimedia();
    if (newFile != null && !newFile.isEmpty()) {
      try {
        // Eliminar la multimedia anterior si existe
        if (existingGato.getMultimedia() != null) {
          Multimedia oldMultimedia = FirestoreUtils.fetchDocument(MULTIMEDIA_COLLECTION, existingGato.getMultimedia(), Multimedia.class);
          firebaseStorageService.deleteFile(oldMultimedia.getUrl());
          FirestoreUtils.deleteDocument(MULTIMEDIA_COLLECTION, existingGato.getMultimedia());
        }
        
        // Subir nueva multimedia
        String newImageUrl = firebaseStorageService.uploadFile(COLLECTION_NAME, newFile);
        Multimedia newMultimedia = new Multimedia(newFile.getOriginalFilename(), newFile.getContentType(), newImageUrl);
        newMultimediaId = FirestoreUtils.saveDocumentWithGeneratedId(MULTIMEDIA_COLLECTION, newMultimedia);
      } catch (IOException e) {
        throw new RuntimeException("Error manejando la nueva multimedia: " + e.getMessage(), e);
      }
    }
    
    // Actualizar datos del gato
    existingGato.setNombre(dto.getNombre());
    existingGato.setTamano(dto.getTamanio());
    existingGato.setPropietario(dto.getPropietario_id());
    existingGato.setMultimedia(newMultimediaId);
    
    // Actualizar documento en Firestore con el mismo ID
    FirestoreUtils.updateDocument(COLLECTION_NAME, id, existingGato);
    
    return existingGato;
  }
  
  // Eliminar un gato
  public boolean delete(String id) {
    Gato gato = getOne(id);
    
    // Eliminar multimedia asociada
    if (gato.getMultimedia() != null) {
      try {
        Multimedia multimedia = FirestoreUtils.fetchDocument(MULTIMEDIA_COLLECTION, gato.getMultimedia(), Multimedia.class);
        firebaseStorageService.deleteFile(multimedia.getUrl());
        FirestoreUtils.deleteDocument(MULTIMEDIA_COLLECTION, gato.getMultimedia());
      } catch (IOException e) {
        throw new RuntimeException("Error eliminando multimedia asociada: " + e.getMessage(), e);
      }
    }
    
    // Eliminar el gato
    FirestoreUtils.deleteDocument(COLLECTION_NAME, id);
    return true;
  }
}
