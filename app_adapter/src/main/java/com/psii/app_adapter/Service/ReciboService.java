package com.psii.app_adapter.Service;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import com.psii.app_adapter.Model.Boleto;
import com.psii.app_adapter.Model.Cliente;
import com.psii.app_adapter.Model.Recibo;
import com.psii.app_adapter.Repository.ReciboRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ReciboService {

    @Autowired
    private ReciboRepository reciboRepository;

    public void createRecibo(Recibo recibo){
        reciboRepository.save(recibo);
    }

    public Optional<Recibo> findById(String id) {
        return findById(id);
    }

    public List<Recibo> findAll() {
        return reciboRepository.findAll();
    }


    public void deleteRecibo(String id) {
        if (reciboRepository.existsById(id)) {
            reciboRepository.deleteById(id);
        }
    }

    public List<Recibo> findByIdOrigem(String id){
        return reciboRepository.findByIdOrigem(id);
    }

    public List<Recibo> findByIdDestino(String id){
        return reciboRepository.findByIdDestino(id);
    }

    

}
