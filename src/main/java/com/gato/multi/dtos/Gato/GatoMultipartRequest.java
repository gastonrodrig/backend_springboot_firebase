package com.gato.multi.dtos.Gato;

import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.web.multipart.MultipartFile;

public class GatoMultipartRequest {
  
  @Schema(type = "string", description = "Nombre del gato")
  private String nombre;
  
  @Schema(type = "string", description = "Tamaño del gato")
  private String tamanio;
  
  @Schema(type = "string", description = "ID del propietario del gato")
  private String propietario_id;
  
  @Schema(type = "string", format = "binary", description = "Archivo de imagen")
  private MultipartFile file;
  
  // Getters y Setters
  public String getNombre() {
    return nombre;
  }
  
  public void setNombre(String nombre) {
    this.nombre = nombre;
  }
  
  public String getTamanio() {
    return tamanio;
  }
  
  public void setTamanio(String tamanio) {
    this.tamanio = tamanio;
  }
  
  public String getPropietario_id() {
    return propietario_id;
  }
  
  public void setPropietario_id(String propietario_id) {
    this.propietario_id = propietario_id;
  }
  
  public MultipartFile getFile() {
    return file;
  }
  
  public void setFile(MultipartFile file) {
    this.file = file;
  }
}
