package com.gustavotorres.cadastropessoa.dtos;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PessoaCadastroInputDTO {

    @NotEmpty
    private String nome;

    @NotEmpty
    @Size(min = 11, max = 14)
    @Pattern(regexp="^([0-9]*)$")
    private String numeroDocumento;

}
