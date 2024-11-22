package com.transporte.transportadora.ui.pessoafisicaui;

import com.transporte.transportadora.model.PessoaFisica;
import com.transporte.transportadora.repository.ClienteRepository;
import com.transporte.transportadora.service.PessoaFisicaService;
import com.transporte.transportadora.ui.MainUI;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;
import java.util.List;

@Lazy
@Component
public class PessoaFisicaUpdateUI extends JFrame {

    private final ClienteRepository clienteRepository;
    private JComboBox<String> cmbPessoasFisicas;
    private JTextField txtNome;
    private JTextField txtCpf;
    private JButton btnAtualizar;
    private JButton btnVoltar;

    private final PessoaFisicaService pessoaFisicaService;
    private final MainUI mainUI;

    public PessoaFisicaUpdateUI(PessoaFisicaService pessoaFisicaService, ClienteRepository clienteRepository, MainUI mainUI) {
        this.pessoaFisicaService = pessoaFisicaService;
        this.mainUI = mainUI;
        initUI();
        this.clienteRepository = clienteRepository;
    }

    private void initUI() {
        setTitle("Atualizar Pessoa Física");
        setSize(400, 300);
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
        btnAtualizar = new JButton("Atualizar");
        add(btnAtualizar, gbc);

        btnAtualizar.addActionListener(e -> atualizarPessoaFisica());
        cmbPessoasFisicas.addActionListener(e -> preencherDadosPessoaFisica());

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
            cmbPessoasFisicas.removeAllItems();
            for (PessoaFisica pessoa : pessoasFisicas) {
                cmbPessoasFisicas.addItem(pessoa.getCpf());
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar Pessoas Físicas: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void preencherDadosPessoaFisica() {
        PessoaFisica pessoaSelecionada =  clienteRepository.findByDocumento(cmbPessoasFisicas.getSelectedItem().toString()).orElse(null).getPessoaFisica();
        if (pessoaSelecionada != null) {
            txtNome.setText(pessoaSelecionada.getNomeCli());
            txtCpf.setText(pessoaSelecionada.getCpf());
        }
    }

    private void atualizarPessoaFisica() {
        try {
            PessoaFisica pessoaSelecionada = (PessoaFisica) cmbPessoasFisicas.getSelectedItem();
            if (pessoaSelecionada == null) {
                JOptionPane.showMessageDialog(this, "Selecione uma Pessoa Física.", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String novoNome = txtNome.getText();
            String novoCpf = txtCpf.getText();

            pessoaSelecionada.setNomeCli(novoNome);
            pessoaSelecionada.setCpf(novoCpf);

            pessoaFisicaService.atualizarPessoaFisica(pessoaSelecionada.getCodCli(), pessoaSelecionada);
            JOptionPane.showMessageDialog(this, "Pessoa Física atualizada com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            carregarPessoasFisicas();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao atualizar Pessoa Física: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void voltarParaMainUI() {
        mainUI.setVisible(true);
        dispose();
    }
}
