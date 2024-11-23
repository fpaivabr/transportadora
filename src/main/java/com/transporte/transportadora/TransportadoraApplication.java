package com.transporte.transportadora;

import com.transporte.transportadora.ui.MainUI;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class TransportadoraApplication {

	public static void main(String[] args) {
		System.setProperty("java.awt.headless", "false");

		ApplicationContext context = SpringApplication.run(TransportadoraApplication.class, args);

		javax.swing.SwingUtilities.invokeLater(() -> context.getBean(MainUI.class).setVisible(true));
	}
}