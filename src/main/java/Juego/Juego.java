package Juego;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class Juego extends JFrame implements KeyListener {
    private JLabel label1;
    private ImageIcon image1, image2;
    private int x1, y1;
    private boolean gameOver;

    public Juego() {
        super("Moving Image");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        ImageIcon originalImage1 = new ImageIcon("src/main/java/img/imagen.png");
        ImageIcon originalImage2 = new ImageIcon("src/main/java/img/imagen2.png");
        Image scaledImage1 = originalImage1.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        Image scaledImage2 = originalImage2.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        image1 = new ImageIcon(scaledImage1);
        image2 = new ImageIcon(scaledImage2);

        label1 = new JLabel(image1);

        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.add(label1);
        getContentPane().add(panel);

        x1 = 200;
        y1 = 200;
        label1.setBounds(x1, y1, 100, 100);

        addKeyListener(this);

        gameOver = false;
        Thread t = new Thread(new Runnable() {
            public void run() {
                while(!gameOver) {
                    Random random = new Random();
                    for (int i = 0; i < 4; i++) {
                        int x2 = 450;
                        int y2 = random.nextInt(450) + 1;
                        JLabel label2 = new JLabel(image2);
                        panel.add(label2);
                        label2.setBounds(x2, y2, 50, 50);
                        while (x2 > 0) {
                            x2 -= 10;
                            label2.setBounds(x2, y2, 50, 50);
                            Rectangle r1 = new Rectangle(x1, y1, 100, 100);
                            Rectangle r2 = new Rectangle(x2, y2, 50, 50);
                            if (r1.intersects(r2)) {
                                int option = JOptionPane.showOptionDialog(null, "Fin del juego", "Juego terminado", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, null, null);
                                if (option == JOptionPane.OK_OPTION) {
                                    System.exit(0);
                                }
                            }
                            try {
                                Thread.sleep(50);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        panel.remove(label2);
                    }
                }
            }
        });
        t.start();

        setSize(500, 500);
        setVisible(true);
    }

    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if (keyCode == KeyEvent.VK_W) {
            y1 -= 10;
        } else if (keyCode == KeyEvent.VK_S) {
            y1 += 10;
        } else if (keyCode == KeyEvent.VK_A) {
            x1 -= 10;
        } else if (keyCode == KeyEvent.VK_D) {
            x1 += 10;
        }
        label1.setBounds(x1, y1, 100, 100);
        getContentPane().repaint();
    }

    public void keyTyped(KeyEvent e) {}
    public void keyReleased(KeyEvent e) {}

    public static void main(String[] args) {
        new Juego();
    }
}