package com.gato.multi.dtos.Gato;

import jakarta.validation.constraints.NotBlank;

public class GatoUpdateDto extends GatoCreateDto {

  @Override
  @NotBlank
  public String getNombre() {
    return super.getNombre();
  }
  
  @Override
  @NotBlank
  public String getTamanio() {
    return super.getTamanio();
  }
  
  @Override
  @NotBlank
  public String getPropietario_id() {
    return super.getPropietario_id();
  }
}