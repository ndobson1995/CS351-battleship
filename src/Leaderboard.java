import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Leaderboard {
    private ArrayList<String> nestedLeaderboardMap = new ArrayList<String>();
    private ArrayList<String> leaderboardMap = new ArrayList<String>();


    Leaderboard() {
        System.out.println("Here is the leaderboard");
    }

    public static void main(String[] args) {
        TESTMETHOD_updateWinnerLoserFile("sonja",2,3, 1,"AI",3,5,4);
    }

    //todo this method will be deleted once all the leaderboard stuff works
    public static void TESTMETHOD_updateWinnerLoserFile(String player,int playerWin, int playerLoses, int playerAttempts,String opponent, int opponentWins, int opponentLose, int oponentAttempts){
        Leaderboard leaderboard = new Leaderboard();
        leaderboard.savePlayerStatstoFile(player, playerWin, playerLoses,playerAttempts);
        leaderboard.savePlayerStatstoFile(opponent, opponentWins, opponentLose,oponentAttempts);
        System.out.println("PLAYER :" + player + " SCORE: "  + playerWin);
        System.out.println("OPPONENT :" + opponent + " SCORE: "  + opponentLose);


    }


    public void savePlayerStatstoFile(String player,int playerWins, int playerLoses, int playerAttempts){
        String playerwins=Integer.toString(playerWins);
        String playerloses=Integer.toString(playerLoses);
        String playerattempts=Integer.toString(playerAttempts);


        nestedLeaderboardMap.add(player);
        nestedLeaderboardMap.add(playerwins);
        nestedLeaderboardMap.add(playerloses);
        nestedLeaderboardMap.add(playerattempts);
        leaderboardMap.add(String.valueOf(nestedLeaderboardMap));
        //todo sort by values decending order, but im passing in the values as string - figure this out

        try{
            FileWriter fw = new FileWriter("leaderboard-file.txt",true);
            BufferedWriter bf = new BufferedWriter(fw);
            bf.append("\nPlayer name\t\t Player Wins\t\t Player Loses\t\t Player Attempts\n");

//            for (i = 0; i < leaderboardMap.length(); i++) {
//                for (j = 0; j < nestedLeaderboardMap.length; j++) {
//                }
//
//                }


//            for(Map.Entry<String, Integer> entry : leaderboardMap.entrySet()){
//                bf.append(entry.getKey()).append("\t\t").append(String.valueOf(entry.getValue()));
//                bf.newLine();
//            }
            bf.close();
        } catch(FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

