package com.gato.multi.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Gato")
public class Gato {
  @Id
  @JsonProperty("_id")
  private String id;
  
  private String nombre;
  private String tamano;
  
  @DBRef
  private Multimedia multimedia;
  
  @DBRef
  private Propietario propietario;
  
  // Constructores
  public Gato() {}
  
  public Gato(String nombre, String tamano, Multimedia multimedia, Propietario propietario) {
    this.nombre = nombre;
    this.tamano = tamano;
    this.multimedia = multimedia;
    this.propietario = propietario;
  }
  
  // Getters y Setters
  public String getId() {
    return id;
  }
  
  public void setId(String id) {
    this.id = id;
  }
  
  public String getNombre() {
    return nombre;
  }
  
  public void setNombre(String nombre) {
    this.nombre = nombre;
  }
  
  public String getTamano() {
    return tamano;
  }
  
  public void setTamano(String tamano) {
    this.tamano = tamano;
  }
  
  public Multimedia getMultimedia() {
    return multimedia;
  }
  
  public void setMultimedia(Multimedia multimedia) {
    this.multimedia = multimedia;
  }
  
  public Propietario getPropietario() {
    return propietario;
  }
  
  public void setPropietario(Propietario propietario) {
    this.propietario = propietario;
  }
}
