package com.psii.app_adapter.Repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import com.psii.app_adapter.Model.Cliente;

@Repository
public interface ClienteRepository extends MongoRepository<Cliente, String> {
    public Optional<Cliente> findByEmail(String email);
    public Optional<Cliente> findByCpf(String cpf);
    public Optional<Cliente> findByTelefone(String telefone);
    public Optional<Cliente> findByNome(String nome);
    
}
