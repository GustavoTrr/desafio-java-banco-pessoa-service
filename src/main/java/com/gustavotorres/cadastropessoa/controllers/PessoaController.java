package com.gustavotorres.cadastropessoa.controllers;

import javax.validation.Valid;

import com.gustavotorres.cadastropessoa.dtos.PessoaCadastroInputDTO;
import com.gustavotorres.cadastropessoa.dtos.PessoaDTO;
import com.gustavotorres.cadastropessoa.services.PessoaService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pessoas")
public class PessoaController {
    
    @Autowired
    private PessoaService pessoaService;

    @GetMapping(produces = {"application/json","application/xml","application/x-yaml"})
    public ResponseEntity<?> findAll(
        ) {

            return new ResponseEntity<>(pessoaService.findAll(),HttpStatus.OK);

    }

    @PostMapping(produces = {"application/json","application/xml","application/x-yaml"},
                consumes = {"application/json","application/xml","application/x-yaml"})
    public PessoaDTO cadastrarPessoa(@Valid @RequestBody PessoaCadastroInputDTO pessoaInputDTO) {
        PessoaDTO pessoaDTORetorno = pessoaService.cadastrarPessoa(pessoaInputDTO);

        return pessoaDTORetorno;
    }

}
