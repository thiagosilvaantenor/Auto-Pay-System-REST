package com.my_projects.auto_pay_system_rest.presentation.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.hibernate.validator.constraints.br.CNPJ;

import java.math.BigDecimal;

public record PagamentoRequest(
        @CNPJ(message = "O CNPJ do pagador é obrigatório.")
        String cnpjPagador,

        @NotNull(message = "O valor é obrigatório.")
        @Positive(message = "O valor deve ser maior que zero.")
        BigDecimal valor,

        @NotBlank(message = "O destino é obrigatório.")
        String destino
) {
}
