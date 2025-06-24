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
import javax.persistence.OneToMany;

/**
 *
 * @author marce
 */

/**
 * Classe que representa o cliente no sistema.
 * Herda da classe Pessoa, e é anotada como entidade JPA (@Entity),
 * o que significa que ela será mapeada para uma tabela no banco de dados.
 */
@Entity
public class Cliente extends Pessoa implements Serializable {

    // ---------- ATRIBUTOS ESPECÍFICOS DO CLIENTE ----------

    /**
     * CPF do cliente.
     * - Deve ser único no banco.
     * - Obrigatório (nullable = false).
     * - Limitado a 14 caracteres (inclui pontos e traço).
     */
    @Column(name = "cpf", unique = true, nullable = false, length = 14)
    private String cpf;

    /**
     * Endereço do cliente.
     * Campo opcional, limitado a 150 caracteres.
     */
    @Column(name = "endereco", length = 150)
    private String endereco;

    // ---------- RELACIONAMENTOS ----------

    /**
     * Um cliente pode ter vários veículos.
     * - mappedBy = "cliente": a outra ponta da relação está na entidade Veiculo.
     * - cascade = ALL: todas as operações feitas no cliente também afetam os veículos.
     * - orphanRemoval = true: remove veículos do banco se forem removidos da lista.
     */
    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Veiculo> veiculos = new ArrayList<>();

    /**
     * Um cliente pode ter várias ordens de serviço associadas.
     * Mesmo comportamento do relacionamento com veículos.
     */
    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrdemServico> ordensServico = new ArrayList<>();

    // ---------- GETTERS E SETTERS ----------

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public List<Veiculo> getVeiculos() {
        return veiculos;
    }

    public void setVeiculos(List<Veiculo> veiculos) {
        this.veiculos = veiculos;
    }

    public List<OrdemServico> getOrdensServico() {
        return ordensServico;
    }

    public void setOrdensServico(List<OrdemServico> ordensServico) {
        this.ordensServico = ordensServico;
    }

    // ---------- UTILITÁRIOS ----------

    /**
     * Representação textual usada em JComboBox e tabelas.
     */
    @Override
    public String toString() {
        return getNome() + " - CPF: " + cpf;
    }

    /**
     * Expõe o método getId() herdado da classe Pessoa.
     * Isso é útil em consultas e exibições.
     */
    public int getId() {
        return super.getId();
    }
}