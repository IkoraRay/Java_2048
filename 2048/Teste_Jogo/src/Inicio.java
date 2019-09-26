import javax.swing.JFrame;

public class Inicio {

	public static void main(String[] args) {
		
		Jogo jogo = new Jogo();
		JFrame janela = new JFrame("2048");
		janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		janela.setResizable(false); // impede redimensionamento da janela pelo jogador
		janela.add(jogo); //JPanel
		janela.pack(); //garante que o conteudo esteja dentro da janela
		janela.setLocationRelativeTo(null); //centraliza janela
		janela.setVisible(true);
		
		jogo.inicia();		
	}
	
}
