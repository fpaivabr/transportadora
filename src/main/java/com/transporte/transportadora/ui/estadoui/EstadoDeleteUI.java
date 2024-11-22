package com.transporte.transportadora.ui.estadoui;

import com.transporte.transportadora.model.Estado;
import com.transporte.transportadora.service.EstadoService;
import com.transporte.transportadora.service.impl.EstadoServiceImpl;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;
import java.util.List;
@Lazy
@Component
public class EstadoDeleteUI extends JFrame {

    private JComboBox<Estado> cmbEstados;
    private JButton btnExcluir;

    private final EstadoService estadoService;

    public EstadoDeleteUI(EstadoService estadoService) {
        this.estadoService = estadoService;
        initUI();
    }
        private void initUI() {
        setTitle("Excluir Estado");
        setSize(300, 150);
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
        gbc.gridwidth = 2;
        btnExcluir = new JButton("Excluir");
        add(btnExcluir, gbc);

        btnExcluir.addActionListener(e -> excluirEstado());

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void carregarEstados() {
        try {
            List<Estado> estados = estadoService.listarTodos();
            for (Estado estado : estados) {
                cmbEstados.addItem(estado);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar estados: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void excluirEstado() {
        try {
            Estado estado = (Estado) cmbEstados.getSelectedItem();
            if (estado == null) {
                JOptionPane.showMessageDialog(this, "Selecione um estado.", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            estadoService.deletarEstado(estado.getId());
            JOptionPane.showMessageDialog(this, "Estado excluído com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            carregarEstados();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao excluir estado: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}
