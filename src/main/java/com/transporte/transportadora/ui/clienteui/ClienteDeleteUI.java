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
public class ClienteDeleteUI extends JFrame {

    private JComboBox<Cliente> cmbClientes;
    private JButton btnDeletar;
    private JButton btnVoltar;

    private final ClienteService clienteService;
    private final MainUI mainUI;

    public ClienteDeleteUI(ClienteService clienteService, MainUI mainUI) {
        this.clienteService = clienteService;
        this.mainUI = mainUI;
        initUI();
    }

    private void initUI() {
        setTitle("Excluir Cliente");
        setSize(400, 200);
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
        add(cmbClientes, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        btnDeletar = new JButton("Excluir");
        add(btnDeletar, gbc);

        btnDeletar.addActionListener(e -> deletarCliente());

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

    private void deletarCliente() {
        try {
            Cliente cliente = (Cliente) cmbClientes.getSelectedItem();
            if (cliente == null) {
                JOptionPane.showMessageDialog(this, "Selecione um cliente.", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            clienteService.deletarCliente(cliente.getCodCli());
            JOptionPane.showMessageDialog(this, "Cliente excluído com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);

            // Atualizar lista de clientes após exclusão
            cmbClientes.removeAllItems();
            carregarClientes();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao excluir cliente: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void voltarParaMainUI() {
        mainUI.setVisible(true);
        dispose();
    }
}
