package com.transporte.transportadora.ui.cidadeui;

import com.transporte.transportadora.model.Cidade;
import com.transporte.transportadora.service.CidadeService;
import com.transporte.transportadora.service.impl.CidadeServiceImpl;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;
import java.util.List;
@Lazy
@Component
public class CidadeDeleteUI extends JFrame {

    private JComboBox<Cidade> cmbCidades;
    private JButton btnDeletar;

    private final CidadeService cidadeService;

    public CidadeDeleteUI(CidadeService cidadeService) {
        this.cidadeService = cidadeService;
        initUI();
    }

    private void initUI() {
        setTitle("Excluir Cidade");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;

        add(new JLabel("Selecione a Cidade:"), gbc);
        gbc.gridx = 1;
        cmbCidades = new JComboBox<>();
        carregarCidades();
        add(cmbCidades, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        btnDeletar = new JButton("Excluir");
        add(btnDeletar, gbc);

        btnDeletar.addActionListener(e -> deletarCidade());

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void carregarCidades() {
        try {
            List<Cidade> cidades = cidadeService.listarTodas();
            for (Cidade cidade : cidades) {
                cmbCidades.addItem(cidade);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar cidades: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deletarCidade() {
        try {
            Cidade cidade = (Cidade) cmbCidades.getSelectedItem();
            if (cidade == null) {
                JOptionPane.showMessageDialog(this, "Selecione uma cidade.", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            cidadeService.deletarCidade(cidade.getId());
            JOptionPane.showMessageDialog(this, "Cidade excluída com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao excluir cidade: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}

