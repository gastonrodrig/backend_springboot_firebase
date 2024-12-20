package com.gato.multi.repositories;

import com.gato.multi.models.Propietario;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PropietarioRepository extends MongoRepository<Propietario, String> {
}
