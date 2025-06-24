/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;

import controller.InterfaceBD;
import java.awt.GridLayout;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import model.Cliente;
import model.Veiculo;

/**
 *
 * @author marce
 */

/**
 * Tela de cadastro ou edição de veículos.
 * Permite preencher placa, modelo, marca e associar um cliente.
 */
public class VeiculoJF extends JFrame {

    // ---------- Componentes da interface ----------
    private JTextField tfPlaca, tfModelo, tfMarca;
    private JComboBox<Cliente> cbClientes;
    private JButton btnSalvar, btnCancelar;

    private Veiculo veiculo; // Usado para edição
    private InterfaceBD persistencia; // Interface de acesso ao banco (via JPA)

    /**
     * Construtor para cadastrar novo veículo.
     */
    public VeiculoJF(InterfaceBD persistencia) {
        this(persistencia, null); // Chama o outro construtor com veiculo = null
    }

    /**
     * Construtor para editar veículo existente (ou criar novo).
     */
    public VeiculoJF(InterfaceBD persistencia, Veiculo veiculo) {
        this.persistencia = persistencia;
        this.veiculo = veiculo;

        // ---------- Configuração da janela ----------
        setTitle("Cadastro de Veículo");
        setSize(400, 250);
        setLocationRelativeTo(null); // Centraliza
        setLayout(new GridLayout(5, 2, 5, 5)); // 5 linhas, 2 colunas

        // ---------- Inicialização dos campos ----------
        tfPlaca = new JTextField();
        tfModelo = new JTextField();
        tfMarca = new JTextField();
        cbClientes = new JComboBox<>();

        carregarClientes(); // Preenche o JComboBox com os clientes do banco

        // ---------- Adiciona os campos à tela ----------
        add(new JLabel("Placa:"));   add(tfPlaca);
        add(new JLabel("Modelo:"));  add(tfModelo);
        add(new JLabel("Marca:"));   add(tfMarca);
        add(new JLabel("Cliente:")); add(cbClientes);

        btnSalvar = new JButton("Salvar");
        btnCancelar = new JButton("Cancelar");
        add(btnSalvar); add(btnCancelar);

        // ---------- Preenche os campos se for edição ----------
        if (veiculo != null) {
            tfPlaca.setText(veiculo.getPlaca());
            tfModelo.setText(veiculo.getModelo());
            tfMarca.setText(veiculo.getMarca());
            cbClientes.setSelectedItem(veiculo.getCliente());
        }

        // ---------- Ação do botão Salvar ----------
        btnSalvar.addActionListener(e -> {
            try {
                // ---------- Validação dos campos ----------
                if (tfPlaca.getText().trim().isEmpty() ||
                    tfModelo.getText().trim().isEmpty() ||
                    tfMarca.getText().trim().isEmpty() ||
                    cbClientes.getSelectedItem() == null) {

                    JOptionPane.showMessageDialog(this,
                        "Todos os campos são obrigatórios e o cliente deve ser selecionado.");
                    return;
                }

                // ---------- Criação ou edição do objeto ----------
                if (this.veiculo == null) {
                    this.veiculo = new Veiculo(); // Criação
                }

                this.veiculo.setPlaca(tfPlaca.getText().trim());
                this.veiculo.setModelo(tfModelo.getText().trim());
                this.veiculo.setMarca(tfMarca.getText().trim());
                this.veiculo.setCliente((Cliente) cbClientes.getSelectedItem());

                // ---------- Persistência no banco ----------
                persistencia.salvar(this.veiculo);

                JOptionPane.showMessageDialog(this, "Veículo salvo com sucesso!");

                dispose(); // Fecha a tela atual
                SwingUtilities.invokeLater(() -> new VeiculoConsultaJF(persistencia).setVisible(true)); // Reabre a lista

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erro ao salvar veículo: " + ex.getMessage());
                ex.printStackTrace();
            }
        });

        // ---------- Ação do botão Cancelar ----------
        btnCancelar.addActionListener(e -> {
            dispose();
            SwingUtilities.invokeLater(() -> new VeiculoConsultaJF(persistencia).setVisible(true));
        });
    }

    /**
     * Preenche o ComboBox com todos os clientes cadastrados.
     */
    private void carregarClientes() {
        List<Cliente> clientes = persistencia.listarClientes();
        for (Cliente c : clientes) {
            cbClientes.addItem(c);
        }
    }
}