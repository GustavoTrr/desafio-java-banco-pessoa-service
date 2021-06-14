package com.gustavotorres.cadastropessoa.controllers;

import javax.validation.Valid;

import com.gustavotorres.cadastropessoa.dtos.PessoaCadastroInputDTO;
import com.gustavotorres.cadastropessoa.dtos.PessoaDTO;
import com.gustavotorres.cadastropessoa.services.PessoaService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pessoas")
public class PessoaController {
    
    @Autowired
    private PessoaService pessoaService;

    @Autowired
    private PagedResourcesAssembler<PessoaDTO> assembler;

    @GetMapping(produces = {"application/json","application/xml","application/x-yaml"})
        public ResponseEntity<?> findAll(@RequestParam(value = "page", defaultValue = "0") int page,    
        @RequestParam(value = "size", defaultValue = "12") int size,    
        @RequestParam(value = "sort", defaultValue = "asc") String sort
    ) {

            var sortDirection = "desc".equalsIgnoreCase(sort) ? Direction.DESC : Direction.ASC;

            Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection,"nome"));

            Page<PessoaDTO> pessoas = pessoaService.findAll(pageable);
            
            PagedModel<EntityModel<PessoaDTO>> pagedModel = assembler.toModel(pessoas);

            return new ResponseEntity<>(pagedModel,HttpStatus.OK);

    }

    @GetMapping(value = "/{id}",
                produces = {"application/json","application/xml","application/x-yaml"})
    public ResponseEntity<?> findById(@PathVariable Long id) {
            return new ResponseEntity<>(pessoaService.findById(id),HttpStatus.OK);

    }

    @PostMapping(produces = {"application/json","application/xml","application/x-yaml"},
                consumes = {"application/json","application/xml","application/x-yaml"})
    public PessoaDTO cadastrarPessoa(@Valid @RequestBody PessoaCadastroInputDTO pessoaInputDTO) {
        PessoaDTO pessoaDTORetorno = pessoaService.cadastrarPessoa(pessoaInputDTO);

        return pessoaDTORetorno;
    }

}
