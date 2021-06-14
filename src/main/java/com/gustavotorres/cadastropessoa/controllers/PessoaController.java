package com.gustavotorres.cadastropessoa.controllers;

import com.gustavotorres.cadastropessoa.dtos.PessoaDTO;
import com.gustavotorres.cadastropessoa.services.PessoaService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

    
    @GetMapping(value = "/{id}", produces = {"application/json","application/xml","application/x-yaml"})
    public PessoaDTO findByID(@PathVariable("id") Long id) {
        PessoaDTO pessoaDTO = pessoaService.findById(id);

        return pessoaDTO;
    }

    @PostMapping(produces = {"application/json","application/xml","application/x-yaml"},
                consumes = {"application/json","application/xml","application/x-yaml"})
    public PessoaDTO create(@RequestBody PessoaDTO pessoaDTO) {
        PessoaDTO pessoaDTORetorno = pessoaService.create(pessoaDTO);

        return pessoaDTORetorno;
    }

    @PutMapping(value = "/{id}", 
                produces = {"application/json","application/xml","application/x-yaml"},
                consumes = {"application/json","application/xml","application/x-yaml"})
    public PessoaDTO update(@PathVariable("id") Long id, @RequestBody PessoaDTO pessoaDTO) {
        PessoaDTO pessoaDTORetorno = pessoaService.update(id, pessoaDTO);

        return pessoaDTORetorno;
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        pessoaService.delete(id);
        return ResponseEntity.ok().build();
    }
}
