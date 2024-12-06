package com.transporte.transportadora.ui;

import com.transporte.transportadora.model.Estado;
import com.transporte.transportadora.repository.EstadoRepository;
import com.transporte.transportadora.service.BuscaService;
import com.transporte.transportadora.ui.cidadeui.ArrecadacaoUI;
import com.transporte.transportadora.ui.cidadeui.FretesPJUI;
import com.transporte.transportadora.ui.cidadeui.MediaFretesUI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;
import java.util.List;

@Component
public class MainUI extends JFrame {

    private final EstadoRepository estadoRepository;
    private final BuscaService buscaService;

    private JComboBox<String> tabelaComboBox;
    private JComboBox<String> estadoComboBox;
    private JComboBox<String> mesComboBox;
    private JTextField anoTextField;
    private JButton confirmarButton;
    private JButton carregarEstadosButton;

    @Autowired
    public MainUI(EstadoRepository estadoRepository, BuscaService buscaService) {
        this.estadoRepository = estadoRepository;
        this.buscaService = buscaService;
        initUI();
    }

    private void initUI() {
        setTitle("Sistema de Transporte - Tela Inicial");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Seleção de Operação
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(new JLabel("Selecione a Operação:"), gbc);

        tabelaComboBox = new JComboBox<>(new String[]{
                "",
                "Arrecadação por cidade/estado no ano de 2024",
                "Quantidade média de fretes (origem/destino)",
                "Fretes atendidos por PJ no mês/ano"
        });
        gbc.gridx = 1;
        add(tabelaComboBox, gbc);

        // Seleção de Estado
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(new JLabel("Estado:"), gbc);

        estadoComboBox = new JComboBox<>(new String[]{"Carregue os estados"});
        gbc.gridx = 1;
        add(estadoComboBox, gbc);

        // Botão para carregar estados
        carregarEstadosButton = new JButton("Carregar Estados");
        carregarEstadosButton.addActionListener(e -> carregarEstados());
        gbc.gridx = 2;
        add(carregarEstadosButton, gbc);

        // Seleção de Mês
        gbc.gridx = 0;
        gbc.gridy = 2;
        add(new JLabel("Mês:"), gbc);

        mesComboBox = new JComboBox<>(getMeses());
        gbc.gridx = 1;
        add(mesComboBox, gbc);

        // Entrada de Ano
        gbc.gridx = 0;
        gbc.gridy = 3;
        add(new JLabel("Ano:"), gbc);

        anoTextField = new JTextField();
        gbc.gridx = 1;
        add(anoTextField, gbc);

        // Botão Confirmar
        confirmarButton = new JButton("Confirmar");
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        add(confirmarButton, gbc);

        confirmarButton.addActionListener(e -> abrirTela());

        setLocationRelativeTo(null);
        setVisible(true);
    }

    public String[] getMeses() {
        return new String[]{
                "Janeiro", "Fevereiro", "Março", "Abril", "Maio", "Junho",
                "Julho", "Agosto", "Setembro", "Outubro", "Novembro", "Dezembro"
        };
    }

    private void carregarEstados() {
        try {
            String[] estados = {"São Pauloooo",
                    "Rio de Janeiroooo",
                    "TESTE_CIDADE",
                    "São Paulo",
                    "Rio de Janeiro"};
            for (String estado : estados) {
                estadoComboBox.addItem(estado);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar estados: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void abrirTela() {
        String operacao = (String) tabelaComboBox.getSelectedItem();
        String estado = (String) estadoComboBox.getSelectedItem();
        String mes = (String) mesComboBox.getSelectedItem();
        String ano = anoTextField.getText();

        if (operacao == null || operacao.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, selecione uma operação.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        switch (operacao) {
            case "Arrecadação por cidade/estado no ano de 2024":
                if (estado == null || estado.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Por favor, selecione um estado.", "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                new ArrecadacaoUI(buscaService.buscarArrecadacaoPorEstado(estado)).setVisible(true);
                break;

            case "Quantidade média de fretes (origem/destino)":
                if (estado == null || estado.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Por favor, selecione um estado.", "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                new MediaFretesUI(buscaService.buscarMediaFretes(estado)).setVisible(true);
                break;

            case "Fretes atendidos por PJ no mês/ano":
                if (mes == null || mes.isEmpty() || ano.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Por favor, preencha mês e ano.", "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                new FretesPJUI(buscaService.buscarFretesAtendidosPorPJ(mes, ano)).setVisible(true);
                break;

            default:
                JOptionPane.showMessageDialog(this, "Operação inválida.", "Erro", JOptionPane.ERROR_MESSAGE);
                break;
        }
    }
}
