package com.gato.multi.services;

import com.gato.multi.dtos.Gato.GatoCreateDto;
import com.gato.multi.dtos.Gato.GatoUpdateDto;
import com.gato.multi.models.Gato;
import com.gato.multi.models.Multimedia;
import com.gato.multi.models.Propietario;
import com.gato.multi.repositories.GatoRepository;
import com.gato.multi.repositories.MultimediaRepository;
import com.gato.multi.repositories.PropietarioRepository;
import com.gato.multi.services.Supabase.SupabaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.List;

@Service
public class GatoService {
  
  @Autowired
  private GatoRepository gatoRepository;
  @Autowired
  private PropietarioRepository propietarioRepository;
  @Autowired
  private MultimediaRepository multimediaRepository;
  @Autowired
  private SupabaseService supabaseService;
  
  public Gato create(GatoCreateDto dto, MultipartFile file) {
    // Validar propietario
    Propietario propietario = propietarioRepository.findById(dto.getPropietario_id())
      .orElseThrow(() -> new ResponseStatusException(
        HttpStatus.NOT_FOUND, "Propietario no encontrado con ID: " + dto.getPropietario_id()));
    
    // Subir la imagen a Supabase
    String imageUrl = null;
    if (file != null && !file.isEmpty()) {
      try {
        imageUrl = supabaseService.uploadImage("prueba", file);
      } catch (IOException e) {
        throw new RuntimeException("Error subiendo la imagen a Supabase: " + e.getMessage(), e);
      }
    }
    
    // Crear registro en Multimedia
    Multimedia multimedia = null;
    if (imageUrl != null) {
      multimedia = new Multimedia(file.getOriginalFilename(), file.getContentType(), imageUrl);
      multimediaRepository.save(multimedia);
    }
    
    // Crear el objeto Gato
    Gato gato = new Gato();
    gato.setNombre(dto.getNombre());
    gato.setTamano(dto.getTamanio());
    gato.setPropietario(propietario);
    gato.setMultimedia(multimedia);
    
    return gatoRepository.save(gato);
  }
  
  // Obtener todos los gatos
  public List<Gato> getAll() {
    return gatoRepository.findAll();
  }
  
  // Obtener un gato por su ID
  public Gato getOne(String id) {
    return gatoRepository.findById(id)
      .orElseThrow(() -> new ResponseStatusException(
        HttpStatus.NOT_FOUND, "Gato no encontrado con ID: " + id));
  }
  
  // Actualizar un gato
  public Gato update(String id, GatoUpdateDto dto, MultipartFile newFile) {
    // Buscar el gato existente
    Gato gato = gatoRepository.findById(id)
      .orElseThrow(() -> new ResponseStatusException(
        HttpStatus.NOT_FOUND, "Gato no encontrado con ID: " + id));
    
    // Buscar al propietario
    Propietario propietario = propietarioRepository.findById(dto.getPropietario_id())
      .orElseThrow(() -> new ResponseStatusException(
        HttpStatus.NOT_FOUND, "Propietario no encontrado con ID: " + dto.getPropietario_id()));
    
    // Si hay multimedia asociada, eliminarla
    if (gato.getMultimedia() != null) {
      try {
        supabaseService.deleteImage(gato.getMultimedia().getUrl());
        multimediaRepository.deleteById(gato.getMultimedia().getId());
      } catch (IOException e) {
        throw new RuntimeException("Error eliminando multimedia existente: " + e.getMessage());
      }
    }
    
    // Subir nueva multimedia si se proporciona un archivo
    if (newFile != null && !newFile.isEmpty()) {
      try {
        String newImageUrl = supabaseService.uploadImage("Gato", newFile);
        Multimedia newMultimedia = new Multimedia(newFile.getOriginalFilename(), newFile.getContentType(), newImageUrl);
        multimediaRepository.save(newMultimedia);
        gato.setMultimedia(newMultimedia);
      } catch (IOException e) {
        throw new RuntimeException("Error subiendo nueva multimedia: " + e.getMessage());
      }
    }
    
    // Actualizar los datos del gato
    gato.setNombre(dto.getNombre());
    gato.setTamano(dto.getTamanio());
    gato.setPropietario(propietario);
    // Nota: multimedia no se toca
    
    return gatoRepository.save(gato);
  }
  
  // Eliminar un gato
  public boolean delete(String id) {
    // Buscar el gato por ID
    Gato gato = gatoRepository.findById(id)
      .orElseThrow(() -> new ResponseStatusException(
        HttpStatus.NOT_FOUND, "Gato no encontrado con ID: " + id));
    
    // Si el gato tiene multimedia, se elimina
    if (gato.getMultimedia() != null && gato.getMultimedia().getUrl() != null) {
      try {
        String multimediaUrl = gato.getMultimedia().getUrl();
        supabaseService.deleteImage(multimediaUrl); // Elimina de Supabase
        multimediaRepository.deleteById(gato.getMultimedia().getId()); // Elimina de MongoDB
      } catch (IOException e) {
        throw new RuntimeException("Error eliminando multimedia asociada: " + e.getMessage());
      }
    }
    
    // Eliminar el gato
    gatoRepository.deleteById(id);
    
    // Retornar respuesta de éxito
    return true;
  }
}
