package com.psii.app_adapter.Service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.psii.app_adapter.Model.Cliente;

@Service
public class SistemaPix {

    private final ClienteService clienteService;

    public SistemaPix(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    public String pagarPix(String chavePix, double valor) {



        try{
            List<Cliente> clientes = clienteService.getAllClientes();

        for (Cliente cliente : clientes) {
            for(String chave : cliente.getChavesPix()){
                if(chave.equals(chavePix)){
                    cliente.setSaldo(cliente.getSaldo()+valor);
                    clienteService.createCliente(cliente);
                    return "GG";
                }
            }
        }
        }catch(Error e){
            e.printStackTrace();
        }
        
        return "Chave não encontrada.";
        
    }

}