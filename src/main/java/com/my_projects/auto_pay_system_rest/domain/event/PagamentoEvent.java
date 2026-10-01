package com.my_projects.auto_pay_system_rest.domain.event;

import java.time.LocalDateTime;
import java.util.UUID;

public interface PagamentoEvent {
    UUID getPagamentoId();
    LocalDateTime getDataOcorrencia();
}
