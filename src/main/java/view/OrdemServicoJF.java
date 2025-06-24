/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;

import controller.InterfaceBD;
import java.awt.GridLayout;
import java.time.LocalDate;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import model.Cliente;
import model.Funcionario;
import model.OrdemServico;
import model.Veiculo;

/**
 *
 * @author marce
 */

/**
 * Tela de cadastro/edição de Ordens de Serviço.
 * Permite selecionar cliente, funcionário e veículo, definir valor, status e descrição.
 */
public class OrdemServicoJF extends JFrame {

    // ---------- Campos da interface ----------
    private JTextField tfDescricao, tfValor, tfStatus;
    private JComboBox<Cliente> cbCliente;
    private JComboBox<Funcionario> cbFuncionario;
    private JComboBox<Veiculo> cbVeiculo;
    private JButton btnSalvar, btnCancelar;

    private InterfaceBD persistencia;           // Camada de persistência
    private OrdemServico ordemservico;          // Objeto em edição (ou novo)

    // ---------- Construtor para nova OS ----------
    public OrdemServicoJF(InterfaceBD persistencia) {
        this(persistencia, null);
    }

    // ---------- Construtor para editar OS existente ----------
    public OrdemServicoJF(InterfaceBD persistencia, OrdemServico os){
        this.persistencia = persistencia;
        this.ordemservico = os;

        // ---------- Configuração da janela ----------
        setTitle("Cadastro Ordem de Serviço");
        setSize(500, 350);
        setLocationRelativeTo(null); // Centraliza a janela
        setLayout(new GridLayout(7, 2, 5, 5)); // 7 linhas, 2 colunas, espaçamento 5px

        // ---------- Criação dos campos ----------
        tfDescricao = new JTextField();
        tfValor = new JTextField();
        tfStatus = new JTextField();

        cbCliente = new JComboBox<>();
        cbFuncionario = new JComboBox<>();
        cbVeiculo = new JComboBox<>();

        carregarDadosCombo(); // Preenche os JComboBoxes

        // ---------- Adiciona os campos à tela ----------
        add(new JLabel("Descrição do serviço:")); add(tfDescricao);
        add(new JLabel("Valor:"));                add(tfValor);
        add(new JLabel("Status:"));               add(tfStatus);
        add(new JLabel("Cliente:"));              add(cbCliente);
        add(new JLabel("Funcionário:"));          add(cbFuncionario);
        add(new JLabel("Veículo:"));              add(cbVeiculo);

        btnSalvar = new JButton("Salvar");
        btnCancelar = new JButton("Cancelar");
        add(btnSalvar); add(btnCancelar);

        // ---------- Pré-preenchimento para edição ----------
        if (ordemservico != null) {
            tfDescricao.setText(ordemservico.getDescricaoServico());
            tfValor.setText(String.valueOf(ordemservico.getValor()));
            tfStatus.setText(ordemservico.getStatus());

            cbCliente.setSelectedItem(ordemservico.getCliente());
            cbFuncionario.setSelectedItem(ordemservico.getFuncionario());
            cbVeiculo.setSelectedItem(ordemservico.getVeiculo());
        }

        // ---------- Ação do botão Salvar ----------
        btnSalvar.addActionListener(e -> {
            try {
                // ---------- Validação dos campos obrigatórios ----------
                if (tfDescricao.getText().trim().isEmpty() ||
                    tfValor.getText().trim().isEmpty() ||
                    tfStatus.getText().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Preencha todos os campos de texto.");
                    return;
                }

                // ---------- Validação do campo valor ----------
                double valor;
                try {
                    valor = Double.parseDouble(tfValor.getText().trim());
                    if (valor < 0) {
                        JOptionPane.showMessageDialog(this, "O valor não pode ser negativo.");
                        return;
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Digite um valor numérico válido para o campo 'Valor'.");
                    return;
                }

                // ---------- Verifica se todos os JComboBox têm seleção válida ----------
                if (cbCliente.getSelectedItem() == null ||
                    cbFuncionario.getSelectedItem() == null ||
                    cbVeiculo.getSelectedItem() == null) {
                    JOptionPane.showMessageDialog(this, "Selecione cliente, funcionário e veículo.");
                    return;
                }

                // ---------- Criação da ordem de serviço se for nova ----------
                if (ordemservico == null) {
                    this.ordemservico = new OrdemServico();
                    ordemservico.setDataAbertura(LocalDate.now()); // Data atual
                }

                // ---------- Preenchimento do objeto ----------
                ordemservico.setDescricaoServico(tfDescricao.getText().trim());
                ordemservico.setValor(valor);
                ordemservico.setStatus(tfStatus.getText().trim());
                ordemservico.setCliente((Cliente) cbCliente.getSelectedItem());
                ordemservico.setFuncionario((Funcionario) cbFuncionario.getSelectedItem());
                ordemservico.setVeiculo((Veiculo) cbVeiculo.getSelectedItem());

                // ---------- Persistência ----------
                persistencia.salvar(ordemservico);
                JOptionPane.showMessageDialog(this, "Ordem de Serviço salva com sucesso!");

                // Fecha a tela atual e reabre a lista
                dispose();
                SwingUtilities.invokeLater(() -> new OrdemServicoConsultaJF(persistencia).setVisible(true));

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erro ao salvar Ordem de Serviço: " + ex.getMessage());
                ex.printStackTrace();
            }
        });

        // ---------- Ação do botão Cancelar ----------
        btnCancelar.addActionListener(e -> {
            dispose();
            SwingUtilities.invokeLater(() -> new OrdemServicoConsultaJF(persistencia).setVisible(true));
        });
    }

    /**
     * Carrega os dados nos JComboBox com clientes, funcionários e veículos.
     */
    private void carregarDadosCombo() {
        try {
            for (Cliente c : persistencia.listarClientes()) {
                cbCliente.addItem(c);
            }
            for (Funcionario f : persistencia.listarFuncionarios()) {
                cbFuncionario.addItem(f);
            }
            for (Veiculo v : persistencia.listarVeiculos()) {
                cbVeiculo.addItem(v);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar dados: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
}