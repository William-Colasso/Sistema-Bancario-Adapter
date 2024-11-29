package com.psii.app_adapter.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.psii.app_adapter.Model.Cliente;

public class SistemaPix {
    
    @Autowired
    private ClienteService clienteService;

    public String pagarPix(String chavePix, double valor) {
        

        List<Cliente>clientes=clienteService.getAllClientes();

        for(Cliente cliente : clientes){
            if(cliente.getChavesPix().contains(chavePix)){
                cliente.setSaldo(cliente.getSaldo()+valor);
                return "GG";
            }
        }

        return "Chave não encontrada.";
    }
}