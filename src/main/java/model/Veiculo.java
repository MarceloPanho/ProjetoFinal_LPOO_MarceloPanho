/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

/**
 *
 * @author marce
 */

/**
 * Classe que representa um veículo no sistema.
 * Cada veículo pertence a um cliente e pode estar associado a várias ordens de serviço.
 */
@Entity
public class Veiculo implements Serializable {

    // ---------- ATRIBUTOS BÁSICOS ----------

    /**
     * Identificador único do veículo.
     * Gerado automaticamente pelo banco com auto incremento.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    /**
     * Placa do veículo.
     * - Deve ser única.
     * - Obrigatória (nullable = false).
     * - Limitada a 10 caracteres.
     */
    @Column(name = "placa", unique = true, nullable = false, length = 10)
    private String placa;

    /**
     * Modelo do veículo (ex: Civic, Uno, Gol).
     */
    @Column(name = "modelo", length = 50)
    private String modelo;

    /**
     * Marca do veículo (ex: Honda, Fiat, VW).
     */
    @Column(name = "marca", length = 50)
    private String marca;

    // ---------- RELACIONAMENTOS ----------

    /**
     * Muitos veículos pertencem a um único cliente.
     * - O cliente é obrigatório (nullable = false).
     * - Representa um relacionamento @ManyToOne com chave estrangeira cliente_id.
     */
    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

    /**
     * Um veículo pode ter várias ordens de serviço associadas.
     * - mappedBy = "veiculo": indica o atributo que faz o mapeamento na classe OrdemServico.
     * - cascade = ALL: operações no veículo afetam as OS relacionadas.
     * - orphanRemoval = true: se uma OS for removida da lista, será excluída do banco.
     */
    @OneToMany(mappedBy = "veiculo", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrdemServico> ordensServico = new ArrayList<>();

    // ---------- GETTERS E SETTERS ----------

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public List<OrdemServico> getOrdensServico() {
        return ordensServico;
    }

    public void setOrdensServico(List<OrdemServico> ordensServico) {
        this.ordensServico = ordensServico;
    }

    // ---------- EXIBIÇÃO NA INTERFACE ----------

    /**
     * Representação textual do veículo.
     * Usado em JComboBox e tabelas (ex: "Gol (ABC-1234)").
     */
    @Override
    public String toString() {
        return modelo + " (" + placa + ")";
    }
}