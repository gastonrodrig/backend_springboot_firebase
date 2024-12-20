package com.gato.multi.services.Supabase;

import okhttp3.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Service
public class SupabaseService {
  
  @Value("${supabase.url}")
  private String supabaseUrl;
  
  @Value("${supabase.api.key}")
  private String supabaseApiKey;
  
  @Value("${supabase.bucket}")
  private String bucketName;
  
  private final OkHttpClient client = new OkHttpClient();
  
  public String uploadImage(String locacion, MultipartFile file) throws IOException {
    // Verificar si el archivo tiene un nombre válido
    String originalFileName = file.getOriginalFilename();
    if (originalFileName == null || originalFileName.isEmpty()) {
      throw new RuntimeException("El archivo no tiene un nombre válido.");
    }
    
    // Quitar espacios en blanco del nombre del archivo
    String sanitizedFileName = originalFileName.replaceAll("[^a-zA-Z0-9._-]", "");
    
    // Generar un número aleatorio de 8 dígitos
    int randomSuffix = (int) (Math.random() * 1_0000_0000);
    
    // Construir el nuevo nombre del archivo con el sufijo aleatorio
    String fileExtension = "";
    int dotIndex = sanitizedFileName.lastIndexOf(".");
    if (dotIndex != -1) {
      fileExtension = sanitizedFileName.substring(dotIndex);
      sanitizedFileName = sanitizedFileName.substring(0, dotIndex);
    }
    
    String uniqueFileName = sanitizedFileName + "_" + randomSuffix + fileExtension;
    
    // Construir la URL para subir el archivo
    String fullUrl = supabaseUrl + "/storage/v1/object/" + bucketName + "/" + locacion + "/" + uniqueFileName;
    
    RequestBody body = RequestBody.create(file.getBytes(), MediaType.parse(file.getContentType()));
    
    Request request = new Request.Builder()
      .url(fullUrl)
      .addHeader("Authorization", "Bearer " + supabaseApiKey)
      .addHeader("apikey", supabaseApiKey)
      .post(body)
      .build();
    
    try (Response response = client.newCall(request).execute()) {
      if (response.isSuccessful()) {
        String publicUrl = supabaseUrl + "/storage/v1/object/public/" + bucketName + "/" + locacion + "/" + uniqueFileName;
        System.out.println("Imagen subida exitosamente: " + publicUrl);
        return publicUrl;
      } else {
        System.err.println("Error al subir imagen. Respuesta: " + response.body().string());
        throw new IOException("Error subiendo imagen: " + response.body().string());
      }
    }
  }
  
  public void deleteImage(String fileUrl) throws IOException {
    // Transformar la URL pública a la URL de la API
    String apiUrl = fileUrl.replace("/public/", "/");
    
    // Construir la solicitud DELETE usando directamente la URL del archivo
    Request request = new Request.Builder()
      .url(apiUrl)
      .addHeader("Authorization", "Bearer " + supabaseApiKey)
      .addHeader("apikey", supabaseApiKey)
      .delete()
      .build();
    
    // Ejecutar la solicitud y manejar la respuesta
    try (Response response = client.newCall(request).execute()) {
      if (response.isSuccessful()) {
        System.out.println("Imagen eliminada exitosamente: " + apiUrl);
      } else {
        throw new IOException("Error eliminando imagen: " + response.body().string());
      }
    }
  }
}
