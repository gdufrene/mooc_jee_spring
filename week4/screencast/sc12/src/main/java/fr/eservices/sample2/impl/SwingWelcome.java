package fr.eservices.sample2.impl;

import javax.swing.JOptionPane;

import fr.eservices.sample2.api.Welcome;

public class SwingWelcome implements Welcome {

	@Override
	public String askName() {
		return JOptionPane.showInputDialog(null, "What is your name", "Welcome", JOptionPane.QUESTION_MESSAGE);
	}
}
