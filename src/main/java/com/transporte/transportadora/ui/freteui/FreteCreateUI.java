package com.transporte.transportadora.ui.freteui;

import com.transporte.transportadora.model.Cidade;
import com.transporte.transportadora.model.Cliente;
import com.transporte.transportadora.model.Frete;
import com.transporte.transportadora.model.TipoCliente;
import com.transporte.transportadora.repository.CidadeRepository;
import com.transporte.transportadora.repository.ClienteRepository;
import com.transporte.transportadora.service.CidadeService;
import com.transporte.transportadora.service.ClienteService;
import com.transporte.transportadora.service.FreteService;
import com.transporte.transportadora.ui.MainUI;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import javax.swing.*;
import javax.swing.text.MaskFormatter;
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
public class FreteCreateUI extends JFrame {

    private final ClienteRepository clienteRepository;
    private final CidadeRepository cidadeRepository;
    private JFormattedTextField txtDataFrete;
    private JFormattedTextField txtPeso;
    private JFormattedTextField txtValor;
    private JFormattedTextField txtIcms;
    private JFormattedTextField txtPedagio;
    private JComboBox<String> cmbOrigem;
    private JComboBox<String> cmbDestino;
    private JComboBox<String>   cmbRemetente;
    private JComboBox<String> cmbDestinatario;
    private JButton btnSalvar;
    private JButton btnVoltar;

    private final FreteService freteService;
    private final CidadeService cidadeService;
    private final ClienteService clienteService;
    private final MainUI mainUI;
    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public FreteCreateUI(ClienteService clienteService, CidadeService cidadeService, final FreteService freteService, ClienteRepository clienteRepository, CidadeRepository cidadeRepository, MainUI mainUI) {
        this.freteService = freteService;
        this.cidadeService = cidadeService;
        this.clienteService = clienteService;
        this.mainUI = mainUI;
        initUI();
        this.clienteRepository = clienteRepository;
        this.cidadeRepository = cidadeRepository;
    }

