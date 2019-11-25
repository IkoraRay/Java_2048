import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

public class Jogo extends JPanel implements KeyListener, Runnable {

	public static final int LARGURA = 400;
	public static final int ALTURA = 450;
	public static final Font main = new Font("Arial Rounded MT Bold", Font.PLAIN, 28); // troca a fonte para Arial tamanho 28
	private Thread jogo;
	private boolean rodando;
	private BufferedImage imagem = new BufferedImage(LARGURA, ALTURA, BufferedImage.TYPE_INT_RGB); // imagem que sera desenhada no JPanel
	
	private Interface interfaceJogo;

	public Jogo() {

		setFocusable(true); // permite entrada do teclado
		setPreferredSize(new Dimension(LARGURA, ALTURA));
		addKeyListener(this);
		
		interfaceJogo = new Interface(LARGURA / 2 - Interface.LARGURA_INTERFACE / 2, ALTURA - Interface.ALTURA_INTERFACE - 10);

	}

	private void atualiza() {
		
		interfaceJogo.atualiza();
		Teclado.atualiza();
	}

	private void processa() {

		Graphics2D graficos = (Graphics2D) imagem.getGraphics();
		graficos.setColor(Color.white);
		graficos.fillRect(0, 0, LARGURA, ALTURA); // desenha um retangulo na tela
		interfaceJogo.processa(graficos);
		graficos.dispose(); // destroi e limpa o JFrame

		Graphics2D graficos2d = (Graphics2D) getGraphics(); // graficos do JPanel, onde tera a imagem
		graficos2d.drawImage(imagem, 0, 0, null);
		graficos2d.dispose();

	}

	@Override
	public void run() {

		int fps = 0, atualizacao = 0;
		long tempoFps = System.currentTimeMillis();
		double nanoSegundosPorAtualizacao = 1000000000.0 / 60;

		// ultima atualizacao em nanosegundos
		double seguido = System.nanoTime(); // tempo atual
		double naoProcessado = 0; // verifica quantas atualizacoes sao necessarias caso haja problema no retorno

		while (rodando) {
			
			boolean processa = false;
			double atual = System.nanoTime();
			naoProcessado += (atual - seguido) / nanoSegundosPorAtualizacao; // conta o numero de atualizacoes que precisam ser feitas baseado no tempo decorrido
			seguido = atual; // reseta o tempo
			
			// atualiza fila
			while (naoProcessado >= 1) { // enquanto houver algo para ser processado

				atualizacao++;
				atualiza();
				naoProcessado--;
				processa = true; // so processa se estiver atualizado
			}

			if (processa) {
				fps++;
				processa();
				processa = false;
			} else {
				try {
					Thread.sleep(1);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

		// tempo FPS
		if (System.currentTimeMillis() - tempoFps > 1000) { // se for maior que 1 segundo
			System.out.printf("%d fpd %d atualizacoes", fps, atualizacao);
			System.out.println();
			fps = 0; // reseta fps
			atualizacao = 0; // reseta numero de atualizacoes
			tempoFps += 1000; // reseta fps
		}

	}

	public synchronized void inicia() {
		if(rodando) return;
		rodando = true;
		jogo = new Thread(this, "jogo");
		jogo.start(); //inicia a execucao da thread
	}
	
	public synchronized void para() {
		if(!rodando) return;
		rodando = false;
		System.exit(0);
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		Teclado.KeyPressed(e);
	}

	@Override
	public void keyReleased(KeyEvent e) {
		Teclado.KeyReleased(e);
	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

}
