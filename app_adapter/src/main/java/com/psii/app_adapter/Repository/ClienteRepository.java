package com.psii.app_adapter.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import com.psii.app_adapter.Model.Cliente;

@Repository
public interface ClienteRepository extends MongoRepository<Cliente, String> {
}
