package com.transporte.transportadora.ui.freteui;

import com.transporte.transportadora.model.Frete;
import com.transporte.transportadora.repository.FreteRepository;
import com.transporte.transportadora.service.FreteService;
import com.transporte.transportadora.service.impl.FreteServiceImpl;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;
import java.util.List;

@Lazy
@Component
public class FreteDeleteUI extends JFrame {

    private JComboBox<Frete> cmbFretes;
    private JButton btnDeletar;

    private final FreteService freteService;

    public FreteDeleteUI(FreteService freteService) {
        this.freteService = freteService;
        initUI();
    }
    private void initUI() {

        setTitle("Deletar Frete");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;

        add(new JLabel("Selecione o Frete para deletar:"), gbc);
        gbc.gridy++;
        cmbFretes = new JComboBox<>();
        carregarFretes();
        add(cmbFretes, gbc);

        gbc.gridy++;
        btnDeletar = new JButton("Deletar");
        add(btnDeletar, gbc);

        btnDeletar.addActionListener(e -> deletarFrete());

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void carregarFretes() {
        try {
            List<Frete> fretes = freteService.listarTodos();
            for (Frete frete : fretes) {
                cmbFretes.addItem(frete);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar fretes: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deletarFrete() {
        try {
            Frete frete = (Frete) cmbFretes.getSelectedItem();
            if (frete == null) {
                JOptionPane.showMessageDialog(this, "Selecione um frete para deletar.", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            freteService.deletarFrete(frete.getNumConhec());
            JOptionPane.showMessageDialog(this, "Frete deletado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);

            // Atualizar lista de fretes no JComboBox
            cmbFretes.removeAllItems();
            carregarFretes();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao deletar frete: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}
