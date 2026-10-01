package com.my_projects.auto_pay_system_rest.infrastructure.persistence.entity;


import com.my_projects.auto_pay_system_rest.domain.model.StatusPagamento;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "pagamentos")
public class PagamentoJpaEntity {

    @Id
    private UUID id;
    private String cnpjPagador;
    private BigDecimal valor;
    private String destino;

    @Enumerated(EnumType.STRING)
    private StatusPagamento status;

    private LocalDateTime dataCriacao;

    // Construtor vazio exigido pelo JPA
    protected PagamentoJpaEntity(){}

    public PagamentoJpaEntity(
            UUID id, String cnpjPagador, BigDecimal valor, String destino,
            StatusPagamento status, LocalDateTime dataCriacao)
    {
        this.id = id;
        this.cnpjPagador = cnpjPagador;
        this.valor = valor;
        this.destino = destino;
        this.status = status;
        this.dataCriacao = dataCriacao;
    }

    public UUID getId() {
        return id;
    }

    public String getCnpjPagador() {
        return cnpjPagador;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public String getDestino() {
        return destino;
    }

    public StatusPagamento getStatus() {
        return status;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }
}
