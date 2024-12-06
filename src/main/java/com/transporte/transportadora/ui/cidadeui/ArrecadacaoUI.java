package com.transporte.transportadora.ui.cidadeui;

import javax.swing.*;
import java.util.List;

public class ArrecadacaoUI extends JFrame {
    public ArrecadacaoUI(List<String[]> resultados) {
        setTitle("Arrecadação por Cidade/Estado");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        String[] colunas = {"Cidade", "Estado", "Quantidade de Fretes", "Valor Total"};
        JTable tabela = new JTable(resultados.toArray(new Object[0][0]), colunas);
        add(new JScrollPane(tabela));

        setLocationRelativeTo(null);
    }
}
