package com.gustavotorres.cadastropessoa.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.gustavotorres.cadastropessoa.dtos.PessoaDTO;

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

    @Column(name = "id_publico")
    private String idPublico;

    @Column(name = "nome")
    private String nome;

    @Column(name = "numero_documento")
    private String numeroDocumento;

    @Column(name = "score")
    private Short score;

    @Column(name = "tipo_pessoa")
    private String tipoPessoa;

    public static Pessoa create(PessoaDTO pessoaDTO) {
        return new ModelMapper().map(pessoaDTO, Pessoa.class);
    }
}
