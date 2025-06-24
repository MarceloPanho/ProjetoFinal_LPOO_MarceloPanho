/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.util.List;
import model.Cliente;
import model.Funcionario;
import model.OrdemServico;
import model.Veiculo;

/**
 *
 * @author marce
 */
public interface InterfaceBD {
 

    // Métodos de persistência genérica
    void salvar(Object o);
    void remover(Object o);
     /**
     * Busca um objeto pelo ID e retorna o objeto do tipo correspondente.
     * Usa generics (<T>) para que o método funcione com qualquer entidade.
     */
    <T> T buscarPorId(Class<T> classe, Object id);

    // Listagens específicas
    List<Cliente> listarClientes();
    List<Funcionario> listarFuncionarios();
    List<Veiculo> listarVeiculos();
    List<OrdemServico> listarOrdensServico();

    // Encerramento da conexão com o banco
    void fecharConexao();
}

