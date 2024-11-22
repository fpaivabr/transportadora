package com.transporte.transportadora.ui.cidadeui;

import com.transporte.transportadora.model.Cidade;
import com.transporte.transportadora.repository.CidadeRepository;
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

    private final CidadeRepository cidadeRepository;
    private JComboBox<String> cmbCidades;
    private JButton btnDeletar;

    private final CidadeService cidadeService;

    public CidadeDeleteUI(CidadeService cidadeService, CidadeRepository cidadeRepository) {
        this.cidadeService = cidadeService;
        initUI();
        this.cidadeRepository = cidadeRepository;
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
                cmbCidades.addItem(cidade.getNome());
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar cidades: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deletarCidade() {
        try {
            if (cmbCidades.getSelectedItem() == null) {
                JOptionPane.showMessageDialog(this, "Selecione uma cidade.", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }
            Cidade cidade = cidadeRepository.findByNome(cmbCidades.getSelectedItem().toString()).orElse(null);
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

