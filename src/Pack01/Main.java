package Pack01;

import java.awt.event.KeyEvent;

import javax.swing.JFrame;

public class Main {

	public static void main(String[] args) {

		Calculator cal = new Calculator();
		cal.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		cal.setTitle("Calculator");
		cal.setSize(400, 400);
		cal.setVisible(true);
	}

	public void keyReleased(KeyEvent arg0) {
	}

	public void keyTyped(KeyEvent arg0) {
	}

	public void Run() {
		Calculator cal = new Calculator();
		cal.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		cal.setTitle("Calculator");
		cal.setSize(400, 400);
		cal.setVisible(true);
	}
}