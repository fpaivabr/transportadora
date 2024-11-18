package com.transporte.transportadora.ui.clienteui;

import com.transporte.transportadora.model.Cliente;
import com.transporte.transportadora.model.PessoaFisica;
import com.transporte.transportadora.model.PessoaJuridica;
import com.transporte.transportadora.model.TipoCliente;
import com.transporte.transportadora.service.ClienteService;
import com.transporte.transportadora.service.impl.ClienteServiceImpl;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ClienteCreateUI extends JFrame {

    private JTextField txtEndereco;
    private JTextField txtTelefone;
    private JTextField txtDataInsc;
    private JComboBox<TipoCliente> cmbTipoCliente;
    private JTextField txtNomeCli; // Campo exclusivo para Pessoa Física
    private JTextField txtCpf; // Campo exclusivo para Pessoa Física
    private JTextField txtRazaoSocial; // Campo exclusivo para Pessoa Jurídica
    private JTextField txtCnpj; // Campo exclusivo para Pessoa Jurídica
    private JTextField txtInscEstadual; // Campo exclusivo para Pessoa Jurídica
    private JButton btnSalvar;

    private ClienteService clienteService;
    private DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public ClienteCreateUI() {
        clienteService = new ClienteServiceImpl();

        setTitle("Cadastrar Cliente");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;

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
        add(new JLabel("Data de Inscrição (dd/MM/yyyy):"), gbc);
        gbc.gridx = 1;
        txtDataInsc = new JTextField(20);
        add(txtDataInsc, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        add(new JLabel("Tipo de Cliente:"), gbc);
        gbc.gridx = 1;
        cmbTipoCliente = new JComboBox<>(TipoCliente.values());
        cmbTipoCliente.addActionListener(e -> atualizarCamposEspecificos());
        add(cmbTipoCliente, gbc);

        // Campos para Pessoa Física
        gbc.gridx = 0;
        gbc.gridy++;
        add(new JLabel("Nome:"), gbc);
        gbc.gridx = 1;
        txtNomeCli = new JTextField(20);
        add(txtNomeCli, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        add(new JLabel("CPF:"), gbc);
        gbc.gridx = 1;
        txtCpf = new JTextField(20);
        add(txtCpf, gbc);

        // Campos para Pessoa Jurídica
        gbc.gridx = 0;
        gbc.gridy++;
        add(new JLabel("Razão Social:"), gbc);
        gbc.gridx = 1;
        txtRazaoSocial = new JTextField(20);
        add(txtRazaoSocial, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        add(new JLabel("CNPJ:"), gbc);
        gbc.gridx = 1;
        txtCnpj = new JTextField(20);
        add(txtCnpj, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        add(new JLabel("Inscrição Estadual:"), gbc);
        gbc.gridx = 1;
        txtInscEstadual = new JTextField(20);
        add(txtInscEstadual, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        btnSalvar = new JButton("Salvar");
        btnSalvar.addActionListener(e -> salvarCliente());
        gbc.gridwidth = 2;
        add(btnSalvar, gbc);

        atualizarCamposEspecificos();

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void atualizarCamposEspecificos() {
        TipoCliente tipoSelecionado = (TipoCliente) cmbTipoCliente.getSelectedItem();

        boolean isPessoaFisica = tipoSelecionado == TipoCliente.PESSOA_FISICA;
        txtNomeCli.setVisible(isPessoaFisica);
        txtCpf.setVisible(isPessoaFisica);

        boolean isPessoaJuridica = tipoSelecionado == TipoCliente.PESSOA_JURIDICA;
        txtRazaoSocial.setVisible(isPessoaJuridica);
        txtCnpj.setVisible(isPessoaJuridica);
        txtInscEstadual.setVisible(isPessoaJuridica);

        validate();
        repaint();
    }

    private void salvarCliente() {
        try {
            String endereco = txtEndereco.getText();
            String telefone = txtTelefone.getText();
            LocalDate dataInsc = LocalDate.parse(txtDataInsc.getText(), dateFormatter);
            TipoCliente tipoCliente = (TipoCliente) cmbTipoCliente.getSelectedItem();

            Cliente cliente = new Cliente();
            cliente.setEndereco(endereco);
            cliente.setTelefone(telefone);
            cliente.setDataInsc(dataInsc);
            cliente.setTipoCliente(tipoCliente);

            if (tipoCliente == TipoCliente.PESSOA_FISICA) {
                PessoaFisica pessoaFisica = new PessoaFisica();
                pessoaFisica.setNomeCli(txtNomeCli.getText());
                pessoaFisica.setCpf(txtCpf.getText());
                cliente.setPessoaFisica(pessoaFisica);
            } else if (tipoCliente == TipoCliente.PESSOA_JURIDICA) {
                PessoaJuridica pessoaJuridica = new PessoaJuridica();
                pessoaJuridica.setRazaoSocial(txtRazaoSocial.getText());
                pessoaJuridica.setCnpj(txtCnpj.getText());
                pessoaJuridica.setInscEstadual(txtInscEstadual.getText());
                cliente.setPessoaJuridica(pessoaJuridica);
            }

            clienteService.salvarCliente(cliente);
            JOptionPane.showMessageDialog(this, "Cliente cadastrado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar cliente: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        new ClienteCreateUI();
    }
}
