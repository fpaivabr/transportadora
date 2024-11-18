package com.transporte.transportadora.ui.pessoajuridicaui;

import com.transporte.transportadora.model.PessoaJuridica;
import com.transporte.transportadora.service.PessoaJuridicaService;
import com.transporte.transportadora.service.impl.PessoaJuridicaServiceImpl;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class PessoaJuridicaDeleteUI extends JFrame {

    private JComboBox<PessoaJuridica> cmbPessoasJuridicas;
    private JButton btnDeletar;

    private PessoaJuridicaService pessoaJuridicaService;

    public PessoaJuridicaDeleteUI() {
        pessoaJuridicaService = new PessoaJuridicaServiceImpl();
        setTitle("Deletar Pessoa Jurídica");
        setSize(400, 150);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;

        add(new JLabel("Selecione uma Pessoa Jurídica:"), gbc);
        gbc.gridx = 1;
        cmbPessoasJuridicas = new JComboBox<>();
        carregarPessoasJuridicas();
        add(cmbPessoasJuridicas, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        btnDeletar = new JButton("Deletar");
        add(btnDeletar, gbc);

        btnDeletar.addActionListener(e -> deletarPessoaJuridica());

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void carregarPessoasJuridicas() {
        try {
            List<PessoaJuridica> pessoasJuridicas = pessoaJuridicaService.listarTodos();
            for (PessoaJuridica pessoa : pessoasJuridicas) {
                cmbPessoasJuridicas.addItem(pessoa);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar Pessoas Jurídicas: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deletarPessoaJuridica() {
        try {
            PessoaJuridica pessoaSelecionada = (PessoaJuridica) cmbPessoasJuridicas.getSelectedItem();
            if (pessoaSelecionada == null) {
                JOptionPane.showMessageDialog(this, "Selecione uma Pessoa Jurídica.", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            pessoaJuridicaService.deletarPessoaJuridica(pessoaSelecionada.getCodCli());
            JOptionPane.showMessageDialog(this, "Pessoa Jurídica deletada com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            carregarPessoasJuridicas();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao deletar Pessoa Jurídica: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        new PessoaJuridicaDeleteUI();
    }
}
