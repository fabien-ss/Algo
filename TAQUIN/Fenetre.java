package affichage;
import taquin.*;

import java.awt.Color;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JLabel;

public class Fenetre extends JFrame{
   
    public Fenetre(Game game, int tile_size, int margin){
     
        this.setTitle("TALLIN");
        this.setSize(350,450);
        Taquin terrain = new Taquin(tile_size, margin, game, this.getWidth(), this.getHeight());
        
        terrain.setBackground(new Color(0x2E678A));

        this.add(terrain);
        
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }    
}
