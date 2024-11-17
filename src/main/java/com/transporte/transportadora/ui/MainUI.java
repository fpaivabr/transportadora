package com.transporte.transportadora.ui;

import com.transporte.transportadora.ui.freteui.FreteCadastroUI;
import com.transporte.transportadora.ui.freteui.FreteUpdateUI;

import javax.swing.*;
import java.awt.*;

public class MainUI extends JFrame {

    private JButton btnCadastrarFrete;
    private JButton btnUpdateFrete;

    public MainUI() {
        setTitle("Sistema de Transporte - Tela Inicial");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        btnCadastrarFrete = new JButton("Cadastrar Frete");

        btnUpdateFrete = new JButton("Atualizar Frete");

        // Adicionando o botão na tela
        add(btnCadastrarFrete);

        add(btnUpdateFrete);

        // Evento de clique no botão "Cadastrar Frete"
        btnCadastrarFrete.addActionListener(e -> abrirTelaCadastroFrete());
        btnUpdateFrete.addActionListener(e -> abrirTelaAtualizarCadastroDeFrete());
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void abrirTelaCadastroFrete() {
        this.setVisible(false);
        FreteCadastroUI cadastroUI = new FreteCadastroUI();
        cadastroUI.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                setVisible(true);
            }
        });
    }

    private void abrirTelaAtualizarCadastroDeFrete() {
        this.setVisible(false);
        FreteUpdateUI updateUI = new FreteUpdateUI();
        updateUI.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                setVisible(true);
            }
        });
    }

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(MainUI::new);
    }
}
