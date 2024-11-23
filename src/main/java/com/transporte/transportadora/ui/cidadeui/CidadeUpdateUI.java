package com.transporte.transportadora.ui.cidadeui;

import com.transporte.transportadora.model.Cidade;
import com.transporte.transportadora.model.Estado;
import com.transporte.transportadora.service.CidadeService;
import com.transporte.transportadora.service.EstadoService;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import javax.swing.*;
import javax.swing.text.NumberFormatter;
import java.awt.*;
import java.util.List;

@Lazy
@Component
public class CidadeUpdateUI extends JFrame {

    private JComboBox<Cidade> cmbCidades;
    private JTextField txtNome;
    private JComboBox<Estado> cmbEstado;
    private JFormattedTextField txtPrecoUnitPeso;
    private JFormattedTextField txtPrecoUnitValor;
    private JButton btnAtualizar;

    private final CidadeService cidadeService;
    private final EstadoService estadoService;

    public CidadeUpdateUI(EstadoService estadoService, CidadeService cidadeService) {
        this.cidadeService = cidadeService;
        this.estadoService = estadoService;
        initUI();
    }

    private void initUI() {
        setTitle("Atualizar Cidade");
        setSize(500, 350);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;

        // ComboBox para selecionar a cidade
        add(new JLabel("Selecione a Cidade:"), gbc);
        gbc.gridx = 1;
        cmbCidades = new JComboBox<>();
        carregarCidades();
        add(cmbCidades, gbc);
        cmbCidades.addActionListener(e -> preencherCampos());

        // Campo para o nome da cidade
        gbc.gridx = 0;
        gbc.gridy++;
        add(new JLabel("Nome da Cidade:"), gbc);
        gbc.gridx = 1;
        txtNome = new JTextField(20);
        add(txtNome, gbc);

        // ComboBox para selecionar o estado
        gbc.gridx = 0;
        gbc.gridy++;
        add(new JLabel("Estado:"), gbc);
        gbc.gridx = 1;
        cmbEstado = new JComboBox<>();
        carregarEstados();
        add(cmbEstado, gbc);

        // Campo para preço por peso
        gbc.gridx = 0;
        gbc.gridy++;
        add(new JLabel("Preço por Peso:"), gbc);
        gbc.gridx = 1;
        NumberFormatter numberFormatter = new NumberFormatter();
        numberFormatter.setAllowsInvalid(false);
        txtPrecoUnitPeso = new JFormattedTextField(numberFormatter);
        add(txtPrecoUnitPeso, gbc);

        // Campo para preço por valor
        gbc.gridx = 0;
        gbc.gridy++;
        add(new JLabel("Preço por Valor:"), gbc);
        gbc.gridx = 1;
        txtPrecoUnitValor = new JFormattedTextField(numberFormatter);
        add(txtPrecoUnitValor, gbc);

        // Botão de atualização
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        btnAtualizar = new JButton("Atualizar");
        add(btnAtualizar, gbc);
        btnAtualizar.addActionListener(e -> atualizarCidade());

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

    private void carregarEstados() {
        try {
            List<Estado> estados = estadoService.listarTodos();
            for (Estado estado : estados) {
                cmbEstado.addItem(estado);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar estados: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void preencherCampos() {
        Cidade cidade = (Cidade) cmbCidades.getSelectedItem();
        if (cidade != null) {
            txtNome.setText(cidade.getNome());
            cmbEstado.setSelectedItem(cidade.getEstado());
            txtPrecoUnitPeso.setValue(cidade.getPrecoUnitPeso());
            txtPrecoUnitValor.setValue(cidade.getPrecoUnitValor());
        }
    }

    private void atualizarCidade() {
        try {
            Cidade cidade = (Cidade) cmbCidades.getSelectedItem();
            if (cidade == null) {
                JOptionPane.showMessageDialog(this, "Selecione uma cidade.", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (txtNome.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "O nome da cidade é obrigatório.", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (cmbEstado.getSelectedItem() == null) {
                JOptionPane.showMessageDialog(this, "Selecione um estado.", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            cidade.setNome(txtNome.getText());
            cidade.setEstado((Estado) cmbEstado.getSelectedItem());
            cidade.setPrecoUnitPeso(((Number) txtPrecoUnitPeso.getValue()).doubleValue());
            cidade.setPrecoUnitValor(((Number) txtPrecoUnitValor.getValue()).doubleValue());

            cidadeService.atualizarCidade(cidade.getId(), cidade);
            JOptionPane.showMessageDialog(this, "Cidade atualizada com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao atualizar cidade: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}