package com.transporte.transportadora.ui.cidadeui;

import com.transporte.transportadora.model.Cidade;
import com.transporte.transportadora.service.CidadeService;
import com.transporte.transportadora.ui.MainUI;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;
import java.util.List;

@Lazy
@Component
public class CidadeReadUI extends JFrame {

    private JTable tabelaCidades;
    private JButton btnVoltar;
    private final CidadeService cidadeService;
    private final MainUI mainUI;

    public CidadeReadUI(CidadeService cidadeService, MainUI mainUI) {
        this.cidadeService = cidadeService;
        this.mainUI = mainUI;
        initUI();
    }

    private void initUI() {
        setTitle("Listar Cidades");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // Configuração da tabela
        String[] colunas = {"ID", "Nome", "Estado", "Preço/Peso", "Preço/Valor"};
        String[][] dados = carregarDados();
        tabelaCidades = new JTable(dados, colunas);
        JScrollPane scrollPane = new JScrollPane(tabelaCidades);

        add(scrollPane, BorderLayout.CENTER);

        // Botão Voltar
        btnVoltar = new JButton("Voltar");
        btnVoltar.addActionListener(e -> voltarParaMainUI());
        add(btnVoltar, BorderLayout.SOUTH);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private String[][] carregarDados() {
        try {
            List<Cidade> cidades = cidadeService.listarTodas();
            String[][] dados = new String[cidades.size()][5];
            for (int i = 0; i < cidades.size(); i++) {
                Cidade cidade = cidades.get(i);
                dados[i][0] = cidade.getId().toString();
                dados[i][1] = cidade.getNome();
                dados[i][2] = cidade.getEstado().getNome();
                dados[i][3] = cidade.getPrecoUnitPeso().toString();
                dados[i][4] = cidade.getPrecoUnitValor().toString();
            }
            return dados;
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar dados: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            return new String[0][0];
        }
    }

    private void voltarParaMainUI() {
        mainUI.setVisible(true); // Torna MainUI visível novamente
        dispose(); // Fecha a janela atual
    }
}
