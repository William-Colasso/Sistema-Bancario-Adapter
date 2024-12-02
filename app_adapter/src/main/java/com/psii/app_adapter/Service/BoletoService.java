package com.psii.app_adapter.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.psii.app_adapter.Model.Boleto;
import com.psii.app_adapter.Repository.BoletoRepository;
import java.util.List;
import java.util.Optional;

@Service
public class BoletoService {


    @Autowired
    private BoletoRepository boletoRepository;




    // Método para buscar todos os clientes
    public List<Boleto> getAllBoletos() {
        return boletoRepository.findAll();
    }

    // Método para buscar um cliente por ID
    public Optional<Boleto> getBoletoById(String id) {
        return boletoRepository.findById(id);
    }

    // Método para criar um novo cliente
    public Boleto createBoleto(Boleto boleto) {
        return boletoRepository.save(boleto);
    }

    // Método para atualizar um cliente
    public Optional<Boleto> updateBoleto(String id, Boleto boleto) {
        if (boletoRepository.existsById(id)) {
            boleto.setId(id); // Atribui o ID do boleto para garantir a atualização correta
            return Optional.of(boletoRepository.save(boleto));
        }
        return Optional.empty();
    }

    // Método para deletar um cliente
    public void deleteBoleto(String id) {
        if (boletoRepository.existsById(id)) {
            boletoRepository.deleteById(id);
            return;
        }
        return;
    }


    public List<Boleto> findByIdCliente(String id){
        return boletoRepository.findByIdCliente(id);
    }

}
