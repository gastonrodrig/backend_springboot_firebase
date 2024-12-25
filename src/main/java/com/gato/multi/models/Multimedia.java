package com.gato.multi.models;

import com.gato.multi.interfaces.HasId;

public class Multimedia implements HasId {
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
  
  // Interfaz HasId
  @Override
  public String getId() {
    return id;
  }
  
  @Override
  public void setId(String id) {
    this.id = id;
  }
  
  // Getters y Setters
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

