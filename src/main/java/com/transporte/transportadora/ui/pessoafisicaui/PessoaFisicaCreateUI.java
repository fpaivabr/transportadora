package com.transporte.transportadora.ui.pessoafisicaui;

import com.transporte.transportadora.model.PessoaFisica;
import com.transporte.transportadora.service.PessoaFisicaService;
import com.transporte.transportadora.service.impl.PessoaFisicaServiceImpl;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;
@Lazy
@Component
public class PessoaFisicaCreateUI extends JFrame {

    private JTextField txtNome;
    private JTextField txtCpf;
    private JButton btnSalvar;

    private final PessoaFisicaService pessoaFisicaService;

    public PessoaFisicaCreateUI(PessoaFisicaService pessoaFisicaService) {
        this.pessoaFisicaService = pessoaFisicaService;
        initUI();
    }
    private void initUI() {
        setTitle("Criar Pessoa Física");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;

        add(new JLabel("Nome:"), gbc);
        gbc.gridx = 1;
        txtNome = new JTextField(20);
        add(txtNome, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        add(new JLabel("CPF:"), gbc);
        gbc.gridx = 1;
        txtCpf = new JTextField(20);
        add(txtCpf, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        btnSalvar = new JButton("Salvar");
        add(btnSalvar, gbc);

        btnSalvar.addActionListener(e -> salvarPessoaFisica());

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void salvarPessoaFisica() {
        try {
            String nome = txtNome.getText();
            String cpf = txtCpf.getText();

            PessoaFisica pessoaFisica = new PessoaFisica();
            pessoaFisica.setNomeCli(nome);
            pessoaFisica.setCpf(cpf);

            pessoaFisicaService.salvarPessoaFisica(pessoaFisica);
            JOptionPane.showMessageDialog(this, "Pessoa Física criada com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            limparCampos();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar Pessoa Física: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void limparCampos() {
        txtNome.setText("");
        txtCpf.setText("");
    }
}