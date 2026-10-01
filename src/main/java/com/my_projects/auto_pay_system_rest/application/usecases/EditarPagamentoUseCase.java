package com.my_projects.auto_pay_system_rest.application.usecases;

import com.my_projects.auto_pay_system_rest.application.dto.EditarPagamentoInput;
import com.my_projects.auto_pay_system_rest.application.ports.out.PagamentoEventPublisherPort;
import com.my_projects.auto_pay_system_rest.application.ports.out.PagamentoRepositoryPort;
import com.my_projects.auto_pay_system_rest.domain.event.PagamentoEditadoEvent;
import com.my_projects.auto_pay_system_rest.domain.model.Pagamento;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class EditarPagamentoUseCase {

    private final PagamentoRepositoryPort repository;
    private final PagamentoEventPublisherPort eventPublisher;

    private static final BigDecimal LIMITE_APROVACAO_AUTOMATICA = new BigDecimal("10000.00");

    public EditarPagamentoUseCase(
            PagamentoRepositoryPort repository,
            PagamentoEventPublisherPort eventPublisher)
    {
        this.repository = repository;
        this.eventPublisher = eventPublisher;
    }

    public Pagamento executar(UUID pagamentoId, EditarPagamentoInput input, String usuarioLogado) {
        //Busca do BD
        Pagamento pagamento = repository.buscarPorId(pagamentoId)
                .orElseThrow(() -> new IllegalArgumentException("Pagamento não encontrado."));

        BigDecimal valorAntigo = pagamento.getValor();

        //Aplica regra de negocio
        pagamento.editarValor(input.novoValor(), usuarioLogado);
        //Reavalia aprovacao com o novo valor
        pagamento.processarAutorizacao(LIMITE_APROVACAO_AUTOMATICA);

        Pagamento pagamentoSalvo = repository.salvar(pagamento);

        PagamentoEditadoEvent eventoAuditoria = new PagamentoEditadoEvent(
                pagamentoSalvo.getId(),
                valorAntigo,
                pagamentoSalvo.getValor(),
                usuarioLogado,
                LocalDateTime.now()
        );
        eventPublisher.publicar(eventoAuditoria);

        return pagamentoSalvo;

    }
}
