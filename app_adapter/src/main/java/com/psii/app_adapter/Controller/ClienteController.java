package com.psii.app_adapter.Controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.psii.app_adapter.Model.Cliente;
import com.psii.app_adapter.Service.ClienteService;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @GetMapping("/")
    public String getClientes(Model model){


        return "geral";
    }
    
}
