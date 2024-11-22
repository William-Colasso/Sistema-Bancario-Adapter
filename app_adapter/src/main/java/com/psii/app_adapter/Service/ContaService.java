package com.psii.app_adapter.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.psii.app_adapter.Model.Conta;
import com.psii.app_adapter.Repository.ContaRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ContaService {

    @Autowired
    private ContaRepository contaRepository;

    // Método para buscar todas as contas
    public List<Conta> getAllContas() {
        return contaRepository.findAll();
    }

    // Método para buscar uma conta por ID
    public Optional<Conta> getContaById(String id) {
        return contaRepository.findById(id);
    }

    // Método para criar uma nova conta
    public Conta createConta(Conta conta) {
        return contaRepository.save(conta);
    }

    // Método para atualizar uma conta
    public Optional<Conta> updateConta(String id, Conta conta) {
        if (contaRepository.existsById(id)) {
            conta.setId(id); // Atribui o ID da conta para garantir a atualização correta
            return Optional.of(contaRepository.save(conta));
        }
        return Optional.empty();
    }

    // Método para deletar uma conta
    public void deleteConta(String id) {
        if (contaRepository.existsById(id)) {
            contaRepository.deleteById(id);
            return ;
        }
        return ;
    }
}
