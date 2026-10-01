package com.my_projects.auto_pay_system_rest.domain.event;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record PagamentoEditadoEvent(
        UUID pagamentoId,
        BigDecimal valorAntigo,
        BigDecimal valorNovo,
        String usuarioAlteracao,
        LocalDateTime dataOcorrencia
) implements PagamentoEvent {
    @Override
    public UUID getPagamentoId(){
        return pagamentoId;
    }

    @Override
    public LocalDateTime getDataOcorrencia() {
        return dataOcorrencia;
    }
}
