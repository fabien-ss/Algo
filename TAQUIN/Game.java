package taquin;

import java.util.ArrayList;
import java.util.LinkedList;

public class Game implements Comparable<Game>{

    Boolean nietsika = false;
    int last_move;
    Game precedent;
    int cout;
    Bloc[][] blocs;

    public void synthese(Game fin){
        LinkedList<Game> gs = fin.etape();
        for(Game g : gs){
            try {
                Thread.sleep(500/*sleep*/); // Pause de 1 seconde entre chaque itération
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            this.blocs = g.blocs;
            this.nietsika = g.nietsika;
            this.cout = g.cout;
        }
    }
    public static void slow(Game debut, Game fin){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                debut.synthese(fin);
            }
        });

        thread.start();
    }
    public LinkedList<Game> etape(){
        LinkedList<Game> list = new LinkedList<>();
        list.add(0, this);
        while(this.precedent != null){
            list.addFirst(this.precedent);
            this.precedent = this.precedent.precedent;
        }
        return list;
    }

    public void printReverseWay(){
        if(this.precedent != null){
            this.precedent.printBoard();
            this.precedent.printReverseWay();
        }
    }
    
    public Boolean check(){
        int u = 1;
        for(int i = 0; i < this.getBlocs().length; i++){
            for(int j = 0; j < this.getBlocs()[i].length; j++){
                if(this.getBlocs()[i][j].numero != u){
                    return false;
                }
                u += 1;
            }
        }
        return true;
    }
    public void printFile(){

    }

    public String getBoardString(Bloc[][] blocss) {
        StringBuilder sb = new StringBuilder();
        for (Bloc[] row : blocss) {
            for (Bloc value : row) {
                sb.append(value.numero+"");
            }
        }
        return sb.toString();
    }

    public Bloc getPivot(){
        for(int i = 0; i < this.getBlocs().length; i++){
            for(int j = 0; j < this.getBlocs()[i].length; j++){
                if(this.getBlocs()[i][j].numero == 9){
                    return this.getBlocs()[i][j];
                }
            }
        }
        return null;
    }

    /*public void printTree(){
        for(Game g : this.game){
            System.out.println("\n");
            g.printBoard();
            System.out.println("\n");
        }
    }*/

    public void setCout(int c){
        this.cout = c;
    }
    public int getCout(){
        return this.cout;
    }

    public Bloc[][] cloner_bloc() {
        Bloc[][] retour = new Bloc[this.blocs.length][this.blocs.length];
        for(int i = 0; i < this.blocs.length; i++){
            for(int j = 0; j < this.blocs[i].length; j++){
                Bloc bc = new Bloc();
                bc.x = this.blocs[i][j].x;
                bc.y = this.blocs[i][j].y;
                bc.numero = this.blocs[i][j].numero;
                bc.couleur = this.blocs[i][j].couleur;
                retour[i][j] = bc;
            }
        }
        return retour;
    }

    public Game deep_clone() {
        Game g = new Game();
        g.setBlocs(this.cloner_bloc());
        return g;
    }

    public void tree(){
    
    }

    public void init(int l, int c){
        this.blocs = new Bloc[l][c];
      //  for(int i = 0; i < l; i++){
        blocs[0][0] = new Bloc(0 , 0, 1, "");
        blocs[0][1] = new Bloc(0 , 1, 2, "");
        blocs[0][2] = new Bloc(0 , 2, 3, "");
      //  blocs[0][3] = new Bloc(0 , 3, 4, "");

        blocs[1][0] = new Bloc(1 , 0, 4, "");
        blocs[1][1] = new Bloc(1 , 1, 5, ""); 
        blocs[1][2] = new Bloc(1 , 2, 6, "");
     //   blocs[1][3] = new Bloc(1 , 3, 8, "");

        blocs[2][0] = new Bloc(2 , 0, 7, ""); 
        blocs[2][1] = new Bloc(2 , 1, 8, ""); 
        blocs[2][2] = new Bloc(2 , 2, 9, "");
       // blocs[2][3] = new Bloc(2 , 3, 12, "");

        /*blocs[3][0] = new Bloc(3 , 0, 13, ""); 
        blocs[3][1] = new Bloc(3 , 1, 14, ""); 
        blocs[3][2] = new Bloc(3 , 2, 15, "");
        blocs[3][3] = new Bloc(3 , 3, 16, "");*/
        //}
    }
    public void printBoard(){
        for(int i = 0; i < blocs.length; i++){
            System.out.println("");
            for(int j = 0; j < blocs[i].length; j++){
                System.out.print(blocs[i][j].getNumero() + " [" +blocs[i][j].getX()+blocs[i][j].getY()+"] "); 
            }
        }
    }
    public Game(){
    }
    public void setBlocs(Bloc[][] blocs){
        this.blocs = blocs;
    }
    public Bloc[][] getBlocs(){
        return this.blocs;
    }
    @Override
    public int compareTo(Game other) {
        return Integer.compare(this.cout, other.cout);
    }
    public static void main(String[] args){
        
        LinkedList<Integer> list = new LinkedList<>();

        // Ajouter un élément au premier indice
        list.addFirst(10);
        list.addFirst(20);

        // Parcourir la liste
        for (int element : list) {
            System.out.println(element);
        }

    }
}