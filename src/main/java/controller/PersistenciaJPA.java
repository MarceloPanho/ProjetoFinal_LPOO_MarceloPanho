/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.util.List;
import model.Cliente;

/**
 *
 * @author marce
 */
import javax.persistence.*;
import model.Funcionario;
import model.OrdemServico;
import model.Veiculo;

public class PersistenciaJPA implements InterfaceBD {

    // Fábrica de gerenciadores de entidades (configurada pelo persistence.xml)
    private EntityManagerFactory emf;

    // Gerenciador de entidades (responsável pelas operações no banco)
    private EntityManager em;

    /**
     * Construtor padrão.
     * Inicializa o EntityManager usando a unidade de persistência definida no persistence.xml
     */
    public PersistenciaJPA() {
        // "pu-projetofinal-marcelopanho" deve ser exatamente o mesmo nome usado no persistence.xml
        emf = Persistence.createEntityManagerFactory("pu-projetofinal-marcelopanho");
        em = emf.createEntityManager();
    }

    // ---------- IMPLEMENTAÇÃO DOS MÉTODOS DA INTERFACE ----------

    /**
     * Lista todos os clientes do banco de dados.
     */
    @Override
    public List<Cliente> listarClientes() {
        return em.createQuery("from Cliente", Cliente.class).getResultList();
    }

    /**
     * Lista todos os funcionários do banco de dados.
     */
    @Override
    public List<Funcionario> listarFuncionarios() {
        return em.createQuery("from Funcionario", Funcionario.class).getResultList();
    }

    /**
     * Lista todos os veículos do banco de dados.
     */
    @Override
    public List<Veiculo> listarVeiculos() {
        return em.createQuery("from Veiculo", Veiculo.class).getResultList();
    }

    /**
     * Lista todas as ordens de serviço do banco de dados.
     */
    @Override
    public List<OrdemServico> listarOrdensServico() {
        return em.createQuery("from OrdemServico", OrdemServico.class).getResultList();
    }

    /**
     * Busca uma entidade por ID. Funciona com qualquer classe anotada com @Entity.
     * Usa generics para poder retornar objetos de tipos diferentes.
     */
    @Override
    public <T> T buscarPorId(Class<T> classe, Object id) {
        return em.find(classe, id);
    }

    /**
     * Remove um objeto do banco de dados.
     * Se o objeto não estiver sendo gerenciado, ele é mesclado primeiro.
     */
    @Override
    public void remover(Object o) {
        em.getTransaction().begin();
        em.remove(em.contains(o) ? o : em.merge(o));
        em.getTransaction().commit();
    }

    /**
     * Salva ou atualiza um objeto no banco de dados.
     * O método merge faz inserção ou update automático, dependendo se o objeto tem ID.
     */
    @Override
    public void salvar(Object o) {
        em.getTransaction().begin();
        em.merge(o); // merge = salva novo ou atualiza existente
        em.getTransaction().commit();
    }

    /**
     * Fecha a conexão com o banco de dados.
     * Deve ser chamada ao final da execução do sistema.
     */
    public void fecharConexao() {
        em.close();
        emf.close();
    }
}