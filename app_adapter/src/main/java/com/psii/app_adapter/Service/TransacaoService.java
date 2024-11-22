package com.psii.app_adapter.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.psii.app_adapter.Model.Transacao;
import com.psii.app_adapter.Repository.TransacaoRepository;

import java.util.List;
import java.util.Optional;

@Service
public class TransacaoService {

    @Autowired
    private TransacaoRepository transacaoRepository;

    // Método para buscar todas as transações
    public List<Transacao> getAllTransacoes() {
        return transacaoRepository.findAll();
    }

    // Método para buscar uma transação por ID
    public Optional<Transacao> getTransacaoById(String id) {
        return transacaoRepository.findById(id);
    }

    // Método para criar uma nova transação
    public Transacao createTransacao(Transacao transacao) {
        return transacaoRepository.save(transacao);
    }

    // Método para atualizar uma transação
    public Optional<Transacao> updateTransacao(String id, Transacao transacao) {
        if (transacaoRepository.existsById(id)) {
            transacao.setId(id); // Atribui o ID da transação para garantir a atualização correta
            return Optional.of(transacaoRepository.save(transacao));
        }
        return Optional.empty();
    }

    // Método para deletar uma transação
    public void deleteTransacao(String id) {
        if (transacaoRepository.existsById(id)) {
            transacaoRepository.deleteById(id);
            return ;
        }
        return ;
    }
}
