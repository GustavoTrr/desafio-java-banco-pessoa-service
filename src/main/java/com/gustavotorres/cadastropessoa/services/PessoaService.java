package com.gustavotorres.cadastropessoa.services;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.ValidationException;

import com.gustavotorres.cadastropessoa.dtos.PessoaCadastroInputDTO;
import com.gustavotorres.cadastropessoa.dtos.PessoaDTO;
import com.gustavotorres.cadastropessoa.entities.Pessoa;
import com.gustavotorres.cadastropessoa.exceptions.ResourceNotFoundException;
import com.gustavotorres.cadastropessoa.repositories.PessoaRepository;
import com.gustavotorres.cadastropessoa.utils.HashUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class PessoaService {
    
    @Autowired PessoaRepository pessoaRepository;

    @Autowired
    TipoPessoaService tipoPessoaService;

    @Autowired
    TipoDocumentoService tipoDocumentoService;

    public PessoaDTO cadastrarPessoa(PessoaCadastroInputDTO pessoaCadastroDTO) {
        
        validarPessoaParaCadastro(pessoaCadastroDTO);
        
        Pessoa pessoaParaCriar = prepararPessoaParaCadastro(pessoaCadastroDTO);

        Pessoa pessoaCriada = pessoaRepository.save(pessoaParaCriar);

        // @TODO envia requisição assincrona para criar conta

        return PessoaDTO.create(pessoaCriada);
    }

    public void validarPessoaParaCadastro(PessoaCadastroInputDTO pessoaCadastroDTO) {

        var numDoc = pessoaCadastroDTO.getNumeroDocumento();

        if (!tipoDocumentoService.isDocumentoValido(numDoc)) {
            throw new ValidationException("Número de Documento Inválido.");
        }

        if (pessoaRepository.findByNumeroDocumento(numDoc) != null) {
            throw new ValidationException("Documento já cadastrado.");
        }
    }

    public Pessoa prepararPessoaParaCadastro(PessoaCadastroInputDTO pessoaCadastroDTO) {

        var pessoaDTO = PessoaDTO.create(pessoaCadastroDTO);
        var tipoPessoa = tipoPessoaService.definirTipoPessoaPorDocumento(pessoaDTO.getNumeroDocumento());

        pessoaDTO.setTipoPessoa(tipoPessoa);
        pessoaDTO.setScore(ScoreService.gerarScoreRandomico());
        pessoaDTO.setIdPublico(gerarIdPublico(pessoaDTO));

        return Pessoa.create(pessoaDTO);
    }

    private String gerarIdPublico(PessoaDTO pessoaDTO) {
        return HashUtils.getHashMd5(pessoaDTO.getNome() + pessoaDTO.getNumeroDocumento());
    }

    public List<PessoaDTO> findAll() {
        return pessoaRepository.findAll().stream().map(this::convertToPessoaDTO).collect(Collectors.toList());
    }

    public Pessoa findOrFail(Long id) {
        var pessoa = pessoaRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Pessoa de id = " + id + " não encontrada."));

        return  pessoa;
    }

    public PessoaDTO findById(Long id) {
        return PessoaDTO.create(findOrFail(id));
    }

    public Page<PessoaDTO> findAll(Pageable pageable) {
        var page = pessoaRepository.findAll(pageable);
        return page.map(this::convertToPessoaDTO);
    }

    private PessoaDTO convertToPessoaDTO(Pessoa pessoa) {
        return PessoaDTO.create(pessoa);
    }
}
