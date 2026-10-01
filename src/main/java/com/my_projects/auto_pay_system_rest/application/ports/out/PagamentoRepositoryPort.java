package com.my_projects.auto_pay_system_rest.application.ports.out;

import com.my_projects.auto_pay_system_rest.domain.model.Pagamento;

import java.util.Optional;
import java.util.UUID;

//Porta para o BD
public interface PagamentoRepositoryPort {
    Pagamento salvar(Pagamento pagamento);
    Optional<Pagamento> buscarPorId(UUID id);
}
