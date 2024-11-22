package com.psii.app_adapter.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.psii.app_adapter.Model.Transacao;

@Repository
public interface TransacaoRepository extends MongoRepository<Transacao, String> {
    // Aqui você pode adicionar métodos personalizados, se necessário
}
