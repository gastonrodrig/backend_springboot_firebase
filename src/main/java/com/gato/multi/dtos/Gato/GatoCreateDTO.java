package com.gato.multi.dtos.Gato;

import jakarta.validation.constraints.NotBlank;
import org.springframework.data.mongodb.core.mapping.MongoId;

public class GatoCreateDTO {
  @NotBlank
  private String nombre;
  
  @NotBlank
  private String tamanio;
  
  @MongoId
  @NotBlank
  private String propietario_id;
  
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
}
