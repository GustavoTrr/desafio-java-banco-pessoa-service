package com.gustavotorres.cadastropessoa.repositories;

import com.gustavotorres.cadastropessoa.entities.Pessoa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PessoaRepository extends JpaRepository<Pessoa, Long> {
    
    public Pessoa findByNumeroDocumento(String numeroDocumento);
}
