import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Interface extends Inicio {

	public static final int LINHAS = 4;
	public static final int COLUNAS = 4;
	private final int numBlocosInicio = 2;
	public static boolean novoJogo = false;
	public static int valor;

	private Bloco[][] interfaceJogo;
	private boolean vitoria;
	private BufferedImage interfaceFundo;
	private BufferedImage interfaceFinal;
	private int x;
	private int y;
	private int pontuacao = 0;
	private int recorde = 0;
	private Font fontePontuacao;

	private static int ESPACAMENTO = 10;
	public static int LARGURA_INTERFACE = (COLUNAS + 1) * ESPACAMENTO + COLUNAS * Bloco.LARGURA;
	public static int ALTURA_INTERFACE = (LINHAS + 1) * ESPACAMENTO + LINHAS * Bloco.ALTURA;

	private boolean iniciado;

	private String localSave;
	private String nomeArquivo = "Saves";

	public Interface(int x, int y) {

		try {
			localSave = Interface.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath(); // salva
																												// onde
																												// java
																												// esta
																												// rodando
		} catch (Exception e) {
			e.printStackTrace();
		}

		fontePontuacao = Jogo.main.deriveFont(24f);
		this.x = x;
		this.y = y;
		interfaceJogo = new Bloco[LINHAS][COLUNAS];
		interfaceFundo = new BufferedImage(LARGURA_INTERFACE, ALTURA_INTERFACE, BufferedImage.TYPE_INT_RGB);
		interfaceFinal = new BufferedImage(LARGURA_INTERFACE, ALTURA_INTERFACE, BufferedImage.TYPE_INT_RGB);

		carregaRecorde();
		criaImagemInterface();
		inicia();

	}

	private void salvaDados() {
		try {
			File arquivo = new File(localSave, nomeArquivo);

			FileWriter saida = new FileWriter(arquivo);
			BufferedWriter escrita = new BufferedWriter(saida);
			escrita.write("" + 0);
			escrita.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void carregaRecorde() {
		try {
			File arq = new File(localSave, nomeArquivo);
			if (!arq.isFile())
				salvaDados();

			BufferedReader leitura = new BufferedReader(new InputStreamReader(new FileInputStream(arq)));
			recorde = Integer.parseInt(leitura.readLine());
			leitura.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void setRecorde() {
		FileWriter saida = null;

		try {
			File arq = new File(localSave, nomeArquivo);
			saida = new FileWriter(arq);
			BufferedWriter escrita = new BufferedWriter(saida);

			escrita.write("" + recorde);

			escrita.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void criaImagemInterface() {

		Graphics2D graficos = (Graphics2D) interfaceFundo.getGraphics();
		graficos.setColor(Color.lightGray);
		graficos.fillRect(0, 0, LARGURA_INTERFACE, ALTURA_INTERFACE);
		graficos.setColor(Color.white);

		for (int linha = 0; linha < LINHAS; linha++) {

			for (int coluna = 0; coluna < COLUNAS; coluna++) {

				int x = ESPACAMENTO + ESPACAMENTO * coluna + Bloco.LARGURA * coluna;
				int y = ESPACAMENTO + ESPACAMENTO * linha + Bloco.ALTURA * linha;
				graficos.fillRoundRect(x, y, Bloco.LARGURA, Bloco.ALTURA, Bloco.ARC_LARGURA, Bloco.ARC_ALTURA);

			}
		}
	}

	private void inicia() {
		for (int i = 0; i < numBlocosInicio; i++) {
			spawnaAleatorio();
		}
	}

	private void spawnaAleatorio() {
		Random random = new Random();
		boolean invalido = true; // considera que os primeiros nao sao validos

		while (invalido) {
			int localizacao = random.nextInt(LINHAS * COLUNAS);
			int linha = localizacao / LINHAS;
			int coluna = localizacao % COLUNAS;

			Bloco atual = interfaceJogo[linha][coluna];
			if (atual == null) {
				Inicio i = new Inicio();
				
				if (i.opcao == JOptionPane.NO_OPTION) {
				this.valor = random.nextInt(10) < 9 ? 2 : 4;
				// pega um valor aleatorio de 0 a 9, se nao for 9, spawna 2, se for 9, spawna 4
				}
				else if(opcao == JOptionPane.YES_OPTION){
					this.valor = random.nextInt(10) < 9 ? 512 : 1024;
				}
				else {
					System.exit(0);
				}
				Bloco bloco = new Bloco(valor, buscaX(coluna), buscaY(linha));
				interfaceJogo[linha][coluna] = bloco;
				invalido = false;
			}
		}
	}

	public int buscaX(int coluna) {
		return ESPACAMENTO + coluna * Bloco.LARGURA + coluna * ESPACAMENTO;
	}

	public int buscaY(int linha) {
		return ESPACAMENTO + linha * Bloco.ALTURA + linha * ESPACAMENTO;
	}

	public void processa(Graphics2D graficos) {

		Graphics2D graficos2d = (Graphics2D) interfaceFinal.getGraphics();
		graficos2d.drawImage(interfaceFundo, 0, 0, null);

		for (int linha = 0; linha < LINHAS; linha++) {
			for (int coluna = 0; coluna < COLUNAS; coluna++) {

				Bloco atual = interfaceJogo[linha][coluna];
				if (atual == null)
					continue;
				atual.processa(graficos2d);

			}
		}

		graficos.drawImage(interfaceFinal, x, y, null);
		graficos2d.dispose();

		graficos.setColor(Color.darkGray);
		graficos.setFont(fontePontuacao);
		graficos.drawString("" + pontuacao, 30, 40);
		graficos.setColor(Color.red);
		graficos.drawString("Recorde: " + recorde, Jogo.LARGURA
				- DesenhosAuxiliares.recebeLarguraMensagem("Recorde: " + recorde, fontePontuacao, graficos) - 20, 40);

	}

	public void atualiza() {
		if(vitoria == true) {
			setRecorde();
			opcoesVit();
		}
		verificaDerrota();
		checaTeclas();

		if (pontuacao > recorde)
			recorde = pontuacao;

		for (int linha = 0; linha < LINHAS; linha++) {
			for (int coluna = 0; coluna < COLUNAS; coluna++) {

				Bloco atual = interfaceJogo[linha][coluna];
				if (atual == null)
					continue;
				atual.atualiza();

				resetaPosicao(atual, linha, coluna);

				if (atual.buscaValor() == 2048) {
					vitoria = true;
				}
			}
		}
	}

	private void resetaPosicao(Bloco atual, int linha, int coluna) {
		if (atual == null)
			return;

		int x = buscaX(coluna);
		int y = buscaY(linha);

		int distX = atual.getX() - x;
		int distY = atual.getY() - y;

		if (Math.abs(distX) < Bloco.VEL_DESLIZE) {
			atual.setX(atual.getX() - distX);
		}
		if (Math.abs(distY) < Bloco.VEL_DESLIZE) {
			atual.setY(atual.getY() - distY);
		}

		if (distX < 0) {
			atual.setX(atual.getX() + Bloco.VEL_DESLIZE);
		}
		if (distY < 0) {
			atual.setY(atual.getY() + Bloco.VEL_DESLIZE);
		}
		if (distX > 0) {
			atual.setX(atual.getX() - Bloco.VEL_DESLIZE);
		}
		if (distY > 0) {
			atual.setY(atual.getY() - Bloco.VEL_DESLIZE);
		}

	}

	private boolean move(int linha, int coluna, int direcaoHorizontal, int direcaoVertical, Direcao direcao) {
		boolean podeMover = false;

		Bloco atual = interfaceJogo[linha][coluna];
		if (atual == null)
			return false; // nao da pra mover posicao vazia
		boolean mover = true;
		int novaLinha = linha;
		int novaColuna = coluna;

		while (mover) { // enquanto puderem combinar ou deslizar para um bloco vazio
			novaColuna += direcaoHorizontal;
			novaLinha += direcaoVertical;
			if (verificaLimites(direcao, novaLinha, novaColuna))
				break;
			if (interfaceJogo[novaLinha][novaColuna] == null) { // posso mover
				interfaceJogo[novaLinha][novaColuna] = atual; // move para vazio
				interfaceJogo[novaLinha - direcaoVertical][novaColuna - direcaoHorizontal] = null; // esvazia posicao
																									// anterior
				interfaceJogo[novaLinha][novaColuna].setDeslizaPara(new Ponto(novaLinha, novaColuna));
				podeMover = true;
			} else if (interfaceJogo[novaLinha][novaColuna].buscaValor() == atual.buscaValor()
					&& interfaceJogo[novaLinha][novaColuna].podeUnir()) {
				interfaceJogo[novaLinha][novaColuna].setPodeUnir(false);
				interfaceJogo[novaLinha][novaColuna].setValor(interfaceJogo[novaLinha][novaColuna].buscaValor() * 2); // multiplica
																														// valor
																														// por
																														// 2
				podeMover = true;
				interfaceJogo[novaLinha - direcaoVertical][novaColuna - direcaoHorizontal] = null;
				interfaceJogo[novaLinha][novaColuna].setDeslizaPara(new Ponto(novaLinha, novaColuna));
				pontuacao += interfaceJogo[novaLinha][novaColuna].buscaValor();
			} else {
				mover = false;
			}
		}

		return podeMover;
	}

	private boolean verificaLimites(Direcao direcao, int linha, int coluna) {
		if (direcao == Direcao.ESQUERDA) {
			return coluna < 0;
		} else if (direcao == Direcao.DIREITA) {
			return coluna > COLUNAS - 1;
		} else if (direcao == Direcao.CIMA) {
			return linha < 0;
		} else if (direcao == Direcao.BAIXO) {
			return linha > LINHAS - 1;
		}
		return false;
	}

	private void moveBlocos(Direcao direcao) {

		boolean podeMover = false;
		int direcaoHorizontal = 0;
		int direcaoVertical = 0;

		if (direcao == Direcao.ESQUERDA) {
			direcaoHorizontal = -1;
			for (int linha = 0; linha < LINHAS; linha++) {
				for (int coluna = 0; coluna < COLUNAS; coluna++) {
					if (!podeMover) {
						podeMover = move(linha, coluna, direcaoHorizontal, direcaoVertical, direcao);
					} else
						move(linha, coluna, direcaoHorizontal, direcaoVertical, direcao);
				}
			}
		} else if (direcao == Direcao.DIREITA) {
			direcaoHorizontal = 1;
			for (int linha = 0; linha < LINHAS; linha++) {
				for (int coluna = COLUNAS - 1; coluna >= 0; coluna--) {
					if (!podeMover) {
						podeMover = move(linha, coluna, direcaoHorizontal, direcaoVertical, direcao);
					} else
						move(linha, coluna, direcaoHorizontal, direcaoVertical, direcao);
				}
			}
		} else if (direcao == Direcao.CIMA) {
			direcaoVertical = -1;
			for (int linha = 0; linha < LINHAS; linha++) {
				for (int coluna = 0; coluna < COLUNAS; coluna++) {
					if (!podeMover) {
						podeMover = move(linha, coluna, direcaoHorizontal, direcaoVertical, direcao);
					} else
						move(linha, coluna, direcaoHorizontal, direcaoVertical, direcao);
				}
			}
		} else if (direcao == Direcao.BAIXO) {
			direcaoVertical = 1;
			for (int linha = LINHAS - 1; linha >= 0; linha--) {
				for (int coluna = 0; coluna < COLUNAS; coluna++) {
					if (!podeMover) {
						podeMover = move(linha, coluna, direcaoHorizontal, direcaoVertical, direcao);
					} else
						move(linha, coluna, direcaoHorizontal, direcaoVertical, direcao);
				}
			}
		} else {

			System.out.println(direcao + "nao eh uma entrada valida!");

		}

		for (int linha = 0; linha < LINHAS; linha++) {
			for (int coluna = 0; coluna < COLUNAS; coluna++) {
				Bloco atual = interfaceJogo[linha][coluna];
				if (atual == null)
					continue;
				atual.setPodeUnir(true);
			}
		}

		if (podeMover) {

			spawnaAleatorio();
		}
	}

	private void opcoesVit() {

		JFrame vitoria = new JFrame("VITORIA");
		vitoria.setSize(300, 300);
		vitoria.setLayout(null);
		vitoria.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		vitoria.setLocationRelativeTo(null);
		vitoria.setVisible(false);
		int opcao = JOptionPane.showConfirmDialog(vitoria, "PARABENS! Voce venceu... Deseja jogar novamente?");
		if (opcao == JOptionPane.YES_OPTION) novoJogo();
		else System.exit(0);

	}

	private void verificaDerrota() {
		for (int linha = 0; linha < LINHAS; linha++) {
			for (int coluna = 0; coluna < COLUNAS; coluna++) {
				if (interfaceJogo[linha][coluna] == null)
					return; // existe posicao vazia, portanto nao perdeu
				if (verificaBlocosEmVolta(linha, coluna, interfaceJogo[linha][coluna])) {
					return;
				}
			}
		}

		opcoes();
		setRecorde();
	}

	private void opcoes() {

		JFrame derrota = new JFrame("DERROTA");
		derrota.setSize(300, 300);
		derrota.setLayout(null);
		derrota.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		derrota.setLocationRelativeTo(null);
		derrota.setVisible(false);
		int opcao = JOptionPane.showConfirmDialog(derrota, "Voce Perdeu! Deseja jogar novamente?");
		if (opcao == JOptionPane.YES_OPTION) novoJogo(); 
		else if (opcao == JOptionPane.NO_OPTION || opcao == JOptionPane.CANCEL_OPTION ) System.exit(0);

	}
	
	private void novoJogo() {
		
		for (int i = 0; i < LINHAS; i++)
			for (int j = 0; j < COLUNAS; j++) {
				interfaceJogo[i][j] = null;
			}

		setRecorde();
		pontuacao = 0;
		criaImagemInterface();
		inicia();
		
	}

	private boolean verificaBlocosEmVolta(int linha, int coluna, Bloco atual) {
		if (linha > 0) {
			Bloco checar = interfaceJogo[linha - 1][coluna];
			if (checar == null)
				return true;
			if (atual.buscaValor() == checar.buscaValor())
				return true;
		}
		if (linha < LINHAS - 1) {
			Bloco checar = interfaceJogo[linha + 1][coluna];
			if (checar == null)
				return true;
			if (atual.buscaValor() == checar.buscaValor())
				return true;
		}
		if (coluna > 0) {
			Bloco checar = interfaceJogo[linha][coluna - 1];
			if (checar == null)
				return true;
			if (atual.buscaValor() == checar.buscaValor())
				return true;
		}
		if (coluna < COLUNAS - 1) {
			Bloco checar = interfaceJogo[linha][coluna + 1];
			if (checar == null)
				return true;
			if (atual.buscaValor() == checar.buscaValor())
				return true;
		}
		return false;
	}

	public void checaTeclas() {

		if (Teclado.teclado(KeyEvent.VK_LEFT) || Teclado.teclado(KeyEvent.VK_A)) {
			moveBlocos(Direcao.ESQUERDA);
			if (!iniciado)
				iniciado = true;
		}
		if (Teclado.teclado(KeyEvent.VK_RIGHT) || Teclado.teclado(KeyEvent.VK_D)) {

			moveBlocos(Direcao.DIREITA);
			if (!iniciado)
				iniciado = true;
		}
		if (Teclado.teclado(KeyEvent.VK_UP) || Teclado.teclado(KeyEvent.VK_W)) {

			moveBlocos(Direcao.CIMA);
			if (!iniciado)
				iniciado = true;
		}
		if (Teclado.teclado(KeyEvent.VK_DOWN) || Teclado.teclado(KeyEvent.VK_S)) {

			moveBlocos(Direcao.BAIXO);
			if (!iniciado)
				iniciado = true;
		}

	}

}
