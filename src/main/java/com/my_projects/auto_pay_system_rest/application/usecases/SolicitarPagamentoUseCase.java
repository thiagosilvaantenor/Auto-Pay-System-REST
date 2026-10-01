package com.my_projects.auto_pay_system_rest.application.usecases;

import com.my_projects.auto_pay_system_rest.application.dto.SolicitarPagamentoDto;
import com.my_projects.auto_pay_system_rest.application.ports.out.PagamentoEventPublisherPort;
import com.my_projects.auto_pay_system_rest.application.ports.out.PagamentoRepositoryPort;
import com.my_projects.auto_pay_system_rest.domain.model.Pagamento;
import com.my_projects.auto_pay_system_rest.domain.event.PagamentoCriadoEvent;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
public class SolicitarPagamentoUseCase {

    private final PagamentoRepositoryPort repository;
    private final PagamentoEventPublisherPort eventPublisher;

    // Limite definido pela Regra de Negóci
    private static final BigDecimal LIMITE_APROVACAO_AUTOMATICA = new BigDecimal("10000.00");

    public SolicitarPagamentoUseCase(
            PagamentoRepositoryPort repository,
            PagamentoEventPublisherPort eventPublisher)
    {
        this.repository = repository;
        this.eventPublisher = eventPublisher;
    }

    public Pagamento executar(SolicitarPagamentoDto dto){
        Pagamento pagamento = new Pagamento(dto.cnpjPagador(), dto.valor(), dto.destino());

        pagamento.processarAutorizacao(LIMITE_APROVACAO_AUTOMATICA);

        Pagamento pagamentoSalvo = repository.salvar(pagamento);

        PagamentoCriadoEvent evento = new PagamentoCriadoEvent(
                pagamento.getId(),
                pagamento.getCnpjPagador(),
                pagamento.getValor(),
                LocalDateTime.now()
        );
        eventPublisher.publicar(evento);
        return pagamentoSalvo;
    }

}
