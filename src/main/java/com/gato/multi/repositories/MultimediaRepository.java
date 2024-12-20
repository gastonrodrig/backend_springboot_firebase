package com.gato.multi.repositories;

import com.gato.multi.models.Multimedia;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MultimediaRepository extends MongoRepository<Multimedia, String> {
}
