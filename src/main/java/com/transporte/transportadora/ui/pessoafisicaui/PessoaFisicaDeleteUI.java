package com.transporte.transportadora.ui.pessoafisicaui;

import com.transporte.transportadora.model.PessoaFisica;
import com.transporte.transportadora.service.PessoaFisicaService;
import com.transporte.transportadora.ui.MainUI;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;
import java.util.List;

@Lazy
@Component
public class PessoaFisicaDeleteUI extends JFrame {

    private JComboBox<PessoaFisica> cmbPessoasFisicas;
    private JButton btnDeletar;
    private JButton btnVoltar;

    private final PessoaFisicaService pessoaFisicaService;
    private final MainUI mainUI;

    public PessoaFisicaDeleteUI(PessoaFisicaService pessoaFisicaService, MainUI mainUI) {
        this.pessoaFisicaService = pessoaFisicaService;
        this.mainUI = mainUI;
        initUI();
    }

    private void initUI() {
        setTitle("Deletar Pessoa Física");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;

        add(new JLabel("Selecione uma Pessoa Física:"), gbc);
        gbc.gridx = 1;
        cmbPessoasFisicas = new JComboBox<>();
        carregarPessoasFisicas();
        add(cmbPessoasFisicas, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        btnDeletar = new JButton("Deletar");
        add(btnDeletar, gbc);

        btnDeletar.addActionListener(e -> deletarPessoaFisica());

        gbc.gridy++;
        btnVoltar = new JButton("Voltar");
        add(btnVoltar, gbc);

        btnVoltar.addActionListener(e -> voltarParaMainUI());

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void carregarPessoasFisicas() {
        try {
            List<PessoaFisica> pessoasFisicas = pessoaFisicaService.listarTodos();
            for (PessoaFisica pessoa : pessoasFisicas) {
                cmbPessoasFisicas.addItem(pessoa);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar Pessoas Físicas: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deletarPessoaFisica() {
        try {
            PessoaFisica pessoaSelecionada = (PessoaFisica) cmbPessoasFisicas.getSelectedItem();
            if (pessoaSelecionada == null) {
                JOptionPane.showMessageDialog(this, "Selecione uma Pessoa Física.", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            pessoaFisicaService.deletarPessoaFisica(pessoaSelecionada.getCodCli());
            JOptionPane.showMessageDialog(this, "Pessoa Física deletada com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            carregarPessoasFisicas();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao deletar Pessoa Física: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void voltarParaMainUI() {
        mainUI.setVisible(true);
        dispose();
    }
}
