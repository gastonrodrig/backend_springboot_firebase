package com.gato.multi.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Multimedia")
public class Multimedia {
  @Id
  @JsonProperty("_id")
  private String id;
  
  private String nombre;
  
  private String tipo;
  
  private String url;
  
  // Constructores
  public Multimedia() {}
  
  public Multimedia(String nombre, String tipo, String url) {
    this.nombre = nombre;
    this.tipo = tipo;
    this.url = url;
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
  
  public String getTipo() {
    return tipo;
  }
  
  public void setTipo(String tipo) {
    this.tipo = tipo;
  }
  
  public String getUrl() {
    return url;
  }
  
  public void setUrl(String url) {
    this.url = url;
  }
}
