package com.TESTE;
import java.awt.event.KeyEvent;

public class Teclado {

    public static final int ASCII = 256;
    public static boolean[] pressionado = new boolean[ASCII];
    public static boolean[] anterior = new boolean[ASCII];

    private Teclado() {
    }

    public static void atualiza() {
        for (int i = 0; i < 4; i++) {
            switch (i) {

                case 0:
                    anterior[KeyEvent.VK_LEFT] = pressionado[KeyEvent.VK_LEFT];
                    anterior[KeyEvent.VK_A] = pressionado[KeyEvent.VK_A];
                    break;
                case 1:
                    anterior[KeyEvent.VK_RIGHT] = pressionado[KeyEvent.VK_RIGHT];
                    anterior[KeyEvent.VK_D] = pressionado[KeyEvent.VK_D];
                    break;
                case 2:
                    anterior[KeyEvent.VK_UP] = pressionado[KeyEvent.VK_UP];
                    anterior[KeyEvent.VK_W] = pressionado[KeyEvent.VK_W];
                    break;
                case 3:
                    anterior[KeyEvent.VK_DOWN] = pressionado[KeyEvent.VK_DOWN];
                    anterior[KeyEvent.VK_S] = pressionado[KeyEvent.VK_S];
                    break;

            }
        }
    }

    public static void KeyPressed(KeyEvent e) {
        pressionado[e.getKeyCode()] = true;
    }

    public static void KeyReleased(KeyEvent e) {
        pressionado[e.getKeyCode()] = false;
    }

    public static boolean teclado(int KeyEvent) {
        return !pressionado[KeyEvent] && anterior[KeyEvent];
    }

}
