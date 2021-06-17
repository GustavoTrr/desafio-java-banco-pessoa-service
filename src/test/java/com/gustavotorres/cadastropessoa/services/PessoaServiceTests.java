package com.gustavotorres.cadastropessoa.services;

import java.util.List;

import javax.validation.ValidationException;

import com.gustavotorres.cadastropessoa.dtos.PessoaCadastroInputDTO;
import com.gustavotorres.cadastropessoa.dtos.PessoaDTO;
import com.gustavotorres.cadastropessoa.entities.Pessoa;
import com.gustavotorres.cadastropessoa.exceptions.ResourceNotFoundException;
import com.gustavotorres.cadastropessoa.messages.CriaContaPessoaMessage;
import com.gustavotorres.cadastropessoa.repositories.PessoaRepository;

import org.junit.Assert;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
public class PessoaServiceTests {
    

    // @InjectMocks
    // private PessoaService pessoaService;

    @Autowired
    private TipoPessoaService tipoPessoaService;

    @Autowired
    private TipoDocumentoService tipoDocumentoService;

    @Mock
    private CriaContaPessoaMessage criaContaPessoaMessage;

    @Autowired
    private PessoaRepository pessoaRepository;

    @Mock
    private Pageable pageable;

    private final PessoaCadastroInputDTO pessoaCadastroDTO = new PessoaCadastroInputDTO("Teste", "93314245076");

    private PessoaService instanciarPessoaService() {
        PessoaService pessoaService = new PessoaService();
        pessoaService.tipoDocumentoService = tipoDocumentoService;
        pessoaService.pessoaRepository = pessoaRepository;
        pessoaService.tipoPessoaService = tipoPessoaService;
        pessoaService.criaContaPessoaMessage = criaContaPessoaMessage;
        return pessoaService;
    }


    @Test 
    @Order(1)
    public void shouldCadastrarPessoa() {
        PessoaService pessoaService = instanciarPessoaService();
        PessoaDTO  pessoaDTO = pessoaService.cadastrarPessoa(this.pessoaCadastroDTO);
        Assert.assertEquals(pessoaDTO.getNome(), this.pessoaCadastroDTO.getNome());
    }

    @Test
    @Order(2)
    public void shouldFindOrFail() {
        PessoaService pessoaService = instanciarPessoaService();
        Pessoa pessoaRetornada = pessoaService.findOrFail(1L);
        Assert.assertEquals(pessoaRetornada.getNome(), this.pessoaCadastroDTO.getNome());
    }

    @Test
    @Order(3)
    public void shouldFindById() {
        PessoaService pessoaService = instanciarPessoaService();
        PessoaDTO pessoaDTORetornada = pessoaService.findById(1L);
        Assert.assertEquals(pessoaDTORetornada.getNome(), this.pessoaCadastroDTO.getNome());
    }

    @Test
    @Order(4)
    public void shouldFindAll() {
        PessoaService pessoaService = instanciarPessoaService();
        List<PessoaDTO> listaRetornada = pessoaService.findAll();
        Assert.assertEquals(listaRetornada.get(0).getNome(), this.pessoaCadastroDTO.getNome());
    }

    @Test
    @Order(5)
    public void shouldFindAllPageable() {
        PessoaService pessoaService = instanciarPessoaService();
        Page<PessoaDTO> pagePessoaDTORetornada = pessoaService.findAll(pageable);
        Assert.assertNotNull(pagePessoaDTORetornada);
    }

    @Test
    @Order(6)
    public void shouldFindOrFailThrowNotFoundException() {
        PessoaService pessoaService = instanciarPessoaService();
        Assert.assertThrows(ResourceNotFoundException.class, () -> {
            pessoaService.findOrFail(5L);
        });
    }

    @Test
    @Order(7)
    public void shouldvalidarPessoaParaCadastroDocInvalidoException() {
        
        PessoaService pessoaService = instanciarPessoaService();
        Assert.assertThrows("Número de Documento Inválido.", ValidationException.class, () -> {
            PessoaCadastroInputDTO pessoaDocInvalido = new PessoaCadastroInputDTO("Um Nome", "12345");
            pessoaService.validarPessoaParaCadastro(pessoaDocInvalido);
        });
    }

    @Test
    @Order(8)
    public void shouldvalidarPessoaParaCadastroDocJaCadastradoException() {
        
        PessoaService pessoaService = instanciarPessoaService();
        Assert.assertThrows("Documento já cadastrado.", ValidationException.class, () -> {
            pessoaService.validarPessoaParaCadastro(this.pessoaCadastroDTO);
        });
    }

}
