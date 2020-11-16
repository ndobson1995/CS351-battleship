import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Leaderboard {
    Scanner scanner = new Scanner(System.in);

    private Map<String,Integer> leaderboardMap = new HashMap<String,Integer>();
    private String userName;
    private int numberOfWins;
    private int numberOfLoses;
    private int totalGamesPlayed;

    Leaderboard() {
        System.out.println("Here is the leaderboard");
    }

    public static void main(String[] args) {
        //updateWinnerLoserFile("winner",100,"AI", 200);
    }


    public static void updateWinnerLoserFile(String player,int playerWin, String opponent, int opponentLose){
        Leaderboard leaderboard = new Leaderboard();
        //leaderboard.savePlayerStatstoFile(player, playerWin, opponent, opponentLose);
        System.out.println("PLAYER :" + player + " SCORE: "  + playerWin);
        System.out.println("OPONNENT :" + opponent + " SCORE: "  + opponentLose);

        //todo this still doesnt take into account the games attempted, no of wins or loses YET

    }


    public void savePlayerStatstoFile(String player,int playerWins, int playerLoses, int playerAttempts){
        //TEST DATTA BELOW- DELETE LATER
        player="sonja";

        //leaderboardMap.put(player,playerWins);
        //TODO take in winner loser and append to leadership file
        //todo sort by values decending order

        try{
            FileWriter fw = new FileWriter("leaderboard-file.txt",true);
            BufferedWriter bf = new BufferedWriter(fw);
            bf.append("Player name\t\t Player Wins\t\t Player Loses\t\t Player Attempts");
            for(Map.Entry<String, Integer> entry : leaderboardMap.entrySet()){
                bf.append(entry.getKey()).append("\t\t").append(String.valueOf(entry.getValue()));
                bf.newLine();
            }
            bf.close();
        } catch(FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void leaderboardFile(){

        //todo: username, score, no of wins, no of loser, total games


        //System.out.println("debug --> here's the map of logins " + loginMap);
    }

//    public static void main(String[] args) {
//        Map<String, Integer> unsortedMap = new HashMap<String, Integer>();
//        unsortedMap.put("Jack", 60);
//        unsortedMap.put("Bob", 40);
//        unsortedMap.put("Rick", 20);
//
////        Map<String, Integer> sortedMap = sortByValue(unsortedMap);
////        printMap(sortedMap);
//    }

}

