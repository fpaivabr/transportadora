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
public class PessoaFisicaReadUI extends JFrame {

    private JTable tabelaPessoasFisicas;
    private JButton btnVoltar;

    private final PessoaFisicaService pessoaFisicaService;
    private final MainUI mainUI;

    public PessoaFisicaReadUI(PessoaFisicaService pessoaFisicaService, MainUI mainUI) {
        this.pessoaFisicaService = pessoaFisicaService;
        this.mainUI = mainUI;
        initUI();
    }

    private void initUI() {
        setTitle("Listar Pessoas Físicas");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        String[] colunas = {"ID", "Nome", "CPF"};
        String[][] dados = carregarDados();
        tabelaPessoasFisicas = new JTable(dados, colunas);
        JScrollPane scrollPane = new JScrollPane(tabelaPessoasFisicas);
        add(scrollPane, BorderLayout.CENTER);

        btnVoltar = new JButton("Voltar");
        btnVoltar.addActionListener(e -> voltarParaMainUI());
        add(btnVoltar, BorderLayout.SOUTH);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private String[][] carregarDados() {
        try {
            List<PessoaFisica> pessoasFisicas = pessoaFisicaService.listarTodos();
            String[][] dados = new String[pessoasFisicas.size()][3];
            for (int i = 0; i < pessoasFisicas.size(); i++) {
                PessoaFisica pessoa = pessoasFisicas.get(i);
                dados[i][0] = pessoa.getCodCli().toString();
                dados[i][1] = pessoa.getNomeCli();
                dados[i][2] = pessoa.getCpf();
            }
            return dados;
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar dados: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            return new String[0][0];
        }
    }

    private void voltarParaMainUI() {
        mainUI.setVisible(true);
        dispose();
    }
}