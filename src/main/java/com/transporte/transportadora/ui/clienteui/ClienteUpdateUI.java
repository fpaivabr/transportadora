package com.transporte.transportadora.ui.clienteui;

import com.transporte.transportadora.model.Cliente;
import com.transporte.transportadora.service.ClienteService;
import com.transporte.transportadora.ui.MainUI;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;
import java.util.List;

@Lazy
@Component
public class ClienteUpdateUI extends JFrame {

    private JComboBox<Cliente> cmbClientes;
    private JTextField txtEndereco;
    private JTextField txtTelefone;
    private JButton btnAtualizar;
    private JButton btnVoltar;

    private final ClienteService clienteService;
    private final MainUI mainUI;

    public ClienteUpdateUI(ClienteService clienteService, MainUI mainUI) {
        this.clienteService = clienteService;
        this.mainUI = mainUI;
        initUI();
    }

    private void initUI() {
        setTitle("Atualizar Cliente");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;

        add(new JLabel("Selecione o Cliente:"), gbc);
        gbc.gridx = 1;
        cmbClientes = new JComboBox<>();
        carregarClientes();
        cmbClientes.addActionListener(e -> preencherCampos());
        add(cmbClientes, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        add(new JLabel("Endereço:"), gbc);
        gbc.gridx = 1;
        txtEndereco = new JTextField(20);
        add(txtEndereco, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        add(new JLabel("Telefone:"), gbc);
        gbc.gridx = 1;
        txtTelefone = new JTextField(20);
        add(txtTelefone, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        btnAtualizar = new JButton("Atualizar");
        btnAtualizar.addActionListener(e -> atualizarCliente());
        add(btnAtualizar, gbc);

        gbc.gridy++;
        btnVoltar = new JButton("Voltar");
        btnVoltar.addActionListener(e -> voltarParaMainUI());
        add(btnVoltar, gbc);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void carregarClientes() {
        try {
            List<Cliente> clientes = clienteService.listarTodos();
            for (Cliente cliente : clientes) {
                cmbClientes.addItem(cliente);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar clientes: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void preencherCampos() {
        Cliente cliente = (Cliente) cmbClientes.getSelectedItem();
        if (cliente != null) {
            txtEndereco.setText(cliente.getEndereco());
            txtTelefone.setText(cliente.getTelefone());
        }
    }

    private void atualizarCliente() {
        try {
            Cliente cliente = (Cliente) cmbClientes.getSelectedItem();
            if (cliente == null) {
                JOptionPane.showMessageDialog(this, "Selecione um cliente.", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            cliente.setEndereco(txtEndereco.getText());
            cliente.setTelefone(txtTelefone.getText());

            clienteService.atualizarCliente(cliente.getCodCli(), cliente);
            JOptionPane.showMessageDialog(this, "Cliente atualizado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao atualizar cliente: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void voltarParaMainUI() {
        mainUI.setVisible(true);
        dispose();
    }
}
