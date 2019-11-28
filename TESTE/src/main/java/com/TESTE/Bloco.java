package com.TESTE;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Bloco {

    public static final int LARGURA = 80;
    public static final int ALTURA = 80;
    public static final int VEL_DESLIZE = 20;
    public static final int ARC_LARGURA = 15;
    public static final int ARC_ALTURA = 15;

    private int valor;
    private BufferedImage imagemBloco;
    private Color corFundo;
    private Color corNumero;
    private Font fonte;
    private Ponto deslizaPara;
    private int x; // coordenadas
    private int y; // coordenadas

    private boolean podeUnir = true;

    public Bloco(int valor, int x, int y) {
        this.valor = valor;
        this.x = x;
        this.y = y;
        deslizaPara = new Ponto(x, y);
        imagemBloco = new BufferedImage(LARGURA, ALTURA, BufferedImage.TYPE_INT_ARGB);
        desenhaImagem();
    }

    private void desenhaImagem() {

        Graphics2D graficos = (Graphics2D) imagemBloco.getGraphics();

        switch (valor) { // define cores de fundo e texto para cada valor no bloco

            case 2:
                corFundo = new Color(0xff71ce);
                corNumero = new Color(0x000000);
                break;
            case 4:
                corFundo = new Color(0x01cdfe);
                corNumero = new Color(0x000000);
                break;
            case 8:
                corFundo = new Color(0x05ffa1);
                corNumero = new Color(0x000000);
                break;
            case 16:
                corFundo = new Color(0xb967ff);
                corNumero = new Color(0x000000);
                break;
            case 32:
                corFundo = new Color(0xfffb96);
                corNumero = new Color(0x000000);
                break;
            case 64:
                corFundo = new Color(0x00aba9);
                corNumero = new Color(0x000000);
                break;
            case 128:
                corFundo = new Color(0xff0097);
                corNumero = new Color(0x000000);
                break;
            case 256:
                corFundo = new Color(0xa200ff);
                corNumero = new Color(0x000000);
                break;
            case 512:
                corFundo = new Color(0x1ba1e2);
                corNumero = new Color(0x000000);
                break;
            case 1024:
                corFundo = new Color(0xf09609);
                corNumero = new Color(0x000000);
                break;
            case 2048:
                corFundo = new Color(0xffffff);
                corNumero = new Color(0x000000);
                break;

        }

        graficos.setColor(new Color(0, 0, 0, 0)); // totalmente transparente
        graficos.fillRect(0, 0, LARGURA, ALTURA);
        graficos.setColor(corFundo);
        graficos.fillRoundRect(0, 0, LARGURA, ALTURA, ARC_LARGURA, ARC_ALTURA);
        graficos.setColor(corNumero);

        if(valor <= 64) fonte = Jogo.main.deriveFont(36f); //para numeros com menos de 3 digitos
        else fonte = Jogo.main;

        graficos.setFont(fonte);

        int desenhoX = LARGURA / 2 - DesenhosAuxiliares.recebeLarguraMensagem("" + valor, fonte, graficos) / 2;
        int desenhoY = ALTURA / 2 + DesenhosAuxiliares.recebeAlturaMensagem("" + valor, fonte, graficos) / 2;
        graficos.drawString("" + valor, desenhoX, desenhoY);
        graficos.dispose();

    }

    public void desenhaPeca(Graphics2D graficos) {
        graficos.drawImage(imagemBloco, x, y, null);
    }

    public int buscaValor() {
        return valor;
    }

    public void setValor(int valor) {
        this.valor = valor;
        desenhaImagem();
    }

    public boolean podeUnir() {
        return podeUnir;
    }

    public void setPodeUnir(boolean podeUnir) {
        this.podeUnir = podeUnir;
    }

    public Ponto getDeslizaPara() {
        return deslizaPara;
    }

    public void setDeslizaPara(Ponto deslizaPara) {
        this.deslizaPara = deslizaPara;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

}
