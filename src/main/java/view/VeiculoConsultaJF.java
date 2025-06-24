package view;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


import controller.InterfaceBD;
import model.Veiculo;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
/**
 *
 * @author marce
 */
public class VeiculoConsultaJF extends JFrame {

    // ---------- Componentes da interface ----------
    private JTextField tfBusca;
    private JButton btnBuscar, btnNovo, btnEditar;
    private JTable tabela;
    private DefaultTableModel modelo;
    private InterfaceBD persistencia; // Interface de comunicação com o banco (JPA)

    /**
     * Construtor da janela de consulta de veículos.
     */
    public VeiculoConsultaJF(InterfaceBD persistencia) {
        this.persistencia = persistencia;

        // ---------- Configuração da janela ----------
        setTitle("Consulta de Veículos");
        setSize(750, 400);
        setLocationRelativeTo(null); // Centraliza
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // ---------- Painel superior com busca e botões ----------
        tfBusca = new JTextField(25);
        btnBuscar = new JButton("Buscar");
        btnNovo = new JButton("Novo");
        btnEditar = new JButton("Editar");

        JPanel painelTopo = new JPanel(new FlowLayout());
        painelTopo.add(new JLabel("Placa ou Modelo:"));
        painelTopo.add(tfBusca);
        painelTopo.add(btnBuscar);
        painelTopo.add(btnNovo);
        painelTopo.add(btnEditar);

        // ---------- Configuração da tabela ----------
        modelo = new DefaultTableModel(
            new String[]{"ID", "Placa", "Modelo", "Marca", "Cliente"}, 0
        );
        tabela = new JTable(modelo);
        JScrollPane scroll = new JScrollPane(tabela);

        add(painelTopo, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);

        // ---------- Ações ----------

        // Atualiza a tabela com base no texto de busca
        btnBuscar.addActionListener(e -> buscar());

        // Abre o formulário para cadastrar novo veículo
        btnNovo.addActionListener(e -> {
            new VeiculoJF(persistencia).setVisible(true);
            dispose(); // Fecha a tela de consulta
        });

        // Abre o formulário de edição com dados preenchidos
        btnEditar.addActionListener(e -> {
            int linha = tabela.getSelectedRow();
            if (linha >= 0) {
                int id = (int) modelo.getValueAt(linha, 0);
                Veiculo v = persistencia.buscarPorId(Veiculo.class, id);
                if (v != null) {
                    new VeiculoJF(persistencia, v).setVisible(true);
                    dispose();
                }
            } else {
                JOptionPane.showMessageDialog(this, "Selecione um veículo na tabela.");
            }
        });

        // Clique com botão direito para excluir veículo
        tabela.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                if (SwingUtilities.isRightMouseButton(e)) {
                    int linha = tabela.rowAtPoint(e.getPoint());
                    tabela.setRowSelectionInterval(linha, linha); // Seleciona a linha clicada
                    int id = (int) modelo.getValueAt(linha, 0);
                    Veiculo v = persistencia.buscarPorId(Veiculo.class, id);

                    int resposta = JOptionPane.showConfirmDialog(null,
                            "Deseja excluir o veículo " + v.getPlaca() + "?",
                            "Confirmação", JOptionPane.YES_NO_OPTION);

                    if (resposta == JOptionPane.YES_OPTION) {
                        persistencia.remover(v);
                        buscar(); // Atualiza a tabela
                    }
                }
            }
        });

        // ---------- Carrega todos os veículos ao abrir ----------
        buscar();
    }

    /**
     * Busca veículos no banco e preenche a tabela com os dados.
     * Filtra por placa ou modelo.
     */
    private void buscar() {
        modelo.setRowCount(0); // Limpa a tabela
        String termo = tfBusca.getText().trim().toLowerCase();

        List<Veiculo> veiculos = persistencia.listarVeiculos();
        for (Veiculo v : veiculos) {
            if (termo.isEmpty() ||
                v.getPlaca().toLowerCase().contains(termo) ||
                v.getModelo().toLowerCase().contains(termo)) {

                modelo.addRow(new Object[]{
                        v.getId(),
                        v.getPlaca(),
                        v.getModelo(),
                        v.getMarca(),
                        v.getCliente().getNome()
                });
            }
        }
    }
}
