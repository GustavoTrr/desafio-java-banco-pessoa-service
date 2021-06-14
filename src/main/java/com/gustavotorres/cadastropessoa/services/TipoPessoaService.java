package com.gustavotorres.cadastropessoa.services;

import javax.validation.ValidationException;

import com.gustavotorres.cadastropessoa.enums.TipoPessoa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TipoPessoaService {

    @Autowired
    TipoDocumentoService tipoDocumentoService;
    
    public TipoPessoa definirTipoPessoaPorDocumento(String numeroDocumento) {
        if (tipoDocumentoService.isCPFValido(numeroDocumento)) {
            return TipoPessoa.PF;
        } else if (tipoDocumentoService.isCNPJValido(numeroDocumento)) {
            return TipoPessoa.PJ;
        } else {
            throw new ValidationException("Tipo de Documento não aceito!");
        }
    } 
}
