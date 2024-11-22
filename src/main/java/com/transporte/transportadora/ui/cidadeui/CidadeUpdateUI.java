package com.transporte.transportadora.ui.cidadeui;

import com.transporte.transportadora.model.Cidade;
import com.transporte.transportadora.model.Estado;
import com.transporte.transportadora.service.CidadeService;
import com.transporte.transportadora.service.EstadoService;
import com.transporte.transportadora.ui.MainUI;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;
import java.util.List;

@Lazy
@Component
public class CidadeUpdateUI extends JFrame {

    private JComboBox<String> cmbCidades;
    private JTextField txtNome;
    private JComboBox<String> cmbEstado;
    private JFormattedTextField txtPrecoUnitPeso;
    private JFormattedTextField txtPrecoUnitValor;
    private JButton btnAtualizar;
    private JButton btnVoltar;

    private final CidadeService cidadeService;
    private final EstadoService estadoService;
    private final MainUI mainUI;

    public CidadeUpdateUI(CidadeService cidadeService, MainUI mainUI, EstadoService estadoService) {
        this.cidadeService = cidadeService;
        this.estadoService = estadoService;
        this.mainUI = mainUI;
        initUI();
    }

    private void initUI() {
        setTitle("Atualizar Cidade");
        setSize(400, 250);
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
        cmbCidades.addActionListener(e -> preencherCampos());
        add(cmbCidades, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        add(new JLabel("Nome da Cidade:"), gbc);
        gbc.gridx = 1;
        txtNome = new JTextField(20);
        add(txtNome, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        btnAtualizar = new JButton("Atualizar");
        add(btnAtualizar, gbc);

        btnAtualizar.addActionListener(e -> atualizarCidade());

        gbc.gridy++;
        btnVoltar = new JButton("Voltar");
        btnVoltar.addActionListener(e -> voltarParaMainUI());
        add(btnVoltar, gbc);

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

    private void carregarEstados() {
        try {
            List<Estado> estados = estadoService.listarTodos();
            for (Estado estado : estados) {
                cmbEstado.addItem(estado.getNome());
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar estados: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void preencherCampos() {
        Cidade cidade = (Cidade) cmbCidades.getSelectedItem();
        if (cidade != null) {
            txtNome.setText(cidade.getNome());
        }
    }

    private void atualizarCidade() {
        try {
            Cidade cidade = (Cidade) cmbCidades.getSelectedItem();
            if (cidade == null) {
                JOptionPane.showMessageDialog(this, "Selecione uma cidade.", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            cidade.setNome(txtNome.getText());

            // Verificar o identificador correto (por exemplo: cidade.getId() ou cidade.getCodCidade())
            cidadeService.atualizarCidade(cidade.getId(), cidade);

            JOptionPane.showMessageDialog(this, "Cidade atualizada com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao atualizar cidade: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void voltarParaMainUI() {
        mainUI.setVisible(true);
        dispose();
    }
}
