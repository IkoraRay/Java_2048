/*
 
 	Joao Guilherme Lustosa Andrade - 16284085
 	Luis Marcelo Stein d'Avila - 18125955
 
 */

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Inicio {
	
	public static int opcao;

	public static void main(String[] args) {

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
	
	public static void perguntaXastre() {
		JFrame easter = new JFrame("EASTER EGG");
		easter.setSize(300, 300);
		easter.setLayout(null);
		easter.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		easter.setLocationRelativeTo(null);
		easter.setVisible(false); // seta JFrame como invisivel para mostrar apenas o botao
		opcao = JOptionPane.showConfirmDialog(easter, "O GRANDIOSO XASTRE GOSTARIA DE TER A VIDA FACILITADA?"); // cria JButton para decidir se usara easter egg
	}
	
}
