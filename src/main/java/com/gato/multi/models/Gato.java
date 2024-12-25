package com.gato.multi.models;

import com.gato.multi.interfaces.HasId;

public class Gato implements HasId {
  private String id;
  private String nombre;
  private String tamano;
  private String multimedia; // ID de la colección Multimedia
  private String propietario; // ID de la colección Propietario
  
  // Constructores
  public Gato() {}
  
  public Gato(String nombre, String tamano, String multimedia, String propietario) {
    this.nombre = nombre;
    this.tamano = tamano;
    this.multimedia = multimedia;
    this.propietario = propietario;
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
  
  public String getTamano() {
    return tamano;
  }
  
  public void setTamano(String tamano) {
    this.tamano = tamano;
  }
  
  public String getMultimedia() {
    return multimedia;
  }
  
  public void setMultimedia(String multimedia) {
    this.multimedia = multimedia;
  }
  
  public String getPropietario() {
    return propietario;
  }
  
  public void setPropietario(String propietario) {
    this.propietario = propietario;
  }
}
