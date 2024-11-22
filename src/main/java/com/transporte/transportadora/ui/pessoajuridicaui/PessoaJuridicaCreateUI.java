package com.transporte.transportadora.ui.pessoajuridicaui;

import com.transporte.transportadora.model.PessoaJuridica;
import com.transporte.transportadora.service.PessoaJuridicaService;
import com.transporte.transportadora.service.impl.PessoaJuridicaServiceImpl;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;
@Lazy
@Component
public class PessoaJuridicaCreateUI extends JFrame {

    private JTextField txtRazaoSocial;
    private JTextField txtCnpj;
    private JTextField txtInscEstadual;
    private JButton btnSalvar;

    private final PessoaJuridicaService pessoaJuridicaService;

    public PessoaJuridicaCreateUI(PessoaJuridicaService pessoaJuridicaService) {
        this.pessoaJuridicaService = pessoaJuridicaService;
        initUI();
    }
    private void initUI() {
        setTitle("Criar Pessoa Jurídica");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;

        add(new JLabel("Razão Social:"), gbc);
        gbc.gridx = 1;
        txtRazaoSocial = new JTextField(20);
        add(txtRazaoSocial, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        add(new JLabel("CNPJ:"), gbc);
        gbc.gridx = 1;
        txtCnpj = new JTextField(20);
        add(txtCnpj, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        add(new JLabel("Inscrição Estadual:"), gbc);
        gbc.gridx = 1;
        txtInscEstadual = new JTextField(20);
        add(txtInscEstadual, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        btnSalvar = new JButton("Salvar");
        add(btnSalvar, gbc);

        btnSalvar.addActionListener(e -> salvarPessoaJuridica());

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void salvarPessoaJuridica() {
        try {
            String razaoSocial = txtRazaoSocial.getText();
            String cnpj = txtCnpj.getText();
            String inscEstadual = txtInscEstadual.getText();

            PessoaJuridica pessoaJuridica = new PessoaJuridica();
            pessoaJuridica.setRazaoSocial(razaoSocial);
            pessoaJuridica.setCnpj(cnpj);
            pessoaJuridica.setInscEstadual(inscEstadual);

            pessoaJuridicaService.salvarPessoaJuridica(pessoaJuridica);
            JOptionPane.showMessageDialog(this, "Pessoa Jurídica criada com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            limparCampos();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar Pessoa Jurídica: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void limparCampos() {
        txtRazaoSocial.setText("");
        txtCnpj.setText("");
        txtInscEstadual.setText("");
    }
}
