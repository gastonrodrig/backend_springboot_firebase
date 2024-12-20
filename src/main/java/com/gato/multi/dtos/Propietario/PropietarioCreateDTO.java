package com.gato.multi.dtos.Propietario;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class PropietarioCreateDTO {
  @NotBlank
  private String nombre;
  
  @NotBlank
  private String telefono;
  
  @NotBlank
  @Email
  private String email;
  
  @NotBlank
  private String direccion;
  
  // Getters y Setters
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
