package com.transporte.transportadora.ui;

import com.transporte.transportadora.ui.cidadeui.*;
import com.transporte.transportadora.ui.clienteui.*;
import com.transporte.transportadora.ui.estadoui.*;
import com.transporte.transportadora.ui.freteui.*;
import com.transporte.transportadora.ui.pessoafisicaui.*;
import com.transporte.transportadora.ui.pessoajuridicaui.*;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;

@Component
public class MainUI extends JFrame {

    private JComboBox<String> tabelaComboBox;
    private JComboBox<String> operacaoComboBox;
    private JButton confirmarButton;
    private final ApplicationContext context;
    public MainUI(ApplicationContext context) {
        this.context = context;
        initUI();
    }

    private void initUI() {
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
            case "CidadeCadastrar":
                tela = context.getBean(CidadeCreateUI.class);
                break;
            case "CidadeListar":
                tela = context.getBean(CidadeReadUI.class);
                break;
            case "CidadeAtualizar":
                tela = context.getBean(CidadeUpdateUI.class);
                break;
            case "CidadeDeletar":
                tela = context.getBean(CidadeDeleteUI.class);
                break;

            case "ClienteCadastrar":
                tela = context.getBean(ClienteCreateUI.class);
                break;
            case "ClienteListar":
                tela = context.getBean(ClienteReadUI.class);
                break;
            case "ClienteAtualizar":
                tela = context.getBean(ClienteUpdateUI.class);
                break;
            case "ClienteDeletar":
                tela = context.getBean(ClienteDeleteUI.class);
                break;

            // Telas de Estado
            case "EstadoCadastrar":
                tela = context.getBean(EstadoCreateUI.class);
                break;
            case "EstadoListar":
                tela = context.getBean(EstadoReadUI.class);
                break;
            case "EstadoAtualizar":
                tela = context.getBean(EstadoUpdateUI.class);
                break;
            case "EstadoDeletar":
                tela = context.getBean(EstadoDeleteUI.class);
                break;

            // Telas de Frete
            case "FreteCadastrar":
                tela = context.getBean(FreteCreateUI.class);
                break;
            case "FreteListar":
                tela = context.getBean(FreteReadUI.class);
                break;
            case "FreteAtualizar":
                tela = context.getBean(FreteUpdateUI.class);
                break;
            case "FreteDeletar":
                tela = context.getBean(FreteDeleteUI.class);
                break;

            // Telas de Pessoa Física
            case "Pessoa FísicaListar":
                tela = context.getBean(PessoaFisicaReadUI.class);
                break;
            case "Pessoa FísicaAtualizar":
                tela = context.getBean(PessoaFisicaUpdateUI.class);
                break;
            case "Pessoa FísicaDeletar":
                tela = context.getBean(PessoaFisicaDeleteUI.class);
                break;

            // Telas de Pessoa Jurídica
            case "Pessoa JurídicaListar":
                tela = context.getBean(PessoaJuridicaReadUI.class);
                break;
            case "Pessoa JurídicaAtualizar":
                tela = context.getBean(PessoaJuridicaUpdateUI.class);
                break;
            case "Pessoa JurídicaDeletar":
                tela = context.getBean(PessoaJuridicaDeleteUI.class);
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
}