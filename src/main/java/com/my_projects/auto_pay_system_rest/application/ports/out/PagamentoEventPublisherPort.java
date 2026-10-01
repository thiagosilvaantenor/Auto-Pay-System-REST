package com.my_projects.auto_pay_system_rest.application.ports.out;

import com.my_projects.auto_pay_system_rest.domain.event.PagamentoEvent;

// Porta para a Mensageria (Filas/Topicos)
public interface PagamentoEventPublisherPort {
    void publicar(PagamentoEvent event);
}
