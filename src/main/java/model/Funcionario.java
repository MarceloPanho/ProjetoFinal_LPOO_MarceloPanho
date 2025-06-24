/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

/**
 *
 * @author marce
 */

@Entity
public class Funcionario extends Pessoa implements Serializable{

    // ---------- ATRIBUTOS ESPECÍFICOS DO FUNCIONÁRIO ----------

    /**
     * Matrícula do funcionário.
     * - Deve ser única no banco.
     * - Obrigatória (nullable = false).
     * - Máximo de 20 caracteres.
     */
    @Column(name = "matricula", unique = true, nullable = false, length = 20)
    private String matricula;

    /**
     * Cargo ocupado pelo funcionário.
     */
    @Column(name = "cargo", length = 50)
    private String cargo;

    /**
     * Salário do funcionário.
     */
    @Column(name = "salario")
    private double salario;

    /**
     * Data de contratação do funcionário.
     */
    @Column(name = "data_contratacao")
    private LocalDate dataContratacao;

    /**
     * Data de demissão do funcionário (se houver).
     */
    @Column(name = "data_demissao")
    private LocalDate dataDemissao;

    // ---------- RELACIONAMENTOS ----------

    /**
     * Um funcionário pode estar associado a várias ordens de serviço.
     * - mappedBy = "funcionario": indica o campo da outra classe que faz o vínculo.
     * - cascade = ALL: qualquer operação no funcionário será refletida nas ordens.
     * - orphanRemoval = true: ordens associadas serão removidas se forem excluídas da lista.
     */
    @OneToMany(mappedBy = "funcionario", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrdemServico> ordensServico = new ArrayList<>();

    // ---------- GETTERS E SETTERS ----------

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public double getSalario() {
        return salario;
    }

    public void setSalario(double salario) {
        this.salario = salario;
    }

    public LocalDate getDataContratacao() {
        return dataContratacao;
    }

    public void setDataContratacao(LocalDate dataContratacao) {
        this.dataContratacao = dataContratacao;
    }

    public LocalDate getDataDemissao() {
        return dataDemissao;
    }

    public void setDataDemissao(LocalDate dataDemissao) {
        this.dataDemissao = dataDemissao;
    }

    public List<OrdemServico> getOrdensServico() {
        return ordensServico;
    }

    public void setOrdensServico(List<OrdemServico> ordensServico) {
        this.ordensServico = ordensServico;
    }

    // ---------- EXIBIÇÃO NA INTERFACE ----------

    /**
     * Representação textual do funcionário.
     * Usada automaticamente em ComboBox, tabelas e logs.
     */
    @Override
    public String toString() {
        return getNome() + " - Matrícula: " + matricula;
    }
}