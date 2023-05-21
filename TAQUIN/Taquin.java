package affichage;
import taquin.*;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.*;
import java.awt.Graphics2D;
import java.awt.*;
import java.awt.geom.Rectangle2D;

public class Taquin extends JPanel implements ActionListener {

    public int SIZE = 3; // Taille du taquin
    public int TILE_SIZE = 80; // Taille d'une case du taquin
    public int MARGIN = 10; // Marge entre les cases

    public Game game; // Tableau représentant l'état du taquin
    public JButton resetButton; // Bouton de réinitialisation
    public JButton playButton; 
    public JTextField mouvement;

    public Taquin(int tile, int margin, Game game, int w, int h) {

        this.SIZE = game.getBlocs().length;
        this.TILE_SIZE = ((int) w / SIZE) - 3;
        this.MARGIN = margin;
        
        this.game = game;
        //this.board = new int[this.SIZE][this.SIZE];
        // Initialise l'état initial du taquin ici

        this.setLayout(new FlowLayout());
            resetButton = new JButton("Réinitialiser");
            resetButton.addActionListener(this);

            playButton = new JButton("Résoudre");
            playButton.addActionListener(this);

            resetButton.setBackground(new Color(0x263C73));
            resetButton.setForeground(Color.WHITE);
            resetButton.setBorder(null);
            playButton.setBackground(new Color(0x263C73));
            playButton.setForeground(Color.WHITE);
            playButton.setBorder(null);
            mouvement = new JTextField();
            mouvement.setBorder(null);

            add(resetButton);
            add(playButton);
            add(mouvement);
        }

    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2 =(Graphics2D) g;

        drawBoard(g2);
        repaint();
    }

    public void drawBoard(Graphics2D g) {
        for (int row = 0; row < this.game.getBlocs().length; row++) {
            for (int col = 0; col < this.game.getBlocs()[row].length; col++) {
                int value = this.game.getBlocs()[row][col].getNumero();
                if (value != 9) {
                    int x = col * (TILE_SIZE + MARGIN);
                    int y = row * (TILE_SIZE + MARGIN);

                    GradientPaint gradient = new GradientPaint(x, y, Color.LIGHT_GRAY, TILE_SIZE, TILE_SIZE, Color.DARK_GRAY);
                    Rectangle2D rectangle = new Rectangle2D.Double(x, y, TILE_SIZE, TILE_SIZE);

                    g.setColor(new Color(0x263C73));
                  //  g.fillRect(x, y, TILE_SIZE, TILE_SIZE);
                    g.fill(rectangle);
                    g.setColor(Color.WHITE);
                    g.drawString(Integer.toString(value), x + TILE_SIZE / 2, y + TILE_SIZE / 2);
                }
            }
        }

        // Positionner le bouton de réinitialisation
        resetButton.setBounds(10, SIZE * (TILE_SIZE + MARGIN) + 10, 100, 30);
        playButton.setBounds(205, SIZE * (TILE_SIZE + MARGIN) + 10, 100, 30);
        mouvement.setBounds(132, SIZE * (TILE_SIZE + MARGIN) + 10, 50, 30);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == resetButton) {
            String moov = this.mouvement.getText();
            Bloc.randomize(game, Integer.valueOf(moov), 1000);
        }
        if (e.getSource() == playButton) {
            Bloc pivot = game.getPivot();
            pivot.getPossibility(game);
            Game fin = pivot.parcourEnLargeur(game);
            Game.slow(game, fin);
        }
    }
}
