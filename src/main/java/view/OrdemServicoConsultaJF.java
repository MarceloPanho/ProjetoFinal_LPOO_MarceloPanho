/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;

import controller.InterfaceBD;
import model.OrdemServico;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

/**
 *
 * @author marce
 */

/**
 * Tela de consulta de Ordens de Serviço (OS).
 * Permite buscar, cadastrar, editar e excluir ordens.
 */
public class OrdemServicoConsultaJF extends JFrame {

    // ---------- Componentes ----------
    private JTextField tfBusca;
    private JButton btnBuscar, btnNovo, btnEditar;
    private JTable tabela;
    private DefaultTableModel modelo;
    private InterfaceBD persistencia;

    /**
     * Construtor da janela de consulta de OS.
     * Recebe a instância de persistência como dependência.
     */
    public OrdemServicoConsultaJF(InterfaceBD persistencia) {
        this.persistencia = persistencia;

        // ---------- Configuração da janela ----------
        setTitle("Consulta de Ordens de Serviço");
        setSize(850, 400);
        setLocationRelativeTo(null); // Centraliza
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // ---------- Painel superior com busca e botões ----------
        tfBusca = new JTextField(25);
        btnBuscar = new JButton("Buscar");
        btnNovo = new JButton("Novo");
        btnEditar = new JButton("Editar");

        JPanel painelTopo = new JPanel(new FlowLayout());
        painelTopo.add(new JLabel("Cliente, Placa ou Status:"));
        painelTopo.add(tfBusca);
        painelTopo.add(btnBuscar);
        painelTopo.add(btnNovo);
        painelTopo.add(btnEditar);

        // ---------- Tabela ----------
        modelo = new DefaultTableModel(new String[]{
            "ID", "Cliente", "Veículo", "Funcionário", "Valor", "Status"
        }, 0);
        tabela = new JTable(modelo);
        JScrollPane scroll = new JScrollPane(tabela);

        add(painelTopo, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);

        // ---------- Ações ----------

        // Busca filtrando por cliente, placa ou status
        btnBuscar.addActionListener(e -> buscar());

        // Criação de nova OS
        btnNovo.addActionListener(e -> {
            new OrdemServicoJF(persistencia).setVisible(true);
            dispose(); // Fecha a tela de consulta
        });

        // Edição da OS selecionada
        btnEditar.addActionListener(e -> {
            int linha = tabela.getSelectedRow();
            if (linha >= 0) {
                int id = (int) modelo.getValueAt(linha, 0);
                OrdemServico os = persistencia.buscarPorId(OrdemServico.class, id);
                if (os != null) {
                    new OrdemServicoJF(persistencia, os).setVisible(true);
                    dispose();
                }
            } else {
                JOptionPane.showMessageDialog(this, "Selecione uma ordem de serviço para editar.");
            }
        });

        // Clique direito na tabela para excluir
        tabela.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                if (SwingUtilities.isRightMouseButton(e)) {
                    int linha = tabela.rowAtPoint(e.getPoint());
                    tabela.setRowSelectionInterval(linha, linha); // Garante que a linha foi selecionada
                    int id = (int) modelo.getValueAt(linha, 0);
                    OrdemServico os = persistencia.buscarPorId(OrdemServico.class, id);

                    int resposta = JOptionPane.showConfirmDialog(null,
                            "Deseja excluir esta OS do cliente " + os.getCliente().getNome() + "?",
                            "Confirmar exclusão", JOptionPane.YES_NO_OPTION);

                    if (resposta == JOptionPane.YES_OPTION) {
                        persistencia.remover(os);
                        buscar(); // Atualiza a tabela
                    }
                }
            }
        });

        buscar(); // Exibe todas as OS ao abrir
    }

    /**
     * Método que preenche a tabela com os dados filtrados.
     */
    private void buscar() {
        modelo.setRowCount(0); // Limpa a tabela

        String termo = tfBusca.getText().trim().toLowerCase();

        List<OrdemServico> lista = persistencia.listarOrdensServico();

        for (OrdemServico os : lista) {
            String cliente = os.getCliente().getNome().toLowerCase();
            String placa = os.getVeiculo().getPlaca().toLowerCase();
            String status = os.getStatus().toLowerCase();

            // Filtra por cliente, placa ou status
            if (termo.isEmpty() || cliente.contains(termo) || placa.contains(termo) || status.contains(termo)) {
                modelo.addRow(new Object[]{
                        os.getId(),
                        os.getCliente().getNome(),
                        os.getVeiculo().getModelo() + " (" + os.getVeiculo().getPlaca() + ")",
                        os.getFuncionario().getNome(),
                        os.getValor(),
                        os.getStatus()
                });
            }
        }
    }
}