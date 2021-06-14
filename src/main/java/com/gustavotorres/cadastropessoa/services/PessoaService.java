package com.gustavotorres.cadastropessoa.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.gustavotorres.cadastropessoa.dtos.PessoaDTO;
import com.gustavotorres.cadastropessoa.entities.Pessoa;
import com.gustavotorres.cadastropessoa.repositories.PessoaRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class PessoaService {
    
    @Autowired PessoaRepository pessoaRepository;

    public PessoaDTO cadastrarPessoa() {
        return new PessoaDTO();
    }

    public List<PessoaDTO> findAll() {
        return pessoaRepository.findAll().stream().map(this::convertToPessoaDTO).collect(Collectors.toList());
    }

    public PessoaDTO create(PessoaDTO pessoaDTO) {
        
        Pessoa pessoa = pessoaRepository.save(Pessoa.create(pessoaDTO));

        return PessoaDTO.create(pessoa);

    }

    public PessoaDTO findById(Long id) {
        var pessoa = pessoaRepository.findById(id).orElse(null);

        return  PessoaDTO.create(pessoa);
    }

    public PessoaDTO update(Long id, PessoaDTO pessoaDTO) {
        final Optional<Pessoa> optionalPessoa = pessoaRepository.findById(id);
        
        pessoaDTO.setId(id);

        if (!optionalPessoa.isPresent()) {
        }

        return PessoaDTO.create(pessoaRepository.save(Pessoa.create(pessoaDTO)));

    }

    public void delete(Long id) {
        var pessoa = pessoaRepository.findById(id).orElse(null);

        pessoaRepository.delete(pessoa);
    }

    public Page<PessoaDTO> findAll(Pageable pageable) {
        var page = pessoaRepository.findAll(pageable);
        return page.map(this::convertToPessoaDTO);
    }

    private PessoaDTO convertToPessoaDTO(Pessoa pessoa) {
        return PessoaDTO.create(pessoa);
    }
}
