package com.gustavotorres.cadastropessoa.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.gustavotorres.cadastropessoa.dtos.PessoaDTO;
import com.gustavotorres.cadastropessoa.enums.TipoPessoa;

import org.modelmapper.ModelMapper;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "pessoas")
public class Pessoa {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "id_publico", unique = true)
    private String idPublico;

    @Column(name = "nome")
    private String nome;

    @NotEmpty
    @Size(min = 11, max = 14)
    @Pattern(regexp="^([0-9]*)$")
    @Column(name = "numero_documento", length = 14, nullable = false, unique = true)
    private String numeroDocumento;

    @Max(10)
    @Min(0)
    @Column(name = "score")
    private Short score;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_pessoa", length = 2, nullable = false)
    private TipoPessoa tipoPessoa;

    public static Pessoa create(PessoaDTO pessoaDTO) {
        return new ModelMapper().map(pessoaDTO, Pessoa.class);
    }
}
