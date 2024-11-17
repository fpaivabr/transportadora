package com.transporte.transportadora;

import com.transporte.transportadora.ui.MainUI;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TransportadoraApplication {

	public static void main(String[] args) {
		System.setProperty("java.awt.headless", "false");

		SpringApplication.run(TransportadoraApplication.class, args);

		javax.swing.SwingUtilities.invokeLater(MainUI::new);
	}
}
