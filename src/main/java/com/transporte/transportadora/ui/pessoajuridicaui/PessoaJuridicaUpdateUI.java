package com.transporte.transportadora.ui.pessoajuridicaui;

import com.transporte.transportadora.model.PessoaJuridica;
import com.transporte.transportadora.service.PessoaJuridicaService;
import com.transporte.transportadora.service.impl.PessoaJuridicaServiceImpl;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class PessoaJuridicaUpdateUI extends JFrame {

    private JComboBox<PessoaJuridica> cmbPessoasJuridicas;
    private JTextField txtRazaoSocial;
    private JTextField txtInscEstadual;
    private JTextField txtCnpj;
    private JButton btnAtualizar;

    private PessoaJuridicaService pessoaJuridicaService;

    public PessoaJuridicaUpdateUI() {
        pessoaJuridicaService = new PessoaJuridicaServiceImpl();
        setTitle("Atualizar Pessoa Jurídica");
        setSize(400, 300);
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
        add(new JLabel("Razão Social:"), gbc);
        gbc.gridx = 1;
        txtRazaoSocial = new JTextField(20);
        add(txtRazaoSocial, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        add(new JLabel("Inscrição Estadual:"), gbc);
        gbc.gridx = 1;
        txtInscEstadual = new JTextField(20);
        add(txtInscEstadual, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        add(new JLabel("CNPJ:"), gbc);
        gbc.gridx = 1;
        txtCnpj = new JTextField(20);
        add(txtCnpj, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        btnAtualizar = new JButton("Atualizar");
        add(btnAtualizar, gbc);

        btnAtualizar.addActionListener(e -> atualizarPessoaJuridica());
        cmbPessoasJuridicas.addActionListener(e -> preencherDadosPessoaJuridica());

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

    private void preencherDadosPessoaJuridica() {
        PessoaJuridica pessoaSelecionada = (PessoaJuridica) cmbPessoasJuridicas.getSelectedItem();
        if (pessoaSelecionada != null) {
            txtRazaoSocial.setText(pessoaSelecionada.getRazaoSocial());
            txtInscEstadual.setText(pessoaSelecionada.getInscEstadual());
            txtCnpj.setText(pessoaSelecionada.getCnpj());
        }
    }

    private void atualizarPessoaJuridica() {
        try {
            PessoaJuridica pessoaSelecionada = (PessoaJuridica) cmbPessoasJuridicas.getSelectedItem();
            if (pessoaSelecionada == null) {
                JOptionPane.showMessageDialog(this, "Selecione uma Pessoa Jurídica.", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String novaRazaoSocial = txtRazaoSocial.getText();
            String novaInscEstadual = txtInscEstadual.getText();
            String novoCnpj = txtCnpj.getText();

            pessoaSelecionada.setRazaoSocial(novaRazaoSocial);
            pessoaSelecionada.setInscEstadual(novaInscEstadual);
            pessoaSelecionada.setCnpj(novoCnpj);

            pessoaJuridicaService.atualizarPessoaJuridica(pessoaSelecionada.getCodCli(), pessoaSelecionada);
            JOptionPane.showMessageDialog(this, "Pessoa Jurídica atualizada com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            carregarPessoasJuridicas();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao atualizar Pessoa Jurídica: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        new PessoaJuridicaUpdateUI();
    }
}
