package com.psii.app_adapter.Controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.psii.app_adapter.Model.Cliente;


import java.util.List;
import java.util.Optional;


@Controller
@RequestMapping
public class PagamentoController {


    @GetMapping("/pagar")
    public String getPagamento(Model model){
        return "/banco/pagar";
    }
}
