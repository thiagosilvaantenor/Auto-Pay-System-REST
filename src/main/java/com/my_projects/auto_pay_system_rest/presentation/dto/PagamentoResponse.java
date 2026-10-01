package com.my_projects.auto_pay_system_rest.presentation.dto;

import java.util.UUID;

public record PagamentoResponse(
        UUID pagamentoId,
        String status,
        String mensagem
) {
}
