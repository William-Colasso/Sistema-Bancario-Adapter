package com.psii.app_adapter.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.psii.app_adapter.Model.Conta;
import com.psii.app_adapter.Service.ContaService;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping
public class ContaController {

    @Autowired
    private ContaService contaService;



    @GetMapping("conta/cadastro/{id}")
    public String getCadastro(@PathVariable Long id, Model model){
        

        return "conta/cadastro";
    }
    
}
