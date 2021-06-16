package com.gustavotorres.cadastropessoa.dtos;

import java.io.Serializable;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.gustavotorres.cadastropessoa.enums.TipoPessoa;

import org.modelmapper.ModelMapper;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class PessoaContaMessageDTO implements Serializable {
    
    @NotEmpty
    private String idPublico;

    @Max(9)
    @Min(0)
    @NotNull
    private Short score;

    @Enumerated(EnumType.STRING)
    @NotEmpty
    @Size(min = 2, max = 2)
    private TipoPessoa tipoPessoa;

    public static PessoaContaMessageDTO create(PessoaDTO pessoaDTO) {
        return new ModelMapper().map(pessoaDTO, PessoaContaMessageDTO.class);
    }
}
