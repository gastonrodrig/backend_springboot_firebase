package com.gato.multi.services.Firebase;

import com.google.firebase.cloud.StorageClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class FirebaseStorageService {
  @Value("${firebase.storage.bucket}")
  private String bucketName;
  
  public String uploadFile(String locacion, MultipartFile file) throws IOException {
    // Validar y procesar el nombre del archivo
    String originalFileName = file.getOriginalFilename();
    if (originalFileName == null || originalFileName.isEmpty()) {
      throw new RuntimeException("El archivo no tiene un nombre válido.");
    }
    
    // Quitar espacios en blanco del nombre del archivo
    String sanitizedFileName = originalFileName.replaceAll("[^a-zA-Z0-9._-]", "");
    
    // Generar un número aleatorio de 8 dígitos
    int randomSuffix = (int) (Math.random() * 1_0000_0000);
    
    // Construir el nuevo nombre del archivo con el sufijo aleatorio
    String fileExtension = sanitizedFileName.contains(".") ? sanitizedFileName.substring(sanitizedFileName.lastIndexOf(".")) : "";
    String uniqueFileName = sanitizedFileName.replace(fileExtension, "") + "_" + randomSuffix + fileExtension;
    
    // Construir la ruta del archivo
    String filePath = locacion + "/" + uniqueFileName;
    
    // Subir el archivo al bucket
    try {
      StorageClient.getInstance().bucket().create(filePath, file.getBytes(), file.getContentType());
      
      String publicUrl = "https://storage.googleapis.com/" + bucketName + "/" + filePath;
      System.out.println("Imagen subida exitosamente: " + publicUrl);
      return publicUrl;
    } catch (Exception e) {
      System.err.println("Error al subir imagen: " + e.getMessage());
      throw new IOException("Error subiendo imagen: " + e.getMessage(), e);
    }
  }
  
  public void deleteFile(String fileUrl) throws IOException {
    // Extraer la ruta relativa al bucket desde la URL pública
    String filePath = fileUrl.replace("https://storage.googleapis.com/" + bucketName + "/", "");
    
    try {
      // Eliminar el archivo del bucket
      StorageClient.getInstance().bucket().get(filePath).delete();
      
      System.out.println("Archivo eliminado exitosamente: " + fileUrl);
    } catch (Exception e) {
      System.err.println("Error al eliminar imagen: " + e.getMessage());
      throw new IOException("Error eliminando imagen: " + e.getMessage(), e);
    }
  }
}
