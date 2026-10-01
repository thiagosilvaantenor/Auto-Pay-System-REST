package com.my_projects.auto_pay_system_rest.infrastructure.adapter;

import com.my_projects.auto_pay_system_rest.application.ports.out.PagamentoRepositoryPort;
import com.my_projects.auto_pay_system_rest.domain.model.Pagamento;
import com.my_projects.auto_pay_system_rest.infrastructure.persistence.entity.PagamentoJpaEntity;
import com.my_projects.auto_pay_system_rest.infrastructure.persistence.repository.SpringDataPagamentoRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Component
public class PagamentoRepositoryAdapter implements PagamentoRepositoryPort {

    private final SpringDataPagamentoRepository repository;

    public PagamentoRepositoryAdapter(SpringDataPagamentoRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional
    public Pagamento salvar(Pagamento pagamento) {
        PagamentoJpaEntity entity = new PagamentoJpaEntity(
                pagamento.getId(),
                pagamento.getCnpjPagador(),
                pagamento.getValor(),
                pagamento.getDestino(),
                pagamento.getStatus(),
                pagamento.getDataCriacao() != null ? pagamento.getDataCriacao() : LocalDateTime.now()
        );
        repository.save(entity);
        return pagamento;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Pagamento> buscarPorId(UUID id) {
        return repository.findById(id)
                .map(this::paraOptionalModel);
    }

    private Pagamento paraOptionalModel(PagamentoJpaEntity entidadeEncontrada) {
        return Pagamento.restaurarDoBanco(
                entidadeEncontrada.getId(),
                entidadeEncontrada.getCnpjPagador(),
                entidadeEncontrada.getValor(),
                entidadeEncontrada.getDestino(),
                entidadeEncontrada.getStatus(),
                entidadeEncontrada.getDataCriacao()
        );
    }
}
