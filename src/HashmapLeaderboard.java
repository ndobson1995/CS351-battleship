import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class HashmapLeaderboard {

    final static String leaderboardFilePath = "leaderboard.txt";

    public static void main(String[] args) {
        read();
        System.out.println("Will played: " + getPlayedTotal("Nicole"));
        System.out.println("Will won: " + getWins("Nicole"));
        System.out.println("Will lost: " + getLosses("Nicole"));
    }


    public static int getWins(String player){
        Map<String, ArrayList<Integer>> leaderboard = readLeaderboard();
        int gamesPlayed = 0;
        if(leaderboard.containsKey(player)){
            ArrayList<Integer> scores = leaderboard.get(player);
            gamesPlayed = scores.get(0);
        }
        return gamesPlayed;
    }


    public static int getLosses(String player){
        Map<String, ArrayList<Integer>> leaderboard = readLeaderboard();
        int gamesPlayed = 0;
        if(leaderboard.containsKey(player)){
            ArrayList<Integer> scores = leaderboard.get(player);
            gamesPlayed = scores.get(1);
        }
        return gamesPlayed;
    }


    public static int getPlayedTotal(String player){
        Map<String, ArrayList<Integer>> leaderboard = readLeaderboard();
        int gamesPlayed = 0;
        if(leaderboard.containsKey(player)){
            ArrayList<Integer> scores = leaderboard.get(player);
            gamesPlayed = scores.get(2);
        }
        return gamesPlayed;
    }


    private static Map<String, ArrayList<Integer>> readLeaderboard() {
        Map<String, ArrayList<Integer>> leaderboard = new HashMap<>();

        try {
            File file = new File(leaderboardFilePath);
            BufferedReader buffStuffReader = new BufferedReader(new FileReader(file));

            String line;

            while ((line = buffStuffReader.readLine()) != null) {
                String[] parts = line.split(":");

                // tidy up what we're taking in so we can use it meaningfully
                String name = parts[0].trim();
                String scores = parts[1].trim();
                String removeSpace = scores.replaceAll("\\s", "");
                String removeBracketsLeft = removeSpace.replaceAll("\\[", "");
                String removeBracketsRight = removeBracketsLeft.replaceAll("\\]", "");

                String[] replaceFix = removeBracketsRight.split(",");

                ArrayList<Integer> winsLossesTotals = new ArrayList<>();
                winsLossesTotals.add(Integer.valueOf(replaceFix[0]));
                winsLossesTotals.add(Integer.valueOf(replaceFix[1]));
                winsLossesTotals.add(Integer.valueOf(replaceFix[2]));


                //put name, age in HashMap if they are not empty
                if (!name.equals("") && !scores.equals(""))
                    leaderboard.put(name, winsLossesTotals);
            }

            try {
                buffStuffReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            try {
                FileWriter fw = new FileWriter("leaderboard.txt");
                BufferedWriter bf = new BufferedWriter(fw);
                bf.append("\nPlayer name\t\t Player Wins\t Player Losses\t Player Attempts\n");
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
        catch (IOException ioException) {
            ioException.printStackTrace();
        }
        return leaderboard;
    }


    public static void write(String player, int win, int loss) {
        HashMap<String, ArrayList<Integer>> scoreSheet = new HashMap<>();
        ArrayList<Integer> gatheredScores = new ArrayList<>();

        int wins = getWins(player);
        int losses = getLosses(player);
        gatheredScores.add(wins + win);
        gatheredScores.add(losses + loss);

        int attempts = getPlayedTotal(player);
        gatheredScores.add(attempts + 1);
        scoreSheet.put(player, gatheredScores);

        try{
            FileWriter file = new FileWriter(leaderboardFilePath, true);

            BufferedWriter buffStuffWriter = new BufferedWriter(file);
            for(Map.Entry<String, ArrayList<Integer>> entry : scoreSheet.entrySet()){
                buffStuffWriter.write( entry.getKey() + ":" + entry.getValue());
                buffStuffWriter.newLine();
            }

            buffStuffWriter.flush();
            buffStuffWriter.close();
            file.close();

        }
        catch(FileNotFoundException e) {
            try {
                FileWriter fw = new FileWriter("leaderboard.txt");
                BufferedWriter bf = new BufferedWriter(fw);
                bf.append("\nPlayer name\t\t Player Wins\t Player Losses\t Player Attempts\n");
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
        catch(IOException e) {
            e.printStackTrace();
        }
    }


    public static void read(){
        Map<String, ArrayList<Integer>> leaderboard = readLeaderboard();
        System.out.println(leaderboard);
    }
}