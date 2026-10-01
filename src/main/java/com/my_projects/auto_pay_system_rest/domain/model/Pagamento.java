package com.my_projects.auto_pay_system_rest.domain.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class Pagamento {
    private UUID id;
    private String cnpjPagador;
    private BigDecimal valor;
    private String destino;
    private StatusPagamento status;
    private LocalDateTime dataCriacao;

    public Pagamento(String cnpjPagador, BigDecimal valor, String destino){
        this.id = UUID.randomUUID();
        this.cnpjPagador = cnpjPagador;
        this.valor = valor;
        this.destino = destino;
        this.status = StatusPagamento.PENDENTE;
        this.dataCriacao = LocalDateTime.now();
    }

    //Construtor privado usado para reconstuir pagamentos já criados
    private Pagamento(
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

    //Factory Method
    public static Pagamento restaurarDoBanco(
            UUID id, String cnpjPagador, BigDecimal valor, String destino,
            StatusPagamento status, LocalDateTime dataCriacao){
        return new Pagamento(id, cnpjPagador, valor, destino, status, dataCriacao);
    }

    //Avaliar o valor para decidir o status
    public void processarAutorizacao(BigDecimal limiteAprovacaoAutomatica) {
            if (this.status != StatusPagamento.PENDENTE) {
                throw new IllegalArgumentException("Apenas pagamentos pendentes podem ser processados");
            }
            //se é igual ou menor ao limite, automaticamente aprovado
            if (this.valor.compareTo(limiteAprovacaoAutomatica) <= 0) {
                this.status = StatusPagamento.AUTORIZADO;
            } else {
                this.status = StatusPagamento.AGUARDANDO_APROVACAO_MANUAL;
            }
    }

    //Edição segura
    public void editarValor(BigDecimal novoValor, String usuarioLogado) {
        if (this.status == StatusPagamento.AUTORIZADO) {
            throw new IllegalArgumentException("Pagamentos já autorizados não podem ser editados");
        }
        this.valor = novoValor;
        //Volta para pendente pos edição, sendo necessario processarAutorização
        this.status = StatusPagamento.PENDENTE;

        //TODO: disparar o evento PagamentoEditado
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
