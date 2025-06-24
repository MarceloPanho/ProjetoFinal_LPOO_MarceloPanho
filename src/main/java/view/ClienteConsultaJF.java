/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;

import controller.InterfaceBD;
import model.Cliente;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

/**
 *
 * @author marce
 *
 */
/**
 * Tela gráfica para consulta de clientes cadastrados.
 * Permite buscar por nome ou CPF, editar, cadastrar e excluir clientes.
 */
public class ClienteConsultaJF extends JFrame {

    // ---------- Componentes da interface ----------
    private JTextField tfBusca;                  // Campo de busca
    private JButton btnBuscar, btnNovo, btnEditar;
    private JTable tabela;                       // Tabela que exibe os dados
    private DefaultTableModel modelo;            // Modelo da tabela
    private InterfaceBD persistencia;            // Objeto responsável pela comunicação com o banco

    /**
     * Construtor da tela de consulta.
     * Recebe o controlador de persistência como parâmetro.
     */
    public ClienteConsultaJF(InterfaceBD persistencia) {
        this.persistencia = persistencia;

        // Configurações básicas da janela
        setTitle("Consulta de Clientes");
        setSize(700, 400);
        setLocationRelativeTo(null); // Centraliza na tela
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Fecha sem encerrar o app
        setLayout(new BorderLayout());

        // ---------- Painel superior com campo de busca e botões ----------
        JPanel painelTopo = new JPanel(new FlowLayout());
        tfBusca = new JTextField(25);
        btnBuscar = new JButton("Buscar");
        btnNovo = new JButton("Novo");
        btnEditar = new JButton("Editar");

        painelTopo.add(new JLabel("Nome ou CPF:"));
        painelTopo.add(tfBusca);
        painelTopo.add(btnBuscar);
        painelTopo.add(btnNovo);
        painelTopo.add(btnEditar);

        // ---------- Tabela de dados ----------
        modelo = new DefaultTableModel(new String[]{"ID", "Nome", "CPF", "Email"}, 0);
        tabela = new JTable(modelo);
        JScrollPane scroll = new JScrollPane(tabela); // Adiciona barra de rolagem

        // Adiciona componentes à janela principal
        add(painelTopo, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);

        // ---------- Ação do botão "Buscar" ----------
        btnBuscar.addActionListener(e -> buscar());

        // ---------- Ação do botão "Novo" ----------
        btnNovo.addActionListener(e -> {
            new ClienteJF(persistencia).setVisible(true); // Abre a tela de cadastro
            dispose(); // Fecha a tela de consulta
        });

        // ---------- Ação do botão "Editar" ----------
        btnEditar.addActionListener(e -> {
            int linha = tabela.getSelectedRow(); // Verifica a linha selecionada
            if (linha >= 0) {
                int id = (int) modelo.getValueAt(linha, 0); // Obtém o ID
                Cliente c = persistencia.buscarPorId(Cliente.class, id); // Busca o cliente no banco
                if (c != null) {
                    new ClienteJF(persistencia, c).setVisible(true); // Abre a tela de edição com dados preenchidos
                    dispose();
                }
            } else {
                JOptionPane.showMessageDialog(this, "Selecione uma linha da tabela para editar.");
            }
        });

        // ---------- Clique com botão direito para excluir cliente ----------
        tabela.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                if (SwingUtilities.isRightMouseButton(e)) {
                    int linha = tabela.rowAtPoint(e.getPoint());
                    tabela.setRowSelectionInterval(linha, linha); // Seleciona a linha clicada
                    int id = (int) modelo.getValueAt(linha, 0); // Pega o ID da linha
                    Cliente c = persistencia.buscarPorId(Cliente.class, id); // Busca no banco

                    int resposta = JOptionPane.showConfirmDialog(null,
                            "Deseja excluir o cliente " + c.getNome() + "?",
                            "Confirmar exclusão", JOptionPane.YES_NO_OPTION);

                    if (resposta == JOptionPane.YES_OPTION) {
                        persistencia.remover(c); // Remove do banco
                        buscar(); // Atualiza a tabela
                    }
                }
            }
        });

        // ---------- Executa a primeira busca automaticamente ----------
        buscar();
    }

    /**
     * Método que realiza a busca de clientes e atualiza a tabela.
     */
    private void buscar() {
        modelo.setRowCount(0); // Limpa a tabela
        String termo = tfBusca.getText().trim().toLowerCase(); // Termo digitado

        List<Cliente> clientes = persistencia.listarClientes(); // Busca todos os clientes

        for (Cliente c : clientes) {
            // Adiciona à tabela somente se o nome ou CPF contiver o termo buscado
            if (termo.isEmpty() ||
                c.getNome().toLowerCase().contains(termo) ||
                c.getCpf().contains(termo)) {
                modelo.addRow(new Object[]{
                        c.getId(),
                        c.getNome(),
                        c.getCpf(),
                        c.getEmail()
                });
            }
        }
    }
}