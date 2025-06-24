/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 *
 * @author marce
 */

@Entity
public class OrdemServico implements Serializable{

    // ---------- IDENTIFICADOR (ID) ----------

    /**
     * Identificador único da ordem de serviço (chave primária).
     * Gerado automaticamente com auto incremento (IDENTITY).
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    // ---------- ATRIBUTOS ----------

    /**
     * Descrição do serviço a ser realizado (ex: "troca de óleo", "revisão").
     * Limite de 255 caracteres.
     */
    @Column(name = "descricao_servico", length = 255)
    private String descricaoServico;

    /**
     * Data de abertura da ordem de serviço.
     * Representa o momento em que a OS foi registrada.
     */
    @Column(name = "data_abertura")
    private LocalDate dataAbertura;

    /**
     * Data de conclusão da ordem de serviço (pode ser nula até o encerramento).
     */
    @Column(name = "data_conclusao")
    private LocalDate dataConclusao;

    /**
     * Valor total cobrado pelo serviço.
     */
    @Column(name = "valor")
    private double valor;

    /**
     * Status atual da OS (ex: "aberta", "em andamento", "finalizada").
     */
    @Column(name = "status", length = 20)
    private String status;

    // ---------- RELACIONAMENTOS ----------

    /**
     * Cada ordem está associada a um cliente.
     * Relacionamento muitos-para-um com Cliente.
     * É obrigatório (nullable = false).
     */
    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

    /**
     * Cada ordem é registrada por um funcionário.
     * Relacionamento muitos-para-um com Funcionario.
     */
    @ManyToOne
    @JoinColumn(name = "funcionario_id", nullable = false)
    private Funcionario funcionario;

    /**
     * Cada ordem está relacionada a um veículo do cliente.
     * Relacionamento muitos-para-um com Veiculo.
     */
    @ManyToOne
    @JoinColumn(name = "veiculo_id", nullable = false)
    private Veiculo veiculo;

    // ---------- GETTERS E SETTERS ----------

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescricaoServico() {
        return descricaoServico;
    }

    public void setDescricaoServico(String descricaoServico) {
        this.descricaoServico = descricaoServico;
    }

    public LocalDate getDataAbertura() {
        return dataAbertura;
    }

    public void setDataAbertura(LocalDate dataAbertura) {
        this.dataAbertura = dataAbertura;
    }

    public LocalDate getDataConclusao() {
        return dataConclusao;
    }

    public void setDataConclusao(LocalDate dataConclusao) {
        this.dataConclusao = dataConclusao;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Funcionario getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(Funcionario funcionario) {
        this.funcionario = funcionario;
    }

    public Veiculo getVeiculo() {
        return veiculo;
    }

    public void setVeiculo(Veiculo veiculo) {
        this.veiculo = veiculo;
    }
}