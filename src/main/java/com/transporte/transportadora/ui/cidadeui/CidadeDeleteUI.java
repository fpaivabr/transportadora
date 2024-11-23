package com.transporte.transportadora.ui.cidadeui;

import com.transporte.transportadora.model.Cidade;
import com.transporte.transportadora.repository.CidadeRepository;
import com.transporte.transportadora.service.CidadeService;
import com.transporte.transportadora.ui.MainUI;
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
    private JButton btnAtualizar;
    private JButton btnVoltar;

    private final CidadeService cidadeService;
    private final MainUI mainUI;

    public CidadeDeleteUI(CidadeService cidadeService, MainUI mainUI, CidadeRepository cidadeRepository) {
        this.cidadeService = cidadeService;
        this.mainUI = mainUI;
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

        gbc.gridy++;
        btnAtualizar = new JButton("Atualizar");
        add(btnAtualizar, gbc);

        btnAtualizar.addActionListener(e -> carregarCidades());

        gbc.gridy++;
        btnVoltar = new JButton("Voltar");
        btnVoltar.addActionListener(e -> voltarParaMainUI());
        add(btnVoltar, gbc);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void carregarCidades() {
        try {
            cmbCidades.removeAllItems();
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

            // Verificar identificador correto (por exemplo: cidade.getCodCidade() ou cidade.getId())
            cidadeService.deletarCidade(cidade.getId()); // Corrija o método conforme o modelo

            JOptionPane.showMessageDialog(this, "Cidade excluída com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);

            // Atualizar lista de cidades no JComboBox
            cmbCidades.removeAllItems();
            carregarCidades();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao excluir cidade: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void voltarParaMainUI() {
        mainUI.setVisible(true);
        dispose();
    }
}
