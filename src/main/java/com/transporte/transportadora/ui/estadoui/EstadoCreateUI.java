package com.transporte.transportadora.ui.estadoui;

import com.transporte.transportadora.model.Estado;
import com.transporte.transportadora.service.EstadoService;
import com.transporte.transportadora.ui.MainUI;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import javax.swing.*;
import javax.swing.text.NumberFormatter;
import java.awt.*;
import java.text.DecimalFormat;

@Lazy
@Component
public class EstadoCreateUI extends JFrame {

    private JTextField txtNome;
    private JFormattedTextField txtIcmsLocal;
    private JFormattedTextField txtIcmsOutroUf;
    private JButton btnSalvar;
    private JButton btnVoltar;

    private final EstadoService estadoService;
    private final MainUI mainUI;

    public EstadoCreateUI(EstadoService estadoService, MainUI mainUI) {
        this.estadoService = estadoService;
        this.mainUI = mainUI;
        initUI();
    }

    private void initUI() {
        setTitle("Cadastro de Estado");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;

        add(new JLabel("Nome do Estado:"), gbc);
        gbc.gridx = 1;
        txtNome = new JTextField(20);
        add(txtNome, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        add(new JLabel("ICMS Local:"), gbc);
        gbc.gridx = 1;
        NumberFormatter numberFormatter = new NumberFormatter(new DecimalFormat("#0.00"));
        numberFormatter.setAllowsInvalid(false);
        txtIcmsLocal = new JFormattedTextField(numberFormatter);
        add(txtIcmsLocal, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        add(new JLabel("ICMS Outro UF:"), gbc);
        gbc.gridx = 1;
        txtIcmsOutroUf = new JFormattedTextField(numberFormatter);
        add(txtIcmsOutroUf, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        btnSalvar = new JButton("Salvar");
        add(btnSalvar, gbc);

        btnSalvar.addActionListener(e -> salvarEstado());

        gbc.gridy++;
        btnVoltar = new JButton("Voltar");
        btnVoltar.addActionListener(e -> voltarParaMainUI());
        add(btnVoltar, gbc);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void salvarEstado() {
        try {
            String nome = txtNome.getText();
            double icmsLocal = ((Number) txtIcmsLocal.getValue()).doubleValue();
            double icmsOutroUf = ((Number) txtIcmsOutroUf.getValue()).doubleValue();

            Estado estado = new Estado();
            estado.setNome(nome);
            estado.setIcmsLocal(icmsLocal);
            estado.setIcmsOutroUf(icmsOutroUf);

            estadoService.salvarEstado(estado);
            JOptionPane.showMessageDialog(this, "Estado cadastrado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            limparCampos();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar estado: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void limparCampos() {
        txtNome.setText("");
        txtIcmsLocal.setValue(null);
        txtIcmsOutroUf.setValue(null);
    }

    private void voltarParaMainUI() {
        mainUI.setVisible(true);
        dispose();
    }
}