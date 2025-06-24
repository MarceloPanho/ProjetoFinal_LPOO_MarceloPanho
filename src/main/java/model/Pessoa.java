/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

/**
 *
 * @author marce
 */

/**
 * Classe abstrata que representa uma pessoa genérica.
 * Serve de base para Cliente e Funcionario.
 * Está mapeada como uma entidade JPA com herança do tipo JOINED.
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED) // Estratégia de herança onde cada subclasse tem sua própria tabela, com chave estrangeira apontando para Pessoa
public abstract class Pessoa implements Serializable {

    // ---------- ATRIBUTOS COMUNS A TODAS AS PESSOAS ----------

    /**
     * Identificador único da pessoa.
     * Gerado automaticamente pelo banco (auto incremento).
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    /**
     * Nome da pessoa (obrigatório, até 100 caracteres).
     */
    @Column(name = "nome", nullable = false, length = 100)
    private String nome;

    /**
     * Telefone da pessoa (opcional, até 20 caracteres).
     */
    @Column(name = "telefone", length = 20)
    private String telefone;

    /**
     * Email da pessoa (opcional, até 100 caracteres).
     */
    @Column(name = "email", length = 100)
    private String email;

    // ---------- GETTERS E SETTERS ----------

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}