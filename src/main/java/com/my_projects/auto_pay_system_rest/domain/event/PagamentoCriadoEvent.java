package com.my_projects.auto_pay_system_rest.domain.event;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record PagamentoCriadoEvent(
        UUID pagamentoId,
        String cnpjPagador,
        BigDecimal valor,
        LocalDateTime dataOcorrencia
) implements PagamentoEvent{

    @Override
    public UUID getPagamentoId() {
        return pagamentoId;
    }

    @Override
    public LocalDateTime getDataOcorrencia() {
        return dataOcorrencia;
    }
}
