/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;

import controller.InterfaceBD;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.SwingUtilities;

/**
 *
 * @author marce
 */

/**
 * Janela principal da aplicação "Oficina Mecânica".
 * Oferece menus para acessar os cadastros e ordens de serviço.
 */
public class PrincipalJF extends JFrame {

    private InterfaceBD persistencia; // Camada de persistência compartilhada com todas as telas

    /**
     * Construtor da janela principal.
     * Recebe o objeto de persistência que será usado em toda a aplicação.
     */
    public PrincipalJF(InterfaceBD persistencia) {
        this.persistencia = persistencia;

        // ---------- Configuração básica da janela ----------
        setTitle("Sistema - Oficina Mecânica");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Centraliza a janela

        // ---------- Criação da barra de menu ----------
        JMenuBar barraMenu = new JMenuBar();

        // ---------- Menu "Cadastros" com subitens ----------
        JMenu menuCadastro = new JMenu("Cadastros");

        JMenuItem miConsultaCliente = new JMenuItem("Clientes");
        JMenuItem miConsultaVeiculo = new JMenuItem("Veículos");
        JMenuItem miConsultaFuncionario = new JMenuItem("Funcionários");

        // Adiciona os itens de cadastro ao menu
        menuCadastro.add(miConsultaVeiculo);
        menuCadastro.add(miConsultaCliente);
        menuCadastro.add(miConsultaFuncionario);

        // ---------- Menu "Serviços" com subitem ----------
        JMenu menuServicos = new JMenu("Serviços");
        JMenuItem miConsultaOS = new JMenuItem("Ordens de Serviço");

        menuServicos.add(miConsultaOS);

        // ---------- Adiciona menus à barra de menu ----------
        barraMenu.add(menuCadastro);
        barraMenu.add(menuServicos);

        setJMenuBar(barraMenu); // Define a barra de menu no JFrame

        // ---------- Ações dos menus: abrem as respectivas telas ----------
        miConsultaFuncionario.addActionListener(e ->
            new FuncionarioConsultaJF(persistencia).setVisible(true));

        miConsultaOS.addActionListener(e ->
            new OrdemServicoConsultaJF(persistencia).setVisible(true));

        miConsultaVeiculo.addActionListener(e ->
            new VeiculoConsultaJF(persistencia).setVisible(true));

        miConsultaCliente.addActionListener(e ->
            new ClienteConsultaJF(persistencia).setVisible(true));
    }

    /**
     * Método main que inicia o sistema, criando a janela principal.
     */
    public static void main(String[] args) {
        InterfaceBD persistencia = new controller.PersistenciaJPA(); // Implementação JPA da persistência

        // Cria a GUI na thread correta (Event Dispatch Thread)
        SwingUtilities.invokeLater(() -> new PrincipalJF(persistencia).setVisible(true));
    }
}