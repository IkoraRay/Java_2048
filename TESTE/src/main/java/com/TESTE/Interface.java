package com.TESTE;
import org.testng.annotations.BeforeClass;

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

    @BeforeClass
    public static void setupHeadlessMode() {
        System.setProperty("java.awt.headless", "false");
    }
    //define que jogo sera 4x4
    public static final int LINHAS = 4;
    public static final int COLUNAS = 4;
    //define o tamanho do loop inicial de spaens aleatorios (2 ou 4)
    private final int numBlocosInicio = 2;
    public static int valor;

    private static Bloco[][] posicao; //define matriz do jogo
    private boolean vitoria;
    private BufferedImage bordaJogo;
    private BufferedImage interfaceFinal;
    private int x;
    private int y;
    private static int pontuacao = 0;
    private int recorde = 0;
    private Font fontePontuacao;

    //define constanntes para o desenho do jogo
    private static int ESPACAMENTO = 10;
    public static int LARGURA_INTERFACE = (COLUNAS + 1) * ESPACAMENTO + COLUNAS * Bloco.LARGURA;
    public static int ALTURA_INTERFACE = (LINHAS + 1) * ESPACAMENTO + LINHAS * Bloco.ALTURA;

    private boolean iniciado;

    private String localSave;
    private String nomeArquivo = "Recorde";
    Inicio i = new Inicio();

    public Interface(int x, int y) {

        try {
            localSave = Interface.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath(); // salva na pasta do JavaProject
        } catch (Exception e) {
            e.printStackTrace();
        }

        fontePontuacao = Jogo.main.deriveFont(24f);
        this.x = x;
        this.y = y;
        posicao = new Bloco[LINHAS][COLUNAS];
        bordaJogo = new BufferedImage(LARGURA_INTERFACE, ALTURA_INTERFACE, BufferedImage.TYPE_INT_RGB);
        interfaceFinal = new BufferedImage(LARGURA_INTERFACE, ALTURA_INTERFACE, BufferedImage.TYPE_INT_RGB);

        carregaRecorde();
        criaImagemInterface();
        System.setProperty("java.swing.headless", "false");
        inicia(); // inicia o jogo

    }

    private void salvaDados() {
        try {
            File arquivo = new File(localSave, nomeArquivo); //cria arquivo

            FileWriter saida = new FileWriter(arquivo);
            BufferedWriter escrita = new BufferedWriter(saida);
            escrita.write("" + 0);
            escrita.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void carregaRecorde() { // pega valor guardado no arquivo e armazena em recorde
        try {
            File arq = new File(localSave, nomeArquivo); //se nao existir arquivo, chama salvaDados()
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

    public void criaImagemInterface() { //desenha o "mapa" do jogo

        Graphics2D graficos = (Graphics2D) bordaJogo.getGraphics();
        graficos.setColor(Color.darkGray);
        graficos.fillRect(0, 0, LARGURA_INTERFACE, ALTURA_INTERFACE);
        graficos.setColor(Color.black);

        for (int linha = 0; linha < LINHAS; linha++) {

            for (int coluna = 0; coluna < COLUNAS; coluna++) {

                int x = ESPACAMENTO + ESPACAMENTO * coluna + Bloco.LARGURA * coluna;
                int y = ESPACAMENTO + ESPACAMENTO * linha + Bloco.ALTURA * linha;
                graficos.fillRoundRect(x, y, Bloco.LARGURA, Bloco.ALTURA, Bloco.ARC_LARGURA, Bloco.ARC_ALTURA);

            }
        }
    }

    public static void perguntaXastre() {
        System.setProperty("java.swing.headless", "false");
        JFrame easter = new JFrame("EASTER EGG");
        easter.setSize(300, 300);
        easter.setLayout(null);
        easter.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        easter.setLocationRelativeTo(null);
        easter.setVisible(false); // seta JFrame como invisivel para mostrar apenas o botao
        opcao = JOptionPane.showConfirmDialog(easter, "O GRANDIOSO XASTRE GOSTARIA DE TER A VIDA FACILITADA?"); // cria JButton para decidir se usara easter egg
    }

    private void inicia() {
        perguntaXastre(); // pergunta ao usuario
        for (int i = 0; i < numBlocosInicio; i++) {
            spawnaAleatorio();
        }
    }

    private static void spawnaAleatorio() { //spawna n numeros aleatorios no inicio do jogo (definido como constante 2)
        Random random = new Random();
        boolean invalido = true; // considera que os primeiros nao sao validos

        while (invalido) {
            int localizacao = random.nextInt(LINHAS * COLUNAS); //armazena um numero aleatorio de 0 a 15
            //usa o numero recebido anteriormente e encontra posicao relativa da matriz
            int linha = localizacao / LINHAS;
            int coluna = localizacao % COLUNAS;

            Bloco posicaoX = posicao[linha][coluna];
            if (posicaoX == null) {

                if (opcao == JOptionPane.NO_OPTION) {
                    valor = random.nextInt(10) < 9 ? 2 : 4;
                    // pega um valor aleatorio de 0 a 9, se nao for 9, spawna 2, se for 9, spawna 4
                }
                else if(opcao == JOptionPane.YES_OPTION){
                    valor = random.nextInt(10) < 9 ? 512 : 1024;
                }
                else {
                    System.exit(0);
                }
                Bloco bloco = new Bloco(valor, buscaX(coluna), buscaY(linha));
                posicao[linha][coluna] = bloco;
                invalido = false;
            }
        }
    }

    //getters
    public static int buscaX(int coluna) {
        return ESPACAMENTO + coluna * Bloco.LARGURA + coluna * ESPACAMENTO;
    }

    public static int buscaY(int linha) {
        return ESPACAMENTO + linha * Bloco.ALTURA + linha * ESPACAMENTO;
    }

    public void desenhoGeral(Graphics2D graficos) { //une as imagens para formar tela do jogo

        Graphics2D graficos2d = (Graphics2D) interfaceFinal.getGraphics();
        graficos2d.drawImage(bordaJogo, 0, 0, null);

        for (int linha = 0; linha < LINHAS; linha++) {
            for (int coluna = 0; coluna < COLUNAS; coluna++) {

                Bloco posicaoX = posicao[linha][coluna];
                if (posicaoX == null)
                    continue;
                posicaoX.desenhaPeca(graficos2d);

            }
        }

        graficos.drawImage(interfaceFinal, x, y, null);
        graficos2d.dispose();

        graficos.setColor(Color.cyan);
        graficos.setFont(fontePontuacao);
        graficos.drawString("" + pontuacao, 30, 40);
        graficos.setColor(Color.magenta);
        graficos.drawString("Recorde: " + recorde, Jogo.LARGURA
                - DesenhosAuxiliares.recebeLarguraMensagem("Recorde: " + recorde, fontePontuacao, graficos) - 20, 40);

    }

    public void atualiza() {
        ///verifica se o jogo terminou
        if(vitoria == true) {
            setRecorde();
            opcoesVit();
        }
        verificaDerrota();

        //verifica qual movimento o usuario deseja fazer caso jogo nao tenha terminado
        checaTeclas();

        if (pontuacao > recorde) //novo recorde
            recorde = pontuacao;

        for (int linha = 0; linha < LINHAS; linha++) {
            for (int coluna = 0; coluna < COLUNAS; coluna++) {

                Bloco posicaoX = posicao[linha][coluna];
                if (posicaoX == null)
                    continue; //se a posicao estiver vazia, pula pra proxima

                resetaPosicao(posicaoX, linha, coluna);

                if (posicaoX.buscaValor() == 2048) {
                    vitoria = true; //se alguma posicao for igual a 2048, o usuario venceu
                }
            }
        }
    }

    private void resetaPosicao(Bloco posicaoX, int linha, int coluna) {
        if (posicaoX == null)
            return;

        int x = buscaX(coluna);
        int y = buscaY(linha);

        int distX = posicaoX.getX() - x;
        int distY = posicaoX.getY() - y;

        if (Math.abs(distX) < Bloco.VEL_DESLIZE) {
            posicaoX.setX(posicaoX.getX() - distX);
        }
        if (Math.abs(distY) < Bloco.VEL_DESLIZE) {
            posicaoX.setY(posicaoX.getY() - distY);
        }

        if (distX < 0) {
            posicaoX.setX(posicaoX.getX() + Bloco.VEL_DESLIZE);
        }
        if (distY < 0) {
            posicaoX.setY(posicaoX.getY() + Bloco.VEL_DESLIZE);
        }
        if (distX > 0) {
            posicaoX.setX(posicaoX.getX() - Bloco.VEL_DESLIZE);
        }
        if (distY > 0) {
            posicaoX.setY(posicaoX.getY() - Bloco.VEL_DESLIZE);
        }

    }

    static public boolean move(int linha, int coluna, int direcaoHorizontal, int direcaoVertical, Direcao direcao) {
        boolean podeMover = false;

        Bloco posicaoX = posicao[linha][coluna];
        if (posicaoX == null)
            return false; // nao da pra mover posicao vazia
        boolean mover = true;
        int novaLinha = linha;
        int novaColuna = coluna;

        while (mover) { // enquanto puderem combinar ou deslizar para um bloco vazio

            //define que a possivel futura posicao esta na proxima ou anterior linha e coluna
            novaColuna += direcaoHorizontal;
            novaLinha += direcaoVertical;

            if (verificaLimites(direcao, novaLinha, novaColuna))
                break; //nao faz nada caso usuario tenha tentado mover pecas que nao podem ser movidas
            if (posicao[novaLinha][novaColuna] == null) { // posicao esta vazia, portanto pode mover
                posicao[novaLinha][novaColuna] = posicaoX; // move para vazio
                posicao[novaLinha - direcaoVertical][novaColuna - direcaoHorizontal] = null; // esvazia posicao anteriormente ocupada
                posicao[novaLinha][novaColuna].setDeslizaPara(new Ponto(novaLinha, novaColuna));
                podeMover = true;
            } else if (posicao[novaLinha][novaColuna].buscaValor() == posicaoX.buscaValor()
                    && posicao[novaLinha][novaColuna].podeUnir()) {
                posicao[novaLinha][novaColuna].setPodeUnir(false);
                posicao[novaLinha][novaColuna].setValor(posicao[novaLinha][novaColuna].buscaValor() * 2); // multiplica valor por 2
                podeMover = true;
                posicao[novaLinha - direcaoVertical][novaColuna - direcaoHorizontal] = null;
                posicao[novaLinha][novaColuna].setDeslizaPara(new Ponto(novaLinha, novaColuna));
                pontuacao += posicao[novaLinha][novaColuna].buscaValor();
            } else {
                mover = false;
            }
        }

        return podeMover;
    }

    private static boolean verificaLimites(Direcao direcao, int linha, int coluna) {
        if (direcao == Direcao.ESQUERDA) { // se usuario quer mover para esquerda, entao as pecas devem obrigatoriamente estar em uma coluna acima de 0
            return coluna < 0;
        } else if (direcao == Direcao.DIREITA) { // o mesmo raciocinio
            return coluna > COLUNAS - 1;
        } else if (direcao == Direcao.CIMA) { // o mesmo raciocinio
            return linha < 0;
        } else if (direcao == Direcao.BAIXO) { // o mesmo raciocinio
            return linha > LINHAS - 1;
        }
        return false;
    }

    static public void moveBlocos(Direcao direcao) {

        boolean podeMover = false;
        int direcaoHorizontal = 0;
        int direcaoVertical = 0;

        if (direcao == Direcao.ESQUERDA) {
            direcaoHorizontal = -1; // se usuario vai mover para a esquerda, ele deslocara a peca pelo menos para a coluna anterior, portanto -1 (casa)
            for (int linha = 0; linha < LINHAS; linha++) {
                for (int coluna = 0; coluna < COLUNAS; coluna++) {
                    if (!podeMover) {
                        podeMover = move(linha, coluna, direcaoHorizontal, direcaoVertical, direcao);
                    } else
                        move(linha, coluna, direcaoHorizontal, direcaoVertical, direcao);
                }
            }
        } else if (direcao == Direcao.DIREITA) { //segue o racicinio
            direcaoHorizontal = 1;
            for (int linha = 0; linha < LINHAS; linha++) {
                for (int coluna = COLUNAS - 1; coluna >= 0; coluna--) {
                    if (!podeMover) {
                        podeMover = move(linha, coluna, direcaoHorizontal, direcaoVertical, direcao);
                    } else
                        move(linha, coluna, direcaoHorizontal, direcaoVertical, direcao);
                }
            }
        } else if (direcao == Direcao.CIMA) { //segue o raciocinio
            direcaoVertical = -1;
            for (int linha = 0; linha < LINHAS; linha++) {
                for (int coluna = 0; coluna < COLUNAS; coluna++) {
                    if (!podeMover) {
                        podeMover = move(linha, coluna, direcaoHorizontal, direcaoVertical, direcao);
                    } else
                        move(linha, coluna, direcaoHorizontal, direcaoVertical, direcao);
                }
            }
        } else if (direcao == Direcao.BAIXO) { //segue o raciocinio
            direcaoVertical = 1;
            for (int linha = LINHAS - 1; linha >= 0; linha--) {
                for (int coluna = 0; coluna < COLUNAS; coluna++) {
                    if (!podeMover) {
                        podeMover = move(linha, coluna, direcaoHorizontal, direcaoVertical, direcao);
                    } else
                        move(linha, coluna, direcaoHorizontal, direcaoVertical, direcao);
                }
            }
        }

        for (int linha = 0; linha < LINHAS; linha++) {
            for (int coluna = 0; coluna < COLUNAS; coluna++) {
                Bloco posicaoX = posicao[linha][coluna];
                if (posicaoX == null)
                    continue;
                posicaoX.setPodeUnir(true); //se a posicao nao esiver vazia, � uma peca que pode ser unida a outra peca
            }
        }

        if (podeMover) { //spawna mais um 2 ou 4, caso seja feito um movimento
            spawnaAleatorio();
        }
    }

    private void opcoesVit() { // da ao usuario a opcao de jogar novamente em JButton

        JFrame vitoria = new JFrame("VITORIA");
        vitoria.setSize(300, 300);
        vitoria.setLayout(null);
        vitoria.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        vitoria.setLocationRelativeTo(null);
        vitoria.setVisible(false);
        int opcao = JOptionPane.showConfirmDialog(vitoria, "PARABENS! Voce venceu... Deseja jogar novamente?");
        if (opcao == JOptionPane.YES_OPTION) {

            for (int i = 0; i < LINHAS; i++)
                for (int j = 0; j < COLUNAS; j++) {
                    posicao[i][j] = null;
                }

            pontuacao = 0;
            this.vitoria = false;
            criaImagemInterface();
            inicia();

        } else System.exit(0);

    }

    private void verificaDerrota() { //verifica se usuario pode mover alguma peca
        for (int linha = 0; linha < LINHAS; linha++) {
            for (int coluna = 0; coluna < COLUNAS; coluna++) {
                if (posicao[linha][coluna] == null)
                    return; // existe posicao vazia, portanto pode mover
                if (verificaBlocosEmVolta(linha, coluna, posicao[linha][coluna])) {
                    return; //existe uma posicao vazia ou uma peca igual ao redor, portanto pode mover
                }
            }
        }

        // se nao pode mover, o usuario perdeu
        opcoesDer();

    }

    private void opcoesDer() { // mesmo do verificaVit para derrota

        JFrame derrota = new JFrame("DERROTA");
        derrota.setSize(300, 300);
        derrota.setLayout(null);
        derrota.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        derrota.setLocationRelativeTo(null);
        derrota.setVisible(false);
        int opcao = JOptionPane.showConfirmDialog(derrota, "Voce Perdeu! Deseja jogar novamente?");
        if (opcao == JOptionPane.YES_OPTION) {

            for (int i = 0; i < LINHAS; i++)
                for (int j = 0; j < COLUNAS; j++) {
                    posicao[i][j] = null;
                }

            setRecorde();
            pontuacao = 0;
            criaImagemInterface();
            inicia();

        } else if (opcao == JOptionPane.NO_OPTION || opcao == JOptionPane.CANCEL_OPTION ) {
            System.exit(0);
        }

    }

    private boolean verificaBlocosEmVolta(int linha, int coluna, Bloco posicaoX) { //verifica se ha posicao vazia ou peca igual ao redor da peca
        if (linha > 0) {
            Bloco checar = posicao[linha - 1][coluna];
            if (checar == null)
                return true;
            if (posicaoX.buscaValor() == checar.buscaValor())
                return true;
        }
        if (linha < LINHAS - 1) {
            Bloco checar = posicao[linha + 1][coluna];
            if (checar == null)
                return true;
            if (posicaoX.buscaValor() == checar.buscaValor())
                return true;
        }
        if (coluna > 0) {
            Bloco checar = posicao[linha][coluna - 1];
            if (checar == null)
                return true;
            if (posicaoX.buscaValor() == checar.buscaValor())
                return true;
        }
        if (coluna < COLUNAS - 1) {
            Bloco checar = posicao[linha][coluna + 1];
            if (checar == null)
                return true;
            if (posicaoX.buscaValor() == checar.buscaValor())
                return true;
        }
        return false; // nao ha posicao vazia nem uma peca igual ao redor da peca verificada
    }

    public void checaTeclas() {

        if (Teclado.teclado(KeyEvent.VK_LEFT) || Teclado.teclado(KeyEvent.VK_A)) {
            moveBlocos(Direcao.ESQUERDA); //envia 0
            if (!iniciado)
                iniciado = true;
        }
        if (Teclado.teclado(KeyEvent.VK_RIGHT) || Teclado.teclado(KeyEvent.VK_D)) {

            moveBlocos(Direcao.DIREITA); //envia 1
            if (!iniciado)
                iniciado = true;
        }
        if (Teclado.teclado(KeyEvent.VK_UP) || Teclado.teclado(KeyEvent.VK_W)) {

            moveBlocos(Direcao.CIMA); //envia 2
            if (!iniciado)
                iniciado = true;
        }
        if (Teclado.teclado(KeyEvent.VK_DOWN) || Teclado.teclado(KeyEvent.VK_S)) {

            moveBlocos(Direcao.BAIXO); // envia 3
            if (!iniciado)
                iniciado = true;
        }

    }

}
