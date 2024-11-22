package com.transporte.transportadora.ui.cidadeui;

import com.transporte.transportadora.model.Cidade;
import com.transporte.transportadora.model.Estado;
import com.transporte.transportadora.service.CidadeService;
import com.transporte.transportadora.service.EstadoService;
import com.transporte.transportadora.service.impl.CidadeServiceImpl;
import com.transporte.transportadora.service.impl.EstadoServiceImpl;
import com.transporte.transportadora.ui.MainUI;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import javax.swing.*;
import javax.swing.text.NumberFormatter;
import java.awt.*;
import java.util.List;
@Lazy
@Component
public class CidadeCreateUI extends JFrame {

    private JTextField txtNome;
    private JComboBox<Estado> cmbEstado;
    private JFormattedTextField txtPrecoUnitPeso;
    private JFormattedTextField txtPrecoUnitValor;
    private JButton btnSalvar;
    private JButton btnVoltar;

    private final CidadeService cidadeService;
    private final MainUI mainUI;
    private final EstadoService estadoService;

    public CidadeCreateUI(EstadoService estadoService, CidadeService cidadeService, MainUI mainUI) {
        this.cidadeService = cidadeService;
        this.estadoService = estadoService;
        this.mainUI = mainUI;
        initUI();
    }
        private void initUI() {
            setTitle("Cadastro de Cidade");
            setSize(400, 300);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setLayout(new GridBagLayout());

            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(5, 5, 5, 5);
            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.gridx = 0;
            gbc.gridy = 0;

            add(new JLabel("Nome da Cidade:"), gbc);
            gbc.gridx = 1;
            txtNome = new JTextField(20);
            add(txtNome, gbc);

            gbc.gridx = 0;
            gbc.gridy++;
            add(new JLabel("Estado:"), gbc);
            gbc.gridx = 1;
            cmbEstado = new JComboBox<>();
            carregarEstados();
            add(cmbEstado, gbc);

            gbc.gridx = 0;
            gbc.gridy++;
            add(new JLabel("Preço por Peso:"), gbc);
            gbc.gridx = 1;
            NumberFormatter numberFormatter = new NumberFormatter();
            numberFormatter.setAllowsInvalid(false);
            txtPrecoUnitPeso = new JFormattedTextField(numberFormatter);
            add(txtPrecoUnitPeso, gbc);

            gbc.gridx = 0;
            gbc.gridy++;
            add(new JLabel("Preço por Valor:"), gbc);
            gbc.gridx = 1;
            txtPrecoUnitValor = new JFormattedTextField(numberFormatter);
            add(txtPrecoUnitValor, gbc);

            gbc.gridx = 0;
            gbc.gridy++;
            gbc.gridwidth = 2;
            btnSalvar = new JButton("Salvar");
            add(btnSalvar, gbc);

            btnSalvar.addActionListener(e -> salvarCidade());

            gbc.gridy++;
            btnVoltar = new JButton("Voltar");
            add(btnVoltar, gbc);

            btnVoltar.addActionListener(e -> voltarParaMainUI());

            setLocationRelativeTo(null);
            setVisible(true);
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

    private void salvarCidade() {
        try {
            String nome = txtNome.getText();
            Estado estado = (Estado) cmbEstado.getSelectedItem();
            Double precoPeso = ((Number) txtPrecoUnitPeso.getValue()).doubleValue();
            Double precoValor = ((Number) txtPrecoUnitValor.getValue()).doubleValue();

            Cidade cidade = new Cidade();
            cidade.setNome(nome);
            cidade.setEstado(estado);
            cidade.setPrecoUnitPeso(precoPeso);
            cidade.setPrecoUnitValor(precoValor);

            cidadeService.salvarCidade(cidade);
            JOptionPane.showMessageDialog(this, "Cidade salva com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar cidade: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
    private void voltarParaMainUI() {
        mainUI.setVisible(true); // Torna MainUI visível novamente
        dispose(); // Fecha a janela atual
    }
}
