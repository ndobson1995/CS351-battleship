import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Leaderboard {
    Scanner scanner = new Scanner(System.in);
    private String userName;
    private boolean activePlayer;
    private int score;


    private void login(){
        LoginPortal loginPortal = new LoginPortal();

    }

    public static void main(String[] args) {
        Map<String, Integer> unsortedMap = new HashMap<String, Integer>();
        unsortedMap.put("Jack", 60);
        unsortedMap.put("Bob", 40);
        unsortedMap.put("Rick", 20);

//        Map<String, Integer> sortedMap = sortByValue(unsortedMap);
//        printMap(sortedMap);
    }

}

