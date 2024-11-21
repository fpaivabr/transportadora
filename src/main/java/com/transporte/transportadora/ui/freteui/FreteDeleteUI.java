package com.transporte.transportadora.ui.freteui;

import com.transporte.transportadora.model.Frete;
import com.transporte.transportadora.service.FreteService;
import com.transporte.transportadora.service.impl.FreteServiceImpl;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class FreteDeleteUI extends JFrame {

    private JComboBox<Frete> cmbFretes;
    private JButton btnDeletar;
    private FreteService freteService;

    public FreteDeleteUI() {
        freteService = new FreteServiceImpl();
        setTitle("Deletar Frete");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;

        cmbFretes = new JComboBox<>();
        carregarFretes();
        add(cmbFretes, gbc);

        gbc.gridy++;
        btnDeletar = new JButton("Deletar Frete");
        add(btnDeletar, gbc);

        btnDeletar.addActionListener(e -> deletarFrete());

        setLocationRelativeTo(null);
        setVisible(true);
    }

    /**
     * Método para carregar a lista de fretes e preencher o JComboBox.
     */
    private void carregarFretes() {
        try {
            List<Frete> fretes = freteService.listarTodos();

            if (fretes.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Nenhum frete cadastrado.", "Informação", JOptionPane.INFORMATION_MESSAGE);
                btnDeletar.setEnabled(false);
            } else {
                for (Frete frete : fretes) {
                    cmbFretes.addItem(frete);
                }
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar fretes: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Método para deletar o frete selecionado.
     */
    private void deletarFrete() {
        try {
            Frete freteSelecionado = (Frete) cmbFretes.getSelectedItem();

            if (freteSelecionado == null) {
                JOptionPane.showMessageDialog(this, "Selecione um frete para deletar.", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int confirmacao = JOptionPane.showConfirmDialog(
                    this,
                    "Tem certeza que deseja deletar o frete selecionado?",
                    "Confirmação",
                    JOptionPane.YES_NO_OPTION
            );

            if (confirmacao == JOptionPane.YES_OPTION) {
                freteService.deletarFrete(freteSelecionado.getNumConhec());
                JOptionPane.showMessageDialog(this, "Frete deletado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                cmbFretes.removeItem(freteSelecionado);
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao deletar frete: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        new FreteDeleteUI();
    }
}
