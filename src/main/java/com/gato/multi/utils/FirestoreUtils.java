package com.gato.multi.utils;

import com.gato.multi.interfaces.HasId;
import com.google.cloud.firestore.DocumentReference;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

public class FirestoreUtils {
  
  /**
   * Obtiene un documento desde Firestore y lo convierte en una clase específica.
   *
   * @param collectionName Nombre de la colección en Firestore.
   * @param documentId     ID del documento.
   * @param clazz          Clase a la que se convertirá el documento.
   * @param <T>            Tipo genérico.
   * @return El objeto convertido.
   */
  public static <T> T fetchDocument(String collectionName, String documentId, Class<T> clazz) {
    try {
      return Optional.ofNullable(
          FirestoreClient.getFirestore()
            .collection(collectionName)
            .document(documentId)
            .get()
            .get()
            .toObject(clazz))
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
          collectionName + " no encontrado con ID: " + documentId));
    } catch (InterruptedException | ExecutionException e) {
      Thread.currentThread().interrupt();
      throw new RuntimeException("Error al obtener el documento: " + e.getMessage(), e);
    }
  }
  
  /**
   * Valida si un documento existe en Firestore.
   *
   * @param collectionName Nombre de la colección en Firestore.
   * @param documentId     ID del documento.
   */
  public static void validateDocumentExists(String collectionName, String documentId) {
    try {
      boolean exists = FirestoreClient.getFirestore()
        .collection(collectionName)
        .document(documentId)
        .get()
        .get()
        .exists();
      
      if (!exists) {
        throw new ResponseStatusException(HttpStatus.NOT_FOUND,
          collectionName + " no encontrado con ID: " + documentId);
      }
    } catch (InterruptedException | ExecutionException e) {
      Thread.currentThread().interrupt();
      throw new RuntimeException("Error al validar la existencia del documento: " + e.getMessage(), e);
    }
  }
  
  /**
   * Guarda un documento en Firestore y devuelve el ID del documento generado.
   *
   * @param collectionName Nombre de la colección en Firestore.
   * @param data           Objeto a guardar.
   * @return ID del documento generado.
   */
  public static String saveDocument(String collectionName, Object data) {
    try {
      DocumentReference docRef = FirestoreClient.getFirestore()
        .collection(collectionName)
        .document();
      docRef.set(data).get();
      return docRef.getId();
    } catch (InterruptedException | ExecutionException e) {
      Thread.currentThread().interrupt();
      throw new RuntimeException("Error al guardar el documento: " + e.getMessage(), e);
    }
  }
  
  /**
   * Actualiza un documento en Firestore usando un ID específico.
   *
   * @param collectionName Nombre de la colección en Firestore.
   * @param documentId     ID del documento a actualizar.
   * @param document       Objeto con los datos actualizados.
   * @param <T>            Tipo genérico del documento.
   */
  public static <T> void updateDocument(String collectionName, String documentId, T document) {
    try {
      FirestoreClient.getFirestore()
        .collection(collectionName)
        .document(documentId)
        .set(document)
        .get();
    } catch (InterruptedException | ExecutionException e) {
      throw new RuntimeException("Error al actualizar el documento en Firestore: " + e.getMessage(), e);
    }
  }
  
  /**
   * Elimina un documento de Firestore.
   *
   * @param collectionName Nombre de la colección en Firestore.
   * @param documentId     ID del documento a eliminar.
   */
  public static void deleteDocument(String collectionName, String documentId) {
    try {
      FirestoreClient.getFirestore()
        .collection(collectionName)
        .document(documentId)
        .delete()
        .get();
    } catch (InterruptedException | ExecutionException e) {
      Thread.currentThread().interrupt();
      throw new RuntimeException("Error al eliminar el documento: " + e.getMessage(), e);
    }
  }
  
  /**
   * Obtiene todos los documentos de una colección y los convierte a una lista de una clase específica.
   *
   * @param collectionName Nombre de la colección en Firestore.
   * @param clazz          Clase a la que se convertirán los documentos.
   * @param <T>            Tipo genérico.
   * @return Lista de objetos convertidos.
   */
  public static <T> List<T> fetchAllDocuments(String collectionName, Class<T> clazz) {
    try {
      return FirestoreClient.getFirestore()
        .collection(collectionName)
        .get()
        .get()
        .toObjects(clazz);
    } catch (InterruptedException | ExecutionException e) {
      Thread.currentThread().interrupt();
      throw new RuntimeException("Error al obtener los documentos: " + e.getMessage(), e);
    }
  }
  
  /**
   * Guarda un documento en una colección de Firestore con un ID generado automáticamente.
   *
   * @param collectionName Nombre de la colección en Firestore donde se guardará el documento.
   * @param document       Objeto del documento a guardar.
   * @param <T>            Tipo genérico del documento.
   * @return El ID generado automáticamente para el documento.
   * @throws RuntimeException Si ocurre un error al guardar el documento en Firestore.
   */
  public static <T> String saveDocumentWithGeneratedId(String collectionName, T document) {
    try {
      // Generar documento con ID automático
      var documentReference = FirestoreClient.getFirestore()
        .collection(collectionName)
        .document();
      
      // Extraer el ID generado
      String generatedId = documentReference.getId();
      
      // Asignar el ID al documento (si tiene un setter para "id")
      if (document instanceof HasId) {
        ((HasId) document).setId(generatedId);
      }
      
      // Guardar el documento en Firestore
      documentReference.set(document).get();
      
      return generatedId; // Devolver el ID generado
    } catch (InterruptedException | ExecutionException e) {
      throw new RuntimeException("Error al guardar el documento en Firestore: " + e.getMessage(), e);
    }
  }
  
}
