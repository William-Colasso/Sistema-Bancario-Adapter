package com.psii.app_adapter.Service;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import com.psii.app_adapter.Model.Cliente;
import com.psii.app_adapter.Repository.ClienteRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    // Método para buscar todos os clientes
    public List<Cliente> getAllClientes() {
        return clienteRepository.findAll();
    }

    // Método para buscar um cliente por ID
    public Optional<Cliente> getClienteById(String id) {
        return clienteRepository.findById(id);
    }

    // Método para criar um novo cliente
    public Cliente createCliente(Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    // Método para atualizar um cliente
    public Optional<Cliente> updateCliente(String id, Cliente cliente) {
        if (clienteRepository.existsById(id)) {
            cliente.setId(id); // Atribui o ID do cliente para garantir a atualização correta
            return Optional.of(clienteRepository.save(cliente));
        }
        return Optional.empty();
    }

    // Método para deletar um cliente
    public void deleteCliente(String id) {
        if (clienteRepository.existsById(id)) {
            clienteRepository.deleteById(id);
            return;
        }
        return;
    }

    public Cliente autenticar(String identificador, String senha) {

        Cliente clienteResposta = null;

        List<Cliente> clientes = clienteRepository.findAll();

        for (Cliente cliente : clientes) {
            if (cliente.getEmail().equals(identificador)) {
                clienteResposta = cliente;
            }
        }

        return clienteResposta;

    }

    public Map<String, Object> findByAny(String cpf, String telefone, String nome, String email) {

        Map<String, Object> response = new HashMap<>();

        response.put("CPF", clienteRepository.findByCpf(cpf).isPresent());

        response.put("TELEFONE", clienteRepository.findByTelefone(telefone).isPresent());

        response.put("NOME", clienteRepository.findByNome(nome).isPresent());

        response.put("EMAIL", clienteRepository.findByEmail(email).isPresent());

        return response;
    }

    public Boolean clienteJaCadastrado(Cliente cliente) {
        return findByAny(cliente.getCpf(), cliente.getTelefone(), cliente.getNome(), cliente.getEmail())
                .containsValue(true);
    }

    public Optional<Cliente> findByEmail(String email) {

        return clienteRepository.findByEmail(email);
    }
}
