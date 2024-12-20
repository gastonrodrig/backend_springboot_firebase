package com.gato.multi.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.gato.multi.models.Gato;

public interface GatoRepository extends MongoRepository<Gato, String> {
}
