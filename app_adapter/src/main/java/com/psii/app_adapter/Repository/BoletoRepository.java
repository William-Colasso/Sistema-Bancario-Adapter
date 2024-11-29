package com.psii.app_adapter.Repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import com.psii.app_adapter.Model.Boleto;

@Repository
public interface BoletoRepository extends MongoRepository<Boleto, String> {
}
