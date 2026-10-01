package com.my_projects.auto_pay_system_rest.presentation.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record EditarPagamentoRequest(
        @NotNull(message = "O novo valor é obrigatório.")
        @Positive(message = "O valor deve ser maior qie zero.")
        BigDecimal novoValor
) {
}
