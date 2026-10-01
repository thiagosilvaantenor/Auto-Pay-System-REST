package com.my_projects.auto_pay_system_rest.presentation.controller;


import com.my_projects.auto_pay_system_rest.application.dto.EditarPagamentoInput;
import com.my_projects.auto_pay_system_rest.application.dto.SolicitarPagamentoDto;
import com.my_projects.auto_pay_system_rest.application.usecases.EditarPagamentoUseCase;
import com.my_projects.auto_pay_system_rest.application.usecases.SolicitarPagamentoUseCase;
import com.my_projects.auto_pay_system_rest.domain.model.Pagamento;
import com.my_projects.auto_pay_system_rest.presentation.dto.EditarPagamentoRequest;
import com.my_projects.auto_pay_system_rest.presentation.dto.PagamentoRequest;
import com.my_projects.auto_pay_system_rest.presentation.dto.PagamentoResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/pagamentos")
public class PagamentoController {

    private final SolicitarPagamentoUseCase solicitarPagamentoUseCase;
    private final EditarPagamentoUseCase editarPagamentoUseCase;

    public PagamentoController(
            SolicitarPagamentoUseCase solicitarPagamentoUseCase,
            EditarPagamentoUseCase editarPagamentoUseCase)
    {
        this.solicitarPagamentoUseCase = solicitarPagamentoUseCase;
        this.editarPagamentoUseCase = editarPagamentoUseCase;
    }

    @PostMapping
    public ResponseEntity<PagamentoResponse> solicitar(@Valid @RequestBody PagamentoRequest requisicao) {

        SolicitarPagamentoDto dto = new SolicitarPagamentoDto(
                requisicao.cnpjPagador(),
                requisicao.valor(),
                requisicao.destino()
        );

        Pagamento pagamento = solicitarPagamentoUseCase.executar(dto);

        PagamentoResponse resposta = new PagamentoResponse(
                pagamento.getId(),
                pagamento.getStatus().name(),
                "Pagamento processado com sucesso."
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(resposta);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PagamentoResponse> editar(
            @PathVariable UUID id,
            @RequestHeader(value = "X-Usuario-Logado", defaultValue = "SISTEMA") String usuarioLogado,
            @Valid @RequestBody EditarPagamentoRequest request)
    {
        EditarPagamentoInput input = new EditarPagamentoInput(request.novoValor());

        Pagamento pagamento = editarPagamentoUseCase.executar(id, input, usuarioLogado);

        PagamentoResponse resposta = new PagamentoResponse(
                pagamento.getId(),
                pagamento.getStatus().name(),
                "Pagamento editado com sucesso pelo usuário: " + usuarioLogado
        );

        return ResponseEntity.ok(resposta);
    }
}
