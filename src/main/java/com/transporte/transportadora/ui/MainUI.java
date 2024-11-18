package com.transporte.transportadora.ui;

import com.transporte.transportadora.ui.cidadeui.*;
import com.transporte.transportadora.ui.clienteui.*;
import com.transporte.transportadora.ui.estadoui.*;
import com.transporte.transportadora.ui.freteui.*;
import com.transporte.transportadora.ui.pessoafisicaui.*;
import com.transporte.transportadora.ui.pessoajuridicaui.*;

import javax.swing.*;
import java.awt.*;

public class MainUI extends JFrame {

    private JComboBox<String> tabelaComboBox;
    private JComboBox<String> operacaoComboBox;
    private JButton confirmarButton;

    public MainUI() {
        setTitle("Sistema de Transporte - Tela Inicial");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // ComboBox Tabela
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(new JLabel("Selecione a Tabela:"), gbc);

        tabelaComboBox = new JComboBox<>(new String[]{"", "Cidade", "Cliente", "Estado", "Frete", "Pessoa Física", "Pessoa Jurídica"});
        gbc.gridx = 1;
        add(tabelaComboBox, gbc);

        // ComboBox Operação
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(new JLabel("Selecione a Operação:"), gbc);

        operacaoComboBox = new JComboBox<>(new String[]{"", "Cadastrar", "Listar", "Atualizar", "Deletar"});
        gbc.gridx = 1;
        add(operacaoComboBox, gbc);

        // Botão Confirmar
        confirmarButton = new JButton("Confirmar");
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        add(confirmarButton, gbc);

        confirmarButton.addActionListener(e -> abrirTela());

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void abrirTela() {
        String tabela = (String) tabelaComboBox.getSelectedItem();
        String operacao = (String) operacaoComboBox.getSelectedItem();

        if (tabela == null || operacao == null || tabela.isEmpty() || operacao.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, selecione uma tabela e uma operação.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        JFrame tela = null;

        switch (tabela + operacao) {
            // Telas de Cidade
            case "CidadeCadastrar":
                tela = new CidadeCreateUI();
                break;
            case "CidadeListar":
                tela = new CidadeReadUI();
                break;
            case "CidadeAtualizar":
                tela = new CidadeUpdateUI();
                break;
            case "CidadeDeletar":
                tela = new CidadeDeleteUI();
                break;

            // Telas de Cliente
            case "ClienteCadastrar":
                tela = new ClienteCreateUI();
                break;
            case "ClienteListar":
                tela = new ClienteCreateUI();
                break;
            case "ClienteAtualizar":
                tela = new ClienteCreateUI();
                break;
            case "ClienteDeletar":
                tela = new ClienteCreateUI();
                break;

            // Telas de Estado
            case "EstadoCadastrar":
                tela = new EstadoCreateUI();
                break;
            case "EstadoListar":
                tela = new EstadoReadUI();
                break;
            case "EstadoAtualizar":
                tela = new EstadoUpdateUI();
                break;
            case "EstadoDeletar":
                tela = new EstadoDeleteUI();
                break;

            // Telas de Frete
            case "FreteCadastrar":
                tela = new FreteCreateUI();
                break;
            case "FreteListar":
                tela = new FreteReadUI();
                break;
            case "FreteAtualizar":
                tela = new FreteUpdateUI();
                break;
            case "FreteDeletar":
                tela = new FreteDeleteUI();
                break;

            // Telas de Pessoa Física
            case "Pessoa FísicaCadastrar":
                tela = new PessoaFisicaCreateUI();
                break;
            case "Pessoa FísicaListar":
                tela = new PessoaFisicaReadUI();
                break;
            case "Pessoa FísicaAtualizar":
                tela = new PessoaFisicaUpdateUI();
                break;
            case "Pessoa FísicaDeletar":
                tela = new PessoaFisicaDeleteUI();
                break;

            // Telas de Pessoa Jurídica
            case "Pessoa JurídicaCadastrar":
                tela = new PessoaJuridicaCreateUI();
                break;
            case "Pessoa JurídicaListar":
                tela = new PessoaJuridicaReadUI();
                break;
            case "Pessoa JurídicaAtualizar":
                tela = new PessoaJuridicaUpdateUI();
                break;
            case "Pessoa JurídicaDeletar":
                tela = new PessoaJuridicaDeleteUI();
                break;

            default:
                JOptionPane.showMessageDialog(this, "Opção inválida.", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
        }

        if (tela != null) {
            this.setVisible(false);
            tela.setVisible(true);
            tela.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                    setVisible(true);
                }
            });
        }
    }

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(MainUI::new);
    }
}
