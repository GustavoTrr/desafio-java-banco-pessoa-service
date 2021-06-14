package com.gustavotorres.cadastropessoa.exceptions;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@JsonPropertyOrder({"field","parameter","message"})
public class ParameterValidationObject {

    private final String message;
    private final String field;
    private final Object parameter;
}