package com.gato.multi.configurations;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;
import com.google.firebase.cloud.StorageClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.io.FileInputStream;
import java.io.IOException;

@Configuration
public class FirebaseConfig {
  @Value("${firebase.storage.bucket}")
  private String bucketName;
  
  @PostConstruct
  public void initializeFirebase() {
    try {
      // Ruta al archivo de clave de servicio
      FileInputStream serviceAccount = new FileInputStream("src/main/resources/firebase/serviceAccountKey.json");
      
      // Configurar las credenciales y servicios de Firebase
      FirebaseOptions options = FirebaseOptions.builder()
        .setCredentials(GoogleCredentials.fromStream(serviceAccount))
        .setStorageBucket(bucketName) // Configura el bucket de almacenamiento
        .build();
      
      // Inicializar Firebase
      if (FirebaseApp.getApps().isEmpty()) { // Evitar inicializar múltiples veces
        FirebaseApp.initializeApp(options);
      }
      
      // Verificar inicialización de servicios
      FirestoreClient.getFirestore(); // Inicializa Firestore
      StorageClient.getInstance();   // Inicializa Storage
      System.out.println("Firebase inicializado correctamente con Firestore y Storage.");
    } catch (IOException e) {
      throw new RuntimeException("Error al inicializar Firebase", e);
    }
  }
}
