/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;

import controller.InterfaceBD;
import java.awt.GridLayout;
import java.time.LocalDate;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import model.Funcionario;
import model.Veiculo;

/**
 *
 * @author marce
 */

/**
 * Tela gráfica para cadastrar ou editar dados de um funcionário.
 * Permite preencher nome, matrícula, cargo e salário com validação.
 */
public class FuncionarioJF extends JFrame {

    // ---------- Componentes ----------
    private JTextField tfNome, tfMatricula, tfCargo, tfSalario;
    private JButton btnSalvar, btnCancelar;
    private Funcionario funcionario;             // Funcionario a ser cadastrado ou editado
    private InterfaceBD persistencia;            // Camada de persistência para salvar os dados

    // ---------- Construtor para novo funcionário ----------
    public FuncionarioJF(InterfaceBD persistencia) {
        this(persistencia, null);
    }

    // ---------- Construtor para edição de funcionário ----------
    public FuncionarioJF(InterfaceBD persistencia, Funcionario funcionario) {
        this.persistencia = persistencia;
        this.funcionario = funcionario;

        // ---------- Configuração da janela ----------
        setTitle("Cadastro de Funcionário");
        setSize(400, 300);
        setLocationRelativeTo(null); // Centraliza
        setLayout(new GridLayout(5, 2, 5, 5)); // 5 linhas, 2 colunas

        // ---------- Criação e adição dos campos ----------
        add(new JLabel("Nome:"));
        tfNome = new JTextField();
        add(tfNome);

        add(new JLabel("Matrícula:"));
        tfMatricula = new JTextField();
        add(tfMatricula);

        add(new JLabel("Cargo:"));
        tfCargo = new JTextField();
        add(tfCargo);

        add(new JLabel("Salário:"));
        tfSalario = new JTextField();
        add(tfSalario);

        btnSalvar = new JButton("Salvar");
        btnCancelar = new JButton("Cancelar");
        add(btnSalvar);
        add(btnCancelar);

        // ---------- Pré-preenchimento para edição ----------
        if (funcionario != null) {
            tfNome.setText(funcionario.getNome());
            tfMatricula.setText(funcionario.getMatricula());
            tfCargo.setText(funcionario.getCargo());
            tfSalario.setText(String.valueOf(funcionario.getSalario()));
        }

        // ---------- Ação do botão Cancelar ----------
        btnCancelar.addActionListener(e -> {
            dispose(); // Fecha a janela atual
            SwingUtilities.invokeLater(() -> new FuncionarioConsultaJF(persistencia).setVisible(true)); // Reabre a lista
        });

        // ---------- Ação do botão Salvar ----------
        btnSalvar.addActionListener(e -> {
            try {
                // ---------- Validação dos campos obrigatórios ----------
                if (tfNome.getText().trim().isEmpty() ||
                    tfMatricula.getText().trim().isEmpty() ||
                    tfCargo.getText().trim().isEmpty() ||
                    tfSalario.getText().trim().isEmpty()) {

                    JOptionPane.showMessageDialog(this, "Todos os campos são obrigatórios.");
                    return;
                }

                // ---------- Conversão e validação do salário ----------
                double salario;
                try {
                    salario = Double.parseDouble(tfSalario.getText().trim());
                    if (salario < 0) {
                        JOptionPane.showMessageDialog(this, "O salário não pode ser negativo.");
                        return;
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Digite um valor numérico válido para o salário.");
                    return;
                }

                // ---------- Criação ou atualização do objeto ----------
                if (this.funcionario == null) {
                    this.funcionario = new Funcionario(); // Novo funcionário
                }

                this.funcionario.setNome(tfNome.getText().trim());
                this.funcionario.setMatricula(tfMatricula.getText().trim());
                this.funcionario.setCargo(tfCargo.getText().trim());
                this.funcionario.setSalario(salario);

                // ---------- Salvando no banco ----------
                persistencia.salvar(this.funcionario);

                JOptionPane.showMessageDialog(this, "Funcionário salvo com sucesso!");
                dispose(); // Fecha a tela atual

                // Reabre a lista de funcionários
                SwingUtilities.invokeLater(() -> new FuncionarioConsultaJF(persistencia).setVisible(true));

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erro ao salvar funcionário: " + ex.getMessage());
                ex.printStackTrace();
            }
        });
    }
}