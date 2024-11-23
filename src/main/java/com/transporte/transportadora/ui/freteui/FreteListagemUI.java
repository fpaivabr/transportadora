
package com.transporte.transportadora.ui.freteui;

import com.transporte.transportadora.model.Frete;
import com.transporte.transportadora.service.FreteService;
import com.transporte.transportadora.service.impl.FreteServiceImpl;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

@Lazy
@Component
public class FreteListagemUI extends JFrame {

    private JTable tabelaFretes;
    private DefaultTableModel tableModel;
    private JButton btnAtualizar;
    private final FreteService freteService;

    public FreteListagemUI(FreteService freteService) {
        this.freteService = freteService;
        setTitle("Listagem de Fretes");
        setSize(800, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        String[] colunas = {"ID", "Data do Frete", "Peso", "Valor", "ICMS", "Pedágio", "Remetente", "Destinatário", "Origem", "Destino"};
        tableModel = new DefaultTableModel(colunas, 0);
        tabelaFretes = new JTable(tableModel);

        JScrollPane scrollPane = new JScrollPane(tabelaFretes);
        add(scrollPane, BorderLayout.CENTER);

        btnAtualizar = new JButton("Atualizar Listagem");
        btnAtualizar.addActionListener(e -> carregarFretes());
        add(btnAtualizar, BorderLayout.SOUTH);

        carregarFretes();

        setLocationRelativeTo(null);
        setVisible(true);
    }

    /**
     * Método para carregar os fretes e preencher a tabela.
     */
    private void carregarFretes() {
        try {
            tableModel.setRowCount(0);

            List<Frete> fretes = freteService.listarTodos();

            for (Frete frete : fretes) {
                Object[] linha = {
                        frete.getNumConhec(),
                        frete.getDataFrete(),
                        frete.getPeso(),
                        frete.getValor(),
                        frete.getIcms(),
                        frete.getPedagio(),
                        frete.getRemetente().getPessoaFisica() != null ? frete.getRemetente().getPessoaFisica().getNomeCli() : frete.getRemetente().getPessoaJuridica().getRazaoSocial(),
                        frete.getDestinatario().getPessoaFisica() != null ? frete.getDestinatario().getPessoaFisica().getNomeCli() : frete.getDestinatario().getPessoaJuridica().getRazaoSocial(),
                        frete.getOrigem().getNome(),
                        frete.getDestino().getNome()
                };
                tableModel.addRow(linha);
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar fretes: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}
