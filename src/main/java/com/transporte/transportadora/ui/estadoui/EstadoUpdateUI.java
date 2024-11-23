package com.transporte.transportadora.ui.estadoui;

import com.transporte.transportadora.model.Estado;
import com.transporte.transportadora.service.EstadoService;
import com.transporte.transportadora.service.impl.EstadoServiceImpl;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import javax.swing.*;
import javax.swing.text.NumberFormatter;
import java.awt.*;
import java.text.DecimalFormat;
import java.util.List;
@Lazy
@Component
public class EstadoUpdateUI extends JFrame {

    private JComboBox<Estado> cmbEstados;
    private JTextField txtNome;
    private JFormattedTextField txtIcmsLocal;
    private JFormattedTextField txtIcmsOutroUf;
    private JButton btnAtualizar;

    private final EstadoService estadoService;

    public EstadoUpdateUI(EstadoService estadoService) {
        this.estadoService = estadoService;
        initUI();
    }
    private void initUI() {
        setTitle("Atualizar Estado");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;

        add(new JLabel("Selecione o Estado:"), gbc);
        gbc.gridx = 1;
        cmbEstados = new JComboBox<>();
        carregarEstados();
        add(cmbEstados, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
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
        btnAtualizar = new JButton("Atualizar");
        add(btnAtualizar, gbc);

        btnAtualizar.addActionListener(e -> atualizarEstado());

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void carregarEstados() {
        try {
            cmbEstados.removeAllItems(); // Limpa o JComboBox antes de recarregar
            List<Estado> estados = estadoService.listarTodos();
            for (Estado estado : estados) {
                cmbEstados.addItem(estado);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar estados: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void atualizarEstado() {
        try {
            Estado estado = (Estado) cmbEstados.getSelectedItem();
            if (estado == null) {
                JOptionPane.showMessageDialog(this, "Selecione um estado.", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            estado.setNome(txtNome.getText());
            estado.setIcmsLocal(((Number) txtIcmsLocal.getValue()).doubleValue());
            estado.setIcmsOutroUf(((Number) txtIcmsOutroUf.getValue()).doubleValue());

            estadoService.atualizarEstado(estado.getId(), estado);

            JOptionPane.showMessageDialog(this, "Estado atualizado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);

            carregarEstados();
            cmbEstados.setSelectedItem(estado);

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao atualizar estado: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }


}