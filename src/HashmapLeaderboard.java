import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * This controls the leaderboard. Writes a hashmap to a text file.
 */
public class HashmapLeaderboard {

    final static String leaderboardFilePath = "leaderboard.txt";

    /**
     * @return the wins
     */


    //todo take out later on - testing
    public static void main(String[] args) {
        read();

        write("sonja",3,4);
        write("willl",3,4);
        write("nic",3,4);
    }


    /**
     * gets number of wins from a player using the hashmap
     *
     * @param player
     * @return integer of total games won
     */
    public static int getWins(String player){
        Map<String, ArrayList<Integer>> leaderboard = readLeaderboard();
        int wins = 0;
        // if there isn't an entry for the player in the leaderboard, return 0.
        if(leaderboard.containsKey(player)){
            ArrayList<Integer> scores = leaderboard.get(player);
            wins = scores.get(0);
        }
        return wins;
    }

    /**
     * gets number of losses from a player using the hashmap
     *
     * @param player
     * @return integer of total games lost
     */
    public static int getLosses(String player){
        Map<String, ArrayList<Integer>> leaderboard = readLeaderboard();
        int losses = 0;
        // if there isn't an entry for the player in the leaderboard, return 0.
        if(leaderboard.containsKey(player)){
            ArrayList<Integer> scores = leaderboard.get(player);
            losses = scores.get(1);
        }
        return losses;
    }


    /**
     * gets number of total game played per user
     *
     * @param player
     * @return total number of games played
     */
    public static int getPlayedTotal(String player){
        Map<String, ArrayList<Integer>> leaderboard = readLeaderboard();
        int gamesPlayed = 0;
        // if there isn't an entry for the player in the leaderboard, return 0.
        if(leaderboard.containsKey(player)){
            ArrayList<Integer> scores = leaderboard.get(player);
            gamesPlayed = scores.get(2);
        }
        return gamesPlayed;
    }


    /**
     * method reads our leaderboard file and splits the data into useable variables
     * prints out the leaderboard displaying player name, wins, losses & total games played
     *
     * @return leaderboard
     */
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
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
            createFile();
        }
        catch (IOException ioException) {
            ioException.printStackTrace();
        }
        return leaderboard;
    }


    /**
     * Write to leaderboard.txt
     * @param player player being added
     * @param win if they won, this is a 1, if not, a 0
     * @param loss if they lost, this is a 1, if not, a 0
     */
    public synchronized static void write(String player, int win, int loss) {
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
            createFile();
        }
        catch(IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method creates the leaderboard file
     * Headers added to differentiate between data
     */
    private static void createFile(){
        FileWriter fw = null;
        try {
            fw = new FileWriter("leaderboard.txt",true);
            BufferedWriter buffStuffWriter = new BufferedWriter(fw);
            buffStuffWriter.append("\nPlayer name\t\t Player Wins\t Player Losses\t Player Attempts\n");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static Map<String, ArrayList<Integer>> read(){
        return readLeaderboard();
    }

    public static void print(){
        Map<String, ArrayList<Integer>> leaderboard = read();
        for (Map.Entry<String, ArrayList<Integer>> entry : leaderboard.entrySet()) {
            System.out.print(entry.getKey()+" | ");
            for(int user : entry.getValue()){
                System.out.print("\t"+ user+"  \t");
            }
            System.out.println();
        }
    }
}