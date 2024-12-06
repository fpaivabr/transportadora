package com.transporte.transportadora.ui.cidadeui;

import javax.swing.*;
import java.util.List;

public class FretesPJUI extends JFrame {
    public FretesPJUI(List<String[]> resultados) {
        setTitle("Fretes Atendidos por Pessoas Jurídicas");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        String[] colunas = {"Numero Conhecido","Data Frete", "Razão Social", "CNPJ", "Representante"};
        JTable tabela = new JTable(resultados.toArray(new Object[0][0]), colunas);
        add(new JScrollPane(tabela));

        setLocationRelativeTo(null);
    }
}

