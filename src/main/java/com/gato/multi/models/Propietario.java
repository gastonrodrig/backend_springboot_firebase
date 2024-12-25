package com.gato.multi.models;

import com.gato.multi.interfaces.HasId;

public class Propietario implements HasId {
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
  
  // Interfaz HasId
  @Override
  public String getId() {
    return id;
  }
  
  @Override
  public void setId(String id) {
    this.id = id;
  }
  
  // Getters y setters
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
