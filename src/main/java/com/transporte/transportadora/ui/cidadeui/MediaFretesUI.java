package com.transporte.transportadora.ui.cidadeui;

import javax.swing.*;
import java.util.List;

public class MediaFretesUI extends JFrame {
    public MediaFretesUI(List<String[]> resultados) {
        setTitle("Média de Fretes (Origem/Destino)");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        String[] colunas = {"Estado", "Cidade", "Média de Fretes Origem", "Média de Fretes Destino"};
        JTable tabela = new JTable(resultados.toArray(new Object[0][0]), colunas);
        add(new JScrollPane(tabela));

        setLocationRelativeTo(null);
    }
}

