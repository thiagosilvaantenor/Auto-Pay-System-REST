package com.my_projects.auto_pay_system_rest.application.dto;

import java.math.BigDecimal;

public record EditarPagamentoInput(
        BigDecimal novoValor
) {}
