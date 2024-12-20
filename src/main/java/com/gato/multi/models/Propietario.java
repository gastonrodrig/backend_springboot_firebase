package com.gato.multi.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Propietario")
public class Propietario {
  @Id
  @JsonProperty("_id")
  private String id;
  
  private String nombre;
  private String telefono;
  private String email;
  private String direccion;
  
  // Constructores
  public Propietario() {}
  
  public Propietario(String nombre, String telefono, String email, String direccion) {
    this.nombre = nombre;
    this.telefono = telefono;
    this.email = email;
    this.direccion = direccion;
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
  
  public String getTelefono() {
    return telefono;
  }
  
  public void setTelefono(String telefono) {
    this.telefono = telefono;
  }
  
  public String getEmail() {
    return email;
  }
  
  public void setEmail(String email) {
    this.email = email;
  }
  
  public String getDireccion() {
    return direccion;
  }
  
  public void setDireccion(String direccion) {
    this.direccion = direccion;
  }
}
