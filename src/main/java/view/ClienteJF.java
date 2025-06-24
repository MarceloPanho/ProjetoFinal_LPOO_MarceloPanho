/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;

import controller.InterfaceBD;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import model.Cliente;

/**
 *
 * @author marce
 */

/**
 * Tela de cadastro e edição de clientes.
 * Permite preencher ou modificar os dados de um cliente, com validação.
 */
public class ClienteJF extends JFrame {

    // ---------- COMPONENTES ----------
    private JTextField tfNome, tfTelefone, tfEmail, tfCpf, tfEndereco;
    private JButton btnSalvar, btnCancelar;
    private InterfaceBD persistencia; // Controlador de persistência (salvar, buscar, etc.)
    private Cliente cliente; // Objeto a ser criado ou editado

    // ---------- CONSTRUTORES ----------

    /**
     * Construtor para cadastro de novo cliente.
     * Apenas chama o segundo construtor com cliente = null.
     */
    public ClienteJF(InterfaceBD persistencia) {
        this(persistencia, null);
    }

    /**
     * Construtor para edição de cliente existente.
     * Recebe o cliente a ser editado (ou null para novo).
     */
    public ClienteJF(InterfaceBD persistencia, Cliente cliente) {
        this.persistencia = persistencia;
        this.cliente = cliente;

        // ---------- CONFIGURAÇÃO DA JANELA ----------
        setTitle("Cadastro de Cliente");
        setSize(400, 350);
        setLocationRelativeTo(null); // Centraliza na tela
        setLayout(new GridLayout(6, 2, 5, 5)); // 6 linhas, 2 colunas, espaçamento 5px

        // ---------- CAMPOS DE ENTRADA ----------
        tfNome = new JTextField();
        tfTelefone = new JTextField();
        tfEmail = new JTextField();
        tfCpf = new JTextField();
        tfEndereco = new JTextField();

        // ---------- ADICIONA OS CAMPOS COM LABELS ----------
        add(new JLabel("Nome:"));      add(tfNome);
        add(new JLabel("Telefone:"));  add(tfTelefone);
        add(new JLabel("Email:"));     add(tfEmail);
        add(new JLabel("CPF:"));       add(tfCpf);
        add(new JLabel("Endereço:"));  add(tfEndereco);

        // ---------- BOTÕES ----------
        btnSalvar = new JButton("Salvar");
        btnCancelar = new JButton("Cancelar");
        add(btnSalvar); add(btnCancelar);

        // ---------- PRÉ-PREENCHIMENTO EM CASO DE EDIÇÃO ----------
        if (cliente != null) {
            tfNome.setText(cliente.getNome());
            tfCpf.setText(cliente.getCpf());
            tfEmail.setText(cliente.getEmail());
            tfTelefone.setText(cliente.getTelefone());
            tfEndereco.setText(cliente.getEndereco());
        }

        // ---------- BOTÃO CANCELAR: fecha e volta para tela de consulta ----------
        btnCancelar.addActionListener(e -> {
            dispose(); // Fecha a tela de cadastro
            SwingUtilities.invokeLater(() -> new ClienteConsultaJF(persistencia).setVisible(true));
        });

        // ---------- BOTÃO SALVAR: valida e salva os dados ----------
        btnSalvar.addActionListener(e -> {
            try {
                // ---------- VALIDAÇÃO ----------
                if (tfNome.getText().trim().isEmpty() ||
                    tfCpf.getText().trim().isEmpty() ||
                    tfEmail.getText().trim().isEmpty() ||
                    tfTelefone.getText().trim().isEmpty() ||
                    tfEndereco.getText().trim().isEmpty()) {

                    JOptionPane.showMessageDialog(this, "Todos os campos devem ser preenchidos.");
                    return;
                }

                // ---------- NOVO CLIENTE OU EDIÇÃO ----------
                if (this.cliente == null) {
                    this.cliente = new Cliente();
                }

                // ---------- ATRIBUTOS ----------
                this.cliente.setNome(tfNome.getText().trim());
                this.cliente.setCpf(tfCpf.getText().trim());
                this.cliente.setEmail(tfEmail.getText().trim());
                this.cliente.setTelefone(tfTelefone.getText().trim());
                this.cliente.setEndereco(tfEndereco.getText().trim());

                // ---------- SALVANDO NO BANCO ----------
                persistencia.salvar(this.cliente);

                JOptionPane.showMessageDialog(this, "Cliente salvo com sucesso!");

                dispose(); // Fecha o formulário
                SwingUtilities.invokeLater(() -> new ClienteConsultaJF(persistencia).setVisible(true)); // Reabre a tela de consulta

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erro ao salvar cliente: " + ex.getMessage());
                ex.printStackTrace();
            }
        });
    }
}