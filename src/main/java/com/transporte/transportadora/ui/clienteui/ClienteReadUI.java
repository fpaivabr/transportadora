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
public class ClienteReadUI extends JFrame {

    private JTable tabelaClientes;
    private JButton btnVoltar;
    private final ClienteService clienteService;
    private final MainUI mainUI;

    public ClienteReadUI(ClienteService clienteService, MainUI mainUI) {
        this.clienteService = clienteService;
        this.mainUI = mainUI;
        initUI();
    }

    private void initUI() {
        setTitle("Listar Clientes");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        String[] colunas = {"ID", "Endereço", "Telefone", "Tipo"};
        String[][] dados = carregarDados();
        tabelaClientes = new JTable(dados, colunas);
        JScrollPane scrollPane = new JScrollPane(tabelaClientes);

        add(scrollPane, BorderLayout.CENTER);

        btnVoltar = new JButton("Voltar");
        btnVoltar.addActionListener(e -> voltarParaMainUI());
        add(btnVoltar, BorderLayout.SOUTH);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private String[][] carregarDados() {
        try {
            List<Cliente> clientes = clienteService.listarTodos();
            String[][] dados = new String[clientes.size()][4];
            for (int i = 0; i < clientes.size(); i++) {
                Cliente cliente = clientes.get(i);
                dados[i][0] = cliente.getCodCli().toString();
                dados[i][1] = cliente.getEndereco();
                dados[i][2] = cliente.getTelefone();
                dados[i][3] = cliente.getTipoCliente().toString();
            }
            return dados;
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar dados: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            return new String[0][0];
        }
    }

    private void voltarParaMainUI() {
        mainUI.setVisible(true);
        dispose();
    }
}
