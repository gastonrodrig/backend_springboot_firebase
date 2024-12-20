package com.gato.multi.services;

import okhttp3.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class SupabaseService {
  
  @Value("${supabase.url}")
  private String supabaseUrl;
  
  @Value("${supabase.api.key}")
  private String supabaseApiKey;
  
  @Value("${supabase.bucket}")
  private String bucketName;
  
  private final OkHttpClient client = new OkHttpClient();
  
  public String uploadImage(MultipartFile file) throws IOException {
    String fileName = file.getOriginalFilename();
    if (fileName == null || fileName.isEmpty()) {
      throw new RuntimeException("El archivo no tiene un nombre válido.");
    }
    
    String fullUrl = supabaseUrl + "/storage/v1/object/prueba/" + fileName;
    System.out.println("URL generada: " + fullUrl);
    
    RequestBody body = RequestBody.create(file.getBytes(), MediaType.parse(file.getContentType()));
    
    Request request = new Request.Builder()
      .url(fullUrl)
      .addHeader("Authorization", "Bearer " + supabaseApiKey)
      .addHeader("apikey", supabaseApiKey)
      .post(body)
      .build();
    
    try (Response response = client.newCall(request).execute()) {
      if (response.isSuccessful()) {
        String publicUrl = supabaseUrl + "/storage/v1/object/public/" + bucketName + "/" + fileName;
        System.out.println("Imagen subida exitosamente: " + publicUrl);
        return publicUrl;
      } else {
        System.err.println("Error al subir imagen. Respuesta: " + response.body().string());
        throw new IOException("Error subiendo imagen: " + response.body().string());
      }
    }
  }
}
