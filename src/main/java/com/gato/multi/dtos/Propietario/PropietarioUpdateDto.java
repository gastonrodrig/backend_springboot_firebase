package com.gato.multi.dtos.Propietario;

import jakarta.validation.constraints.NotBlank;

public class PropietarioUpdateDto extends PropietarioCreateDto{
  @Override
  @NotBlank
  public String getNombre() {
    return super.getNombre();
  }
  
  @Override
  @NotBlank
  public String getTelefono() {
    return super.getTelefono();
  }
  
  @Override
  @NotBlank
  public String getEmail() {
    return super.getEmail();
  }
  
  @Override
  @NotBlank
  public String getDireccion() {
    return super.getDireccion();
  }
}