    private void initUI() {
        setTitle("Cadastro de Frete");
        setSize(500, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;

        try {
            // Campo Data do Frete
            MaskFormatter dataFormatter = new MaskFormatter("##/##/####");
            dataFormatter.setPlaceholderCharacter('_');
            txtDataFrete = new JFormattedTextField(dataFormatter);
            add(new JLabel("Data do Frete:"), gbc);
            gbc.gridx = 1;
            add(txtDataFrete, gbc);

            // Campo Peso
            gbc.gridx = 0;
            gbc.gridy++;
            add(new JLabel("Peso (kg):"), gbc);
            gbc.gridx = 1;
            NumberFormat pesoFormat = DecimalFormat.getInstance(new Locale("pt", "BR"));
            pesoFormat.setMinimumFractionDigits(2);
            pesoFormat.setMaximumFractionDigits(2);
            NumberFormatter pesoFormatter = new NumberFormatter(pesoFormat);
            pesoFormatter.setAllowsInvalid(false);
            pesoFormatter.setOverwriteMode(true);
            txtPeso = new JFormattedTextField(pesoFormatter);
            add(txtPeso, gbc);

            // Campo Valor
            gbc.gridx = 0;
            gbc.gridy++;
            add(new JLabel("Valor (R$):"), gbc);
            gbc.gridx = 1;
            NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
            NumberFormatter currencyFormatter = new NumberFormatter(currencyFormat);
            currencyFormatter.setAllowsInvalid(false);
            currencyFormatter.setOverwriteMode(true);
            txtValor = new JFormattedTextField(currencyFormatter);
            add(txtValor, gbc);

            // Campo ICMS
            gbc.gridx = 0;
            gbc.gridy++;
            add(new JLabel("ICMS (R$):"), gbc);
            gbc.gridx = 1;
            txtIcms = new JFormattedTextField(currencyFormatter);
            add(txtIcms, gbc);

            // Campo Pedágio
            gbc.gridx = 0;
            gbc.gridy++;
            add(new JLabel("Pedágio (R$):"), gbc);
            gbc.gridx = 1;
            txtPedagio = new JFormattedTextField(currencyFormatter);
            add(txtPedagio, gbc);

            // Campo Cidade de Origem
            gbc.gridx = 0;
            gbc.gridy++;
            add(new JLabel("Cidade de Origem:"), gbc);
            gbc.gridx = 1;
            cmbOrigem = new JComboBox<>();
            carregarCidades();
            add(cmbOrigem, gbc);

            // Campo Cidade de Destino
            gbc.gridx = 0;
            gbc.gridy++;
            add(new JLabel("Cidade de Destino:"), gbc);
            gbc.gridx = 1;
            cmbDestino = new JComboBox<>();
            carregarCidades();
            add(cmbDestino, gbc);

            // Campo Cliente Remetente
            gbc.gridx = 0;
            gbc.gridy++;
            add(new JLabel("Cliente Remetente:"), gbc);
            gbc.gridx = 1;
            cmbRemetente = new JComboBox<>();
            carregarClientes();
            add(cmbRemetente, gbc);

            // Campo Cliente Destinatário
            gbc.gridx = 0;
            gbc.gridy++;
            add(new JLabel("Cliente Destinatário:"), gbc);
            gbc.gridx = 1;
            cmbDestinatario = new JComboBox<>();
            carregarClientes();
            add(cmbDestinatario, gbc);

            // Botão Salvar
            gbc.gridx = 0;
            gbc.gridy++;
            gbc.gridwidth = 2;
            btnSalvar = new JButton("Salvar");
            add(btnSalvar, gbc);

            btnSalvar.addActionListener(e -> salvarFrete());

            // Botão Voltar
            gbc.gridy++;
            btnVoltar = new JButton("Voltar");
            add(btnVoltar, gbc);

            btnVoltar.addActionListener(e -> voltarParaMainUI());

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao configurar a formatação: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void carregarCidades() {
        try {
            List<Cidade> cidades = cidadeService.listarTodas();
            for (Cidade cidade : cidades) {
                cmbOrigem.addItem(cidade.getNome());
                cmbDestino.addItem(cidade.getNome());
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar cidades: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void carregarClientes() {
        try {
            List<Cliente> clientes = clienteService.listarTodos();
            for (Cliente cliente : clientes) {
                if(cliente.getTipoCliente().equals(TipoCliente.PESSOA_FISICA)){
                    cmbRemetente.addItem(cliente.getPessoaFisica().getCpf());
                    cmbDestinatario.addItem(cliente.getPessoaFisica().getCpf());
                }else{
                    cmbRemetente.addItem(cliente.getPessoaJuridica().getCnpj());
                    cmbDestinatario.addItem(cliente.getPessoaJuridica().getCnpj());
                }
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar clientes: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void salvarFrete() {
        try {
            LocalDate dataFrete = LocalDate.parse(txtDataFrete.getText(), dateFormatter);
            double peso = ((Number) txtPeso.getValue()).doubleValue();
            double valor = ((Number) txtValor.getValue()).doubleValue();
            double icms = ((Number) txtIcms.getValue()).doubleValue();
            double pedagio = ((Number) txtPedagio.getValue()).doubleValue();

            Cidade origem = cidadeRepository.findByNome(cmbOrigem.getSelectedItem().toString()).orElse(null);
            Cidade destino = cidadeRepository.findByNome(cmbDestino.getSelectedItem().toString()).orElse(null);
            Cliente remetente = clienteRepository.findByDocumento(cmbRemetente.getSelectedItem().toString()).orElse(null);
            Cliente destinatario = clienteRepository.findByDocumento(cmbDestinatario.getSelectedItem().toString()).orElse(null);

            if(origem == null || destino == null || remetente == null || destinatario == null){
                JOptionPane.showMessageDialog(this, "Erro ao salvar o frete, informacoes nao correspondem com a do banco de dados ");
                return;
            }
            Frete frete = new Frete();
            frete.setDataFrete(dataFrete);
            frete.setPeso(peso);
            frete.setValor(valor);
            frete.setIcms(icms);
            frete.setPedagio(pedagio);
            frete.setOrigem(origem);
            frete.setDestino(destino);
            frete.setRemetente(remetente);
            frete.setDestinatario(destinatario);

            freteService.salvarFrete(frete);
            JOptionPane.showMessageDialog(this, "Frete cadastrado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar o frete: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void voltarParaMainUI() {
        mainUI.setVisible(true); // Reexibe a MainUI
        dispose(); // Fecha a tela atual
    }
}
