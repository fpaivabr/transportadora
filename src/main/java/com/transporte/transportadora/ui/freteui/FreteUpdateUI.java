package com.transporte.transportadora.ui.freteui;

import com.transporte.transportadora.model.Cidade;
import com.transporte.transportadora.model.Cliente;
import com.transporte.transportadora.model.Frete;
import com.transporte.transportadora.service.CidadeService;
import com.transporte.transportadora.service.ClienteService;
import com.transporte.transportadora.service.FreteService;
import com.transporte.transportadora.service.impl.CidadeServiceImpl;
import com.transporte.transportadora.service.impl.ClienteServiceImpl;
import com.transporte.transportadora.service.impl.FreteServiceImpl;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import javax.swing.*;
import javax.swing.text.NumberFormatter;
import java.awt.*;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

@Lazy
@Component
public class FreteUpdateUI extends JFrame {

    private JComboBox<Cliente> cmbRemetente;
    private JComboBox<Cliente> cmbDestinatario;
    private JComboBox<Cidade> cmbOrigem;
    private JComboBox<Cidade> cmbDestino;
    private JComboBox<Frete> cmbFretes;
    private JFormattedTextField txtDataFrete;
    private JFormattedTextField txtPeso;
    private JFormattedTextField txtValor;
    private JFormattedTextField txtIcms;
    private JFormattedTextField txtPedagio;
    private JButton btnBuscar;
    private JButton btnAtualizar;

    private final FreteService freteService;
    private final CidadeService cidadeService;
    private final ClienteService clienteService;
    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public FreteUpdateUI(ClienteService clienteService, CidadeService cidadeService, FreteService freteService) {
        this.freteService = freteService;
        this.cidadeService = cidadeService;
        this.clienteService = clienteService;
        initUI();
    }

    private void initUI() {
        setTitle("Atualizar Frete");
        setSize(500, 700);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;

        // Seleção de Cliente Remetente
        gbc.gridwidth = 2;
        add(new JLabel("Selecione o Remetente do Frete:"), gbc);
        gbc.gridy++;
        cmbRemetente = new JComboBox<>();
        carregarClientes();
        add(cmbRemetente, gbc);

        // Botão de buscar fretes
        gbc.gridy++;
        btnBuscar = new JButton("Buscar Fretes");
        add(btnBuscar, gbc);

        // Seleção de Frete
        gbc.gridy++;
        add(new JLabel("Selecione o Frete:"), gbc);
        gbc.gridy++;
        cmbFretes = new JComboBox<>();
        add(cmbFretes, gbc);

        // Configuração dos campos de entrada
        gbc.gridwidth = 1;
        gbc.gridy++;
        add(new JLabel("Data do Frete:"), gbc);
        gbc.gridy++;
        txtDataFrete = new JFormattedTextField(new DecimalFormat("##/##/####"));
        add(txtDataFrete, gbc);

        gbc.gridy++;
        add(new JLabel("Peso (kg):"), gbc);
        gbc.gridy++;
        NumberFormat pesoFormat = DecimalFormat.getInstance(new Locale("pt", "BR"));
        pesoFormat.setMinimumFractionDigits(2);
        pesoFormat.setMaximumFractionDigits(2);
        NumberFormatter pesoFormatter = new NumberFormatter(pesoFormat);
        pesoFormatter.setAllowsInvalid(false);
        pesoFormatter.setOverwriteMode(true);
        txtPeso = new JFormattedTextField(pesoFormatter);
        add(txtPeso, gbc);

        gbc.gridy++;
        add(new JLabel("Valor (R$):"), gbc);
        gbc.gridy++;
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
        NumberFormatter currencyFormatter = new NumberFormatter(currencyFormat);
        currencyFormatter.setAllowsInvalid(false);
        currencyFormatter.setOverwriteMode(true);
        txtValor = new JFormattedTextField(currencyFormatter);
        add(txtValor, gbc);

        gbc.gridy++;
        add(new JLabel("ICMS (R$):"), gbc);
        gbc.gridy++;
        txtIcms = new JFormattedTextField(currencyFormatter);
        add(txtIcms, gbc);

        gbc.gridy++;
        add(new JLabel("Pedágio (R$):"), gbc);
        gbc.gridy++;
        txtPedagio = new JFormattedTextField(currencyFormatter);
        add(txtPedagio, gbc);

        // Campos de seleção de cidade de origem e destino
        gbc.gridy++;
        add(new JLabel("Cidade de Origem:"), gbc);
        gbc.gridy++;
        cmbOrigem = new JComboBox<>();
        carregarCidades();
        add(cmbOrigem, gbc);

        gbc.gridy++;
        add(new JLabel("Cidade de Destino:"), gbc);
        gbc.gridy++;
        cmbDestino = new JComboBox<>();
        add(cmbDestino, gbc);

        gbc.gridy++;
        add(new JLabel("Destinatário:"), gbc);
        gbc.gridy++;
        cmbDestinatario = new JComboBox<>();
        carregarClientes();
        add(cmbDestinatario, gbc);

        gbc.gridy++;
        btnAtualizar = new JButton("Atualizar Frete");
        add(btnAtualizar, gbc);

        // Eventos
        btnBuscar.addActionListener(e -> buscarFretes());
        cmbFretes.addActionListener(e -> preencherDadosFrete());
        btnAtualizar.addActionListener(e -> atualizarFrete());

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void carregarClientes() {
        try {
            List<Cliente> clientes = clienteService.listarTodos();
            cmbRemetente.removeAllItems();
            cmbDestinatario.removeAllItems();
            for (Cliente cliente : clientes) {
                cmbRemetente.addItem(cliente);
                cmbDestinatario.addItem(cliente);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar clientes: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void carregarCidades() {
        try {
            List<Cidade> cidades = cidadeService.listarTodas();
            cmbOrigem.removeAllItems();
            cmbDestino.removeAllItems();
            for (Cidade cidade : cidades) {
                cmbOrigem.addItem(cidade);
                cmbDestino.addItem(cidade);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar cidades: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void buscarFretes() {
        try {
            Cliente remetente = (Cliente) cmbRemetente.getSelectedItem();
            if (remetente == null) {
                JOptionPane.showMessageDialog(this, "Selecione um remetente.", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            List<Frete> fretes = freteService.buscarFretesPorRemetente(remetente);
            cmbFretes.removeAllItems();

            if (fretes.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Nenhum frete encontrado para o remetente selecionado.", "Informação", JOptionPane.INFORMATION_MESSAGE);
            } else {
                for (Frete frete : fretes) {
                    cmbFretes.addItem(frete);
                }
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao buscar fretes: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void preencherDadosFrete() {
        Frete frete = (Frete) cmbFretes.getSelectedItem();
        if (frete != null) {
            txtDataFrete.setText(frete.getDataFrete().format(dateFormatter));
            txtPeso.setValue(frete.getPeso());
            txtValor.setValue(frete.getValor());
            txtIcms.setValue(frete.getIcms());
            txtPedagio.setValue(frete.getPedagio());
            cmbOrigem.setSelectedItem(frete.getOrigem());
            cmbDestino.setSelectedItem(frete.getDestino());
            cmbRemetente.setSelectedItem(frete.getRemetente());
            cmbDestinatario.setSelectedItem(frete.getDestinatario());
        }
    }

    private void atualizarFrete() {
        try {
            Frete frete = (Frete) cmbFretes.getSelectedItem();
            if (frete == null) {
                JOptionPane.showMessageDialog(this, "Selecione um frete para atualizar.", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            LocalDate dataFrete = LocalDate.parse(txtDataFrete.getText(), dateFormatter);
            double peso = ((Number) txtPeso.getValue()).doubleValue();
            double valor = ((Number) txtValor.getValue()).doubleValue();
            double icms = ((Number) txtIcms.getValue()).doubleValue();
            double pedagio = ((Number) txtPedagio.getValue()).doubleValue();
            Cidade origem = (Cidade) cmbOrigem.getSelectedItem();
            Cidade destino = (Cidade) cmbDestino.getSelectedItem();
            Cliente remetente = (Cliente) cmbRemetente.getSelectedItem();
            Cliente destinatario = (Cliente) cmbDestinatario.getSelectedItem();

            frete.setDataFrete(dataFrete);
            frete.setPeso(peso);
            frete.setValor(valor);
            frete.setIcms(icms);
            frete.setPedagio(pedagio);
            frete.setOrigem(origem);
            frete.setDestino(destino);
            frete.setRemetente(remetente);
            frete.setDestinatario(destinatario);

            freteService.atualizarFrete(frete.getNumConhec(), frete);
            JOptionPane.showMessageDialog(this, "Frete atualizado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao atualizar frete: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}