package com.transporte.transportadora.ui.freteui;

import com.transporte.transportadora.model.Cidade;
import com.transporte.transportadora.model.Cliente;
import com.transporte.transportadora.model.Frete;
import com.transporte.transportadora.model.TipoCliente;
import com.transporte.transportadora.repository.ClienteRepository;
import com.transporte.transportadora.repository.FreteRepository;
import com.transporte.transportadora.service.CidadeService;
import com.transporte.transportadora.service.ClienteService;
import com.transporte.transportadora.service.FreteService;
import com.transporte.transportadora.ui.MainUI;
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

    private final FreteRepository freteRepository;
    private final ClienteRepository clienteRepository;
    private JComboBox<String> cmbRemetente;
    private JComboBox<String> cmbDestinatario;
    private JComboBox<String> cmbOrigem;
    private JComboBox<String> cmbDestino;
    private JComboBox<String> cmbFretes;
    private JFormattedTextField txtDataFrete;
    private JFormattedTextField txtPeso;
    private JFormattedTextField txtValor;
    private JFormattedTextField txtIcms;
    private JFormattedTextField txtPedagio;
    private JButton btnAtualizar;
    private JButton btnVoltar;

    private final FreteService freteService;
    private final CidadeService cidadeService;
    private final ClienteService clienteService;
    private final MainUI mainUI;
    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public FreteUpdateUI(ClienteService clienteService, CidadeService cidadeService, FreteService freteService, FreteRepository freteRepository, MainUI mainUI, ClienteRepository clienteRepository) {
        this.freteService = freteService;
        this.cidadeService = cidadeService;
        this.clienteService = clienteService;
        this.mainUI = mainUI;
        initUI();
        this.freteRepository = freteRepository;
        this.clienteRepository = clienteRepository;
    }

    private void initUI() {
        setTitle("Atualizar Frete");
        setSize(600, 800);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;

        // Seleção de Frete
        add(new JLabel("Selecione o Frete:"), gbc);
        gbc.gridx = 1;
        cmbFretes = new JComboBox<>();
        carregarFretes();
        add(cmbFretes, gbc);

        // Campos de atualização
        gbc.gridx = 0;
        gbc.gridy++;
        add(new JLabel("Data do Frete:"), gbc);
        gbc.gridx = 1;
        txtDataFrete = new JFormattedTextField(new DecimalFormat("##/##/####"));
        add(txtDataFrete, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        add(new JLabel("Peso (kg):"), gbc);
        gbc.gridx = 1;
        NumberFormat pesoFormat = DecimalFormat.getInstance(new Locale("pt", "BR"));
        pesoFormat.setMinimumFractionDigits(2);
        pesoFormat.setMaximumFractionDigits(2);
        NumberFormatter pesoFormatter = new NumberFormatter(pesoFormat);
        pesoFormatter.setAllowsInvalid(false);
        txtPeso = new JFormattedTextField(pesoFormatter);
        add(txtPeso, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        add(new JLabel("Valor (R$):"), gbc);
        gbc.gridx = 1;
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
        NumberFormatter currencyFormatter = new NumberFormatter(currencyFormat);
        currencyFormatter.setAllowsInvalid(false);
        txtValor = new JFormattedTextField(currencyFormatter);
        add(txtValor, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        add(new JLabel("ICMS (R$):"), gbc);
        gbc.gridx = 1;
        txtIcms = new JFormattedTextField(currencyFormatter);
        add(txtIcms, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        add(new JLabel("Pedágio (R$):"), gbc);
        gbc.gridx = 1;
        txtPedagio = new JFormattedTextField(currencyFormatter);
        add(txtPedagio, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        add(new JLabel("Cidade de Origem:"), gbc);
        gbc.gridx = 1;
        cmbOrigem = new JComboBox<>();
        carregarCidades();
        add(cmbOrigem, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        add(new JLabel("Cidade de Destino:"), gbc);
        gbc.gridx = 1;
        cmbDestino = new JComboBox<>();
        carregarCidades();
        add(cmbDestino, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        add(new JLabel("Cliente Remetente:"), gbc);
        gbc.gridx = 1;
        cmbRemetente = new JComboBox<>();
        carregarClientes();
        add(cmbRemetente, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        add(new JLabel("Cliente Destinatário:"), gbc);
        gbc.gridx = 1;
        cmbDestinatario = new JComboBox<>();
        carregarClientes();
        add(cmbDestinatario, gbc);

        // Botões
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        btnAtualizar = new JButton("Atualizar");
        add(btnAtualizar, gbc);
        btnAtualizar.addActionListener(e -> atualizarFrete());

        gbc.gridy++;
        btnVoltar = new JButton("Voltar");
        add(btnVoltar, gbc);
        btnVoltar.addActionListener(e -> voltarParaMainUI());

        cmbFretes.addActionListener(e -> preencherDadosFrete());

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void carregarFretes() {
        try {
            cmbFretes = new JComboBox<>();
            List<Cliente> clientes = clienteService.listarTodos();
            cmbRemetente.removeAllItems();
            cmbDestinatario.removeAllItems();
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
            JOptionPane.showMessageDialog(this, "Erro ao carregar fretes: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void carregarCidades() {
        try {
            cmbOrigem = new JComboBox<>();
            cmbDestinatario = new JComboBox<>();
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
            cmbOrigem = new JComboBox<>();
            cmbDestino = new JComboBox<>();
            cmbRemetente = new JComboBox<>();
            cmbDestinatario = new JComboBox<>();
            List<Cliente> clientes = clienteService.listarTodos();
            for (Cliente cliente : clientes) {
                if(cliente.getTipoCliente().equals(TipoCliente.PESSOA_FISICA)){
                    cmbOrigem.addItem(cliente.getPessoaFisica().getCpf());
                    cmbDestino.addItem(cliente.getPessoaFisica().getCpf());
                }else{
                    cmbRemetente.addItem(cliente.getPessoaJuridica().getCnpj());
                    cmbDestinatario.addItem(cliente.getPessoaJuridica().getCnpj());
                }
            }

            List<Frete> fretes = freteService.buscarFretesPorRemetente(clienteRepository.findByDocumento(cmbRemetente.getSelectedItem().toString()).orElse(null));
            cmbFretes.removeAllItems();

            if (fretes.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Nenhum frete encontrado para o remetente selecionado.", "Informação", JOptionPane.INFORMATION_MESSAGE);
            } else {
                for (Frete frete : fretes) {
                    cmbFretes.addItem(frete.getNumConhec().toString());
                }
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar clientes: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void preencherDadosFrete() {
        Frete frete = freteRepository.findByNumConhec((cmbFretes.getSelectedItem().toString())).orElse(null);
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
            Frete frete = freteRepository.findByNumConhec((cmbFretes.getSelectedItem().toString())).orElse(null);
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

    private void voltarParaMainUI() {
        mainUI.setVisible(true);
        dispose();
    }
}
