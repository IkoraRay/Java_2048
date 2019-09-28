import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Inicio {
	
	public static int opcao;

	public static void main(String[] args) {

		JFrame easter = new JFrame("EASTER EGG");
		easter.setSize(300, 300);
		easter.setLayout(null);
		easter.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		easter.setLocationRelativeTo(null);
		easter.setVisible(false);
		opcao = JOptionPane.showConfirmDialog(easter, "O GRANDIOSO XASTRE GOSTARIA DE TER A VIDA FACILITADA?");

		Jogo jogo = new Jogo();
		JFrame janela = new JFrame("Xastre Planet Number 2048");
		janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		janela.setResizable(false); // impede redimensionamento da janela pelo jogador
		janela.add(jogo); // JPanel
		janela.pack(); // garante que o conteudo esteja dentro da janela
		janela.setLocationRelativeTo(null); // centraliza janela
		janela.setVisible(true);
		jogo.inicia();

	}
}
