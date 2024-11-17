package com.transporte.transportadora.ui.freteui;

import com.transporte.transportadora.model.Frete;
import com.transporte.transportadora.service.FreteService;
import com.transporte.transportadora.service.impl.FreteServiceImpl;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;

public class FreteCadastroUI extends JFrame {

    private JTextField txtDataFrete;
    private JTextField txtPeso;
    private JTextField txtValor;
    private JTextField txtIcms;
    private JTextField txtPedagio;
    private JButton btnSalvar;

    private FreteService freteService;

    public FreteCadastroUI() {
        freteService = new FreteServiceImpl();
        setTitle("Cadastro de Frete");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(6, 2));

        txtDataFrete = new JTextField();
        txtPeso = new JTextField();
        txtValor = new JTextField();
        txtIcms = new JTextField();
        txtPedagio = new JTextField();
        btnSalvar = new JButton("Salvar");

        // Adicionando os componentes na tela
        add(new JLabel("Data do Frete (yyyy-mm-dd):"));
        add(txtDataFrete);
        add(new JLabel("Peso:"));
        add(txtPeso);
        add(new JLabel("Valor:"));
        add(txtValor);
        add(new JLabel("ICMS:"));
        add(txtIcms);
        add(new JLabel("Pedágio:"));
        add(txtPedagio);
        add(btnSalvar);

        btnSalvar.addActionListener(e -> salvarFrete());

        setVisible(true);
    }

    private void salvarFrete() {
        try {
            LocalDate dataFrete = LocalDate.parse(txtDataFrete.getText());
            double peso = Double.parseDouble(txtPeso.getText());
            double valor = Double.parseDouble(txtValor.getText());
            double icms = Double.parseDouble(txtIcms.getText());
            double pedagio = Double.parseDouble(txtPedagio.getText());

            if (dataFrete.isBefore(LocalDate.now())) {
                JOptionPane.showMessageDialog(this, "A data do frete não pode ser anterior a hoje.", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Frete frete = new Frete();
            frete.setDataFrete(dataFrete);
            frete.setPeso(peso);
            frete.setValor(valor);
            frete.setIcms(icms);
            frete.setPedagio(pedagio);

            freteService.salvarFrete(frete);
            JOptionPane.showMessageDialog(this, "Frete cadastrado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);

            // Limpar os campos
            limparCampos();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar o frete: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void limparCampos() {
        txtDataFrete.setText("");
        txtPeso.setText("");
        txtValor.setText("");
        txtIcms.setText("");
        txtPedagio.setText("");
    }

    public static void main(String[] args) {
        new FreteCadastroUI();
    }
}
