package com.gustavotorres.cadastropessoa.dtos;

import com.gustavotorres.cadastropessoa.entities.Pessoa;

import org.modelmapper.ModelMapper;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PessoaDTO {
    
    private Long id;

    private String idPublico;

    private String nome;

    private String numeroDocumento;

    private Short score;

    private String tipoPessoa;

    public static PessoaDTO create(Pessoa pessoa) {
        return new ModelMapper().map(pessoa, PessoaDTO.class);
    }
}
