/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;

import controller.InterfaceBD;
import model.Funcionario;

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
 * Tela de consulta de funcionários.
 * Permite buscar, cadastrar, editar e excluir funcionários.
 */
public class FuncionarioConsultaJF extends JFrame {

    // ---------- Componentes da interface ----------
    private JTextField tfBusca;
    private JButton btnBuscar, btnNovo, btnEditar;
    private JTable tabela;
    private DefaultTableModel modelo;
    private InterfaceBD persistencia;

    /**
     * Construtor da tela de consulta de funcionários.
     * Recebe a camada de persistência como parâmetro.
     */
    public FuncionarioConsultaJF(InterfaceBD persistencia) {
        this.persistencia = persistencia;

        // ---------- Configurações da janela ----------
        setTitle("Consulta de Funcionários");
        setSize(750, 400);
        setLocationRelativeTo(null); // Centraliza na tela
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // ---------- Componentes do topo ----------
        tfBusca = new JTextField(25);
        btnBuscar = new JButton("Buscar");
        btnNovo = new JButton("Novo");
        btnEditar = new JButton("Editar");

        JPanel painelTopo = new JPanel(new FlowLayout());
        painelTopo.add(new JLabel("Nome, Matrícula ou Cargo:"));
        painelTopo.add(tfBusca);
        painelTopo.add(btnBuscar);
        painelTopo.add(btnNovo);
        painelTopo.add(btnEditar);

        // ---------- Tabela para exibir os funcionários ----------
        modelo = new DefaultTableModel(new String[]{
                "ID", "Nome", "Matrícula", "Cargo", "Salário"
        }, 0);
        tabela = new JTable(modelo);
        JScrollPane scroll = new JScrollPane(tabela);

        add(painelTopo, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);

        // ---------- Ações dos botões ----------

        // Busca funcionários com base no termo
        btnBuscar.addActionListener(e -> buscar());

        // Abre a tela de cadastro de novo funcionário
        btnNovo.addActionListener(e -> {
            new FuncionarioJF(persistencia).setVisible(true);
            dispose(); // Fecha a tela de consulta
        });

        // Edita funcionário selecionado na tabela
        btnEditar.addActionListener(e -> {
            int linha = tabela.getSelectedRow();
            if (linha >= 0) {
                int id = (int) modelo.getValueAt(linha, 0); // Coluna do ID
                Funcionario f = persistencia.buscarPorId(Funcionario.class, id);
                if (f != null) {
                    new FuncionarioJF(persistencia, f).setVisible(true);
                    dispose();
                }
            } else {
                JOptionPane.showMessageDialog(this, "Selecione um funcionário para editar.");
            }
        });

        // Clique com o botão direito para excluir funcionário
        tabela.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                if (SwingUtilities.isRightMouseButton(e)) {
                    int linha = tabela.rowAtPoint(e.getPoint());
                    tabela.setRowSelectionInterval(linha, linha); // Seleciona a linha clicada
                    int id = (int) modelo.getValueAt(linha, 0);
                    Funcionario f = persistencia.buscarPorId(Funcionario.class, id);
                    int resposta = JOptionPane.showConfirmDialog(null,
                            "Deseja excluir o funcionário " + f.getNome() + "?",
                            "Confirmar exclusão", JOptionPane.YES_NO_OPTION);
                    if (resposta == JOptionPane.YES_OPTION) {
                        persistencia.remover(f);
                        buscar(); // Atualiza a tabela
                    }
                }
            }
        });

        // Exibe todos os funcionários ao abrir a tela
        buscar();
    }

    /**
     * Método que busca os funcionários e preenche a tabela.
     * Permite busca por nome, matrícula ou cargo.
     */
    private void buscar() {
        modelo.setRowCount(0); // Limpa a tabela
        String termo = tfBusca.getText().trim().toLowerCase();

        List<Funcionario> lista = persistencia.listarFuncionarios(); // Consulta ao banco

        for (Funcionario f : lista) {
            String nome = f.getNome().toLowerCase();
            String matricula = f.getMatricula().toLowerCase();
            String cargo = f.getCargo().toLowerCase();

            // Verifica se o termo aparece em algum campo buscável
            if (termo.isEmpty() || nome.contains(termo) || matricula.contains(termo) || cargo.contains(termo)) {
                modelo.addRow(new Object[]{
                        f.getId(),
                        f.getNome(),
                        f.getMatricula(),
                        f.getCargo(),
                        f.getSalario()
                });
            }
        }
    }
}