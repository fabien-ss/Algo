package taquin;

import java.util.Random;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.HashSet;
import java.util.List;
import java.util.LinkedList;

import java.util.LinkedList;
import java.util.Queue;

public class Bloc {

    int x;
    int y;
    int numero;
    String couleur;
    ArrayList<Bloc> ways;
    
    public List<Game> nextMoves(Game game){
        List<Game> itterations = new ArrayList<Game>();
        Game nouvelle_table = new Game();
        for(Bloc b : ways){
                nouvelle_table = null;
                
                nouvelle_table = game.deep_clone();

                Bloc possibily = b;
                int tx = this.x;
                int ty = this.y;

                Bloc temp = nouvelle_table.getBlocs()[possibily.x][possibily.y];
                nouvelle_table.getBlocs()[possibily.x][possibily.y] = nouvelle_table.getBlocs()[tx][ty];
                nouvelle_table.getBlocs()[possibily.x][possibily.y].x = possibily.x;
                nouvelle_table.getBlocs()[possibily.x][possibily.y].y = possibily.y;
                
                nouvelle_table.getBlocs()[tx][ty] = temp;
                nouvelle_table.getBlocs()[tx][ty].x = tx;
                nouvelle_table.getBlocs()[tx][ty].y = ty;
                nouvelle_table.getBlocs()[possibily.x][possibily.y].getPossibility(nouvelle_table);
              //  nouvelle_table.getBlocs()[possibily.x][possibily.y].getTree(nouvelle_table, itteration - 1);
   
                itterations.add(nouvelle_table);
            }
        return itterations;
    }

    public Queue<Game> getFile(Game game) {

        Queue<Game> file = new LinkedList<>();
        HashSet<String> parcourues = new HashSet<>();

        Game racine = game;

        file.add(racine);
        parcourues.add(racine.getBoardString(racine.getBlocs())); // Ajouter l'état initial à l'ensemble des états visités

        while (!file.isEmpty()) {
            Game currentGame = file.poll();

            Bloc piv = currentGame.getPivot();
            piv.getPossibility(currentGame);
            List<Game> nextMoves = piv.nextMoves(currentGame);

            for (Game move : nextMoves) {
                if (!parcourues.contains(move.getBoardString(move.getBlocs()))) {
                    file.add(move);
                    move.precedent = currentGame;
                    move.cout = currentGame.cout;
                    parcourues.add(move.getBoardString(move.getBlocs()));
                }
            }
        }
        return file; // Aucune solution trouvée
    }

    public ArrayList<Game> parcourEnLargeur2(Game game) {

        ArrayList<Game> tonga = new ArrayList<>();
        Queue<Game> file = new LinkedList<>();
        HashSet<String> parcourues = new HashSet<>();

        Game racine = game;

        file.add(racine);
        parcourues.add(racine.getBoardString(racine.getBlocs())); // Ajouter l'état initial à l'ensemble des états visités

        while (!file.isEmpty()) {
            Game currentGame = file.poll();

            Bloc piv = currentGame.getPivot();
            piv.getPossibility(currentGame);
            List<Game> nextMoves = piv.nextMoves(currentGame);

            for (Game move : nextMoves) {
                if (!parcourues.contains(move.getBoardString(move.getBlocs()))) {
                    file.add(move);
                    move.precedent = currentGame;
                    move.cout = currentGame.cout + 1;
                    parcourues.add(move.getBoardString(move.getBlocs()));
                    if (move.check()) {
                        tonga.add(move);
                    }
                }
            }
        }

        return tonga; // Aucune solution trouvée
    }
   
    public Game parcourEnLargeur(Game game) {
        
        Queue<Game> file = new LinkedList<>();
        HashSet<String> parcourues = new HashSet<>();

        Game racine = game;

        file.add(racine);
        parcourues.add(racine.getBoardString(racine.getBlocs())); // Ajouter l'état initial à l'ensemble des états visités

        while (!file.isEmpty()) {
            Game currentGame = file.poll();

            Bloc piv = currentGame.getPivot();
            piv.getPossibility(currentGame);
            List<Game> nextMoves = piv.nextMoves(currentGame);

            for (Game move : nextMoves) {
                if (!parcourues.contains(move.getBoardString(move.getBlocs()))) {
                    file.add(move);
                    move.precedent = currentGame;
                    move.cout = currentGame.cout + 1;
                    parcourues.add(move.getBoardString(move.getBlocs()));
                    if (move.check()) {
                        return move;
                    }
                }
            }
        }

        return null; // Aucune solution trouvée
    }

    
    public void printPossibility(){
        for(Bloc b : ways){
            System.out.println(b.numero);
        }
    }

    
    public void getPossibility(Game game){
        this.ways = new ArrayList<Bloc>();
        try{
            ways.add(game.getBlocs()[this.x+1][this.y]);
        }
        catch(Exception e){}
        try{
            ways.add(game.getBlocs()[this.x-1][this.y]);
        }
        catch(Exception e){}
        try{
            ways.add(game.getBlocs()[this.x][this.y+1]);
        }
        catch(Exception e){}
        try{
            ways.add(game.getBlocs()[this.x][this.y-1]);
        }
        catch(Exception e){}
    }

    public void getPossibility2(Game game){
        this.ways = new ArrayList<Bloc>();
        for(int i = 0; i < game.getBlocs().length; i++){
            for(int j = 0; j < game.getBlocs()[i].length; j++){
                int x1 = this.x - game.getBlocs()[i][j].x;
                int y1 = this.y - game.getBlocs()[i][j].y;
                float distance = (float) Math.sqrt(x1*x1+y1*y1);
                if(distance == (float) 1 && game.getBlocs()[i][j].numero != this.numero){
                    this.ways.add(game.getBlocs()[i][j]);
                }
            }
        }
    }

    public void shuffleBoard(Game game, int itteration, int sleep) {
        if(itteration != 0){
            try {
                Thread.sleep(100/*sleep*/); // Pause de 1 seconde entre chaque itération
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            
            int randomNum = getRandomNumber(0, this.ways.size() - 1);
            Bloc possibily = ways.get(randomNum);

            int tx = this.x;
            int ty = this.y;

            Bloc temp = game.getBlocs()[possibily.x][possibily.y];
            game.getBlocs()[possibily.x][possibily.y] = game.getBlocs()[tx][ty];
            game.getBlocs()[possibily.x][possibily.y].x = possibily.x;
            game.getBlocs()[possibily.x][possibily.y].y = possibily.y;
            
            game.getBlocs()[tx][ty] = temp;
            game.getBlocs()[tx][ty].x = tx;
            game.getBlocs()[tx][ty].y = ty;

            game.getBlocs()[this.x][this.y].getPossibility(game);
            game.getBlocs()[this.x][this.y].shuffleBoard(game, itteration - 1, 0);
        }
    }
    public static void randomize(Game game, int itteration, int sleep) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                Bloc pivot = game.getPivot(); 
                pivot.getPossibility(game);
                pivot.printPossibility();
                pivot.shuffleBoard(game, itteration, sleep);
               // shuffleBoard(game, itteration);
            }
        });

        thread.start();
    }
    public static int getRandomNumber(int min, int max) {
        Random random = new Random();
        return random.nextInt(max - min + 1) + min;
    }
    public Bloc(int x, int y, int numero, String couleur){
        this.setX(x);
        this.setY(y);
        this.setCouleur(couleur);
        this.setNumero(numero);
    }
    public Bloc(){}
    public void setX(int x){
        this.x = x;
    }
    public void setY(int y){
        this.y = y;
    }
    public int getX()
    {
        return this.x;
    }  
    public int getY(){
        return this.y;
    }
    public void setNumero(int numero){
        this.numero = numero;
    }
    public void setCouleur(String couleur){
        this.couleur = couleur;
    }
    public int getNumero(){
        return this.numero;
    }
    public String getCouleur(){
        return this.couleur;
    }
    public static void main(String[] args) throws Exception{
        
        Scanner scan = new Scanner(System.in);
        int deep = scan.nextInt();

        Game game = new Game();
        game.init(3, 3);
        System.out.println(game.check());
        game.printBoard();
        game.getBlocs()[2][2].getPossibility(game);
        game.getBlocs()[2][2].printPossibility();
        game.getBlocs()[2][2].shuffleBoard(game, deep, 0);
        System.out.println("Mélangé");
        game.printBoard();

        Bloc pivot = game.getPivot();

        pivot.getPossibility(game);

        Game blc = pivot.parcourEnLargeur(game);
        LinkedList<Game> gs = blc.etape();
        System.out.println("cout1:"+blc.getCout());
  //      game.synthese(blc);
//        game.printBoard();

        ArrayList<Game> gamess = pivot.parcourEnLargeur2(game);
        System.out.println(gamess.size());
        for(Game g : gamess){
            System.out.println("cout:"+g.getCout());
        }
    }
}