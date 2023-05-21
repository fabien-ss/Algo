import taquin.Game;
import affichage.Fenetre;

public class Main{
    public static void main(String[] args) {
        Game game = new Game();
        game.init(3, 3);
        Fenetre fen = new Fenetre(game, 100, 5);
    }
}