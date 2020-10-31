import java.awt.*;

public class Captain {

    private static Player one;
    private static Player two;

    public static void main(String[] args) {

        EventQueue.invokeLater(new Runnable(){
            @Override
            public void run() {
                one = new Player();
            }
        });


    }
}
