public class Player {

    private static int hitPoints;

    public Player(){

        this.hitPoints = 10;

        Board board = new Board();
        board.print();
        board.setLocation(500, 100);
        board.setUndecorated(true);
    }


    public static void hit(){
        hitPoints--;
    }

    public void go(){

    }
}
