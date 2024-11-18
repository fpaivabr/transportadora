package com.transporte.transportadora.ui.pessoajuridicaui;

import com.transporte.transportadora.model.PessoaJuridica;
import com.transporte.transportadora.service.PessoaJuridicaService;
import com.transporte.transportadora.service.impl.PessoaJuridicaServiceImpl;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class PessoaJuridicaReadUI extends JFrame {

    private JTable tabelaPessoasJuridicas;
    private PessoaJuridicaService pessoaJuridicaService;

    public PessoaJuridicaReadUI() {
        pessoaJuridicaService = new PessoaJuridicaServiceImpl();
        setTitle("Listar Pessoas Jurídicas");
        setSize(500, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        String[] colunas = {"ID", "Razão Social", "CNPJ", "Inscrição Estadual"};
        String[][] dados = carregarDados();
        tabelaPessoasJuridicas = new JTable(dados, colunas);
        JScrollPane scrollPane = new JScrollPane(tabelaPessoasJuridicas);

        add(scrollPane, BorderLayout.CENTER);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private String[][] carregarDados() {
        try {
            List<PessoaJuridica> pessoasJuridicas = pessoaJuridicaService.listarTodos();
            String[][] dados = new String[pessoasJuridicas.size()][4];
            for (int i = 0; i < pessoasJuridicas.size(); i++) {
                PessoaJuridica pessoa = pessoasJuridicas.get(i);
                dados[i][0] = pessoa.getCodCli().toString();
                dados[i][1] = pessoa.getRazaoSocial();
                dados[i][2] = pessoa.getCnpj();
                dados[i][3] = pessoa.getInscEstadual();
            }
            return dados;
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar dados: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            return new String[0][0];
        }
    }

    public static void main(String[] args) {
        new PessoaJuridicaReadUI();
    }
}
