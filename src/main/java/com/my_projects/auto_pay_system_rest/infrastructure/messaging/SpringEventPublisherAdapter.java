package com.my_projects.auto_pay_system_rest.infrastructure.messaging;

import com.my_projects.auto_pay_system_rest.application.ports.out.PagamentoEventPublisherPort;
import com.my_projects.auto_pay_system_rest.domain.event.PagamentoEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class SpringEventPublisherAdapter implements PagamentoEventPublisherPort {

    private final ApplicationEventPublisher publisher;

    private static final Logger logger = LoggerFactory.getLogger(SpringEventPublisherAdapter.class);

    public SpringEventPublisherAdapter(ApplicationEventPublisher publisher){
        this.publisher = publisher;
    }

    @Override
    public void publicar(PagamentoEvent event) {
        //Spring vai disparar internamente
        logger.info("Disparando evento para o broker simulado: {}", event.getClass().getSimpleName());
        publisher.publishEvent(event);
    }
}
