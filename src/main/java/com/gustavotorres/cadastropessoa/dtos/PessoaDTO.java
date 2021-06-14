package com.gustavotorres.cadastropessoa.dtos;

import java.io.Serializable;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.gustavotorres.cadastropessoa.entities.Pessoa;
import com.gustavotorres.cadastropessoa.enums.TipoPessoa;

import org.modelmapper.ModelMapper;
import org.springframework.hateoas.RepresentationModel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PessoaDTO extends RepresentationModel<PessoaDTO> implements Serializable {
    
    private Long id;

    private String idPublico;

    private String nome;

    @NotEmpty
    @Size(min = 11, max = 14)
    @Pattern(regexp="^([0-9]*)$")
    private String numeroDocumento;

    @Max(10)
    @Min(0)
    private Short score;

    @Enumerated(EnumType.STRING)
    private TipoPessoa tipoPessoa;

    public static PessoaDTO create(PessoaCadastroInputDTO pessoaCadastroDTO) {
        return new ModelMapper().map(pessoaCadastroDTO, PessoaDTO.class);
    }

    public static PessoaDTO create(Pessoa pessoa) {
        return new ModelMapper().map(pessoa, PessoaDTO.class);
    }
}
