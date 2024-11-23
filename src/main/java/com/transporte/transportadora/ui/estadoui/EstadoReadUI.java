package com.transporte.transportadora.ui.estadoui;

import com.transporte.transportadora.model.Estado;
import com.transporte.transportadora.service.EstadoService;
import com.transporte.transportadora.ui.MainUI;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;
import java.util.List;

@Lazy
@Component
public class EstadoReadUI extends JFrame {

    private JTable tabelaEstados;
    private JButton btnVoltar;
    private final EstadoService estadoService;
    private final MainUI mainUI;
    private JButton atualizar;

    public EstadoReadUI(EstadoService estadoService, MainUI mainUI) {
        this.estadoService = estadoService;
        this.mainUI = mainUI;
        initUI();
    }

    private void initUI() {
        setTitle("Listar Estados");
        setSize(500, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        String[] colunas = {"ID", "Nome", "ICMS Local", "ICMS Outro UF"};
        String[][] dados = carregarDados();
        tabelaEstados = new JTable(dados, colunas);
        JScrollPane scrollPane = new JScrollPane(tabelaEstados);

        add(scrollPane, BorderLayout.CENTER);

        atualizar = new JButton("Atualizar Tabela");
        atualizar.addActionListener(e -> recarregarDados());
        add(atualizar, BorderLayout.SOUTH);

        btnVoltar = new JButton("Voltar");
        btnVoltar.addActionListener(e -> voltarParaMainUI());
        add(btnVoltar, BorderLayout.SOUTH);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void recarregarDados() {
        try {
            String[] colunas = {"ID", "Nome", "ICMS Local", "ICMS Outro UF"};
            String[][] dados = carregarDados();
            tabelaEstados.setModel(new javax.swing.table.DefaultTableModel(dados, colunas));
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao recarregar dados: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private String[][] carregarDados() {
        try {
            List<Estado> estados = estadoService.listarTodos();
            String[][] dados = new String[estados.size()][4];
            for (int i = 0; i < estados.size(); i++) {
                Estado estado = estados.get(i);
                dados[i][0] = estado.getId().toString();
                dados[i][1] = estado.getNome();
                dados[i][2] = estado.getIcmsLocal().toString();
                dados[i][3] = estado.getIcmsOutroUf().toString();
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