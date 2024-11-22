package com.psii.app_adapter.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.psii.app_adapter.Model.Conta;

@Repository
public interface ContaRepository extends MongoRepository<Conta, String> {
    // Aqui você pode adicionar métodos personalizados, se necessário
}
