import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class LoginPortal extends JFrame implements ActionListener{

    JPanel panel;
    JLabel username_label, message;
    JTextField username_text;
    JButton submit;
    Map<String, Boolean> loginMap = new HashMap<String, Boolean>();


    /**
     * Runs the login gui interface
     *
     * @param args - normal arguments
     */
    public static void main(String[] args) {
        new LoginPortal();
    }

    /**
     * Creates the login gui interface
     * Prompts for user to enter their name
     */
    LoginPortal () {
        username_label = new JLabel();
        username_label.setText("    User Name :");
        username_text = new JTextField();

        panel = new JPanel(new GridLayout(2, 1));
        panel.add(username_label);
        panel.add(username_text);

        submit = new JButton("SUBMIT");
        message = new JLabel();
        panel.add(message);
        panel.add(submit);

        submit.addActionListener(this);
        add(panel, BorderLayout.CENTER);
        setTitle("BATTLESHIP LOGIN PORTAL");
        setSize(400,250);
        setVisible(true);
    }

    /**
     * @return username entered into the text field
     */
    public String playerNamePopulated() {
        String playernameToReturn = username_text.getText();
        return playernameToReturn;

    }


    /**
     * Trys to create login portal file if username is entered & valid
     * Catches already active players and prompts player to use a different username
     *
     * @param action - java param
     */
    @Override
    public void actionPerformed(ActionEvent action) {
        String userName = username_text.getText();
        Boolean value = null;
        try {
            createFile();
            value = checkDetailsAreValid(userName);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        if (value==false){
            message.setText(" Hello " + userName.trim().toUpperCase() + "");
            saveDetailsToFile(userName,true);
        }else{
            message.setText("This user is already active.");
            System.exit(0);
        }
    }

    /**
     * This method handles the name taken from scanner input
     *
     * @param name - username of player
     */
    public void loginCLI(String name) {
        String userName = name;
        Boolean value = null;
        try {
            createFile();
            value = checkDetailsAreValid(userName);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        //TODO TALK WITH SONJA DUPLICATED CODE IN ABOVE METHOD actionPerformed
        if (value==false){
            System.out.println(" Hello " + userName.trim().toUpperCase() + "");
            saveDetailsToFile(userName,true);
            System.out.println("Successful login.");
        }else{
            System.out.println("This user is already active.");
            System.exit(0);
        }
    }


    /**
     * File creation method
     */
    private void createFile() {
        try {
            FileWriter fw = new FileWriter("login-data.txt", true);
            BufferedWriter bf = new BufferedWriter(fw);
        }catch(Exception e){
            e.printStackTrace();
        }
    }


    /**
     * This method will take in the details and check the file to make sure the player is not currently logged in.
     *
     * @param playername - name user has entered
     * @return - boolean dependent on if player name is active or not
     */
    private boolean checkDetailsAreValid(String playername) throws IOException {
        loginMap = getHashMapFromFile();
        for(Map.Entry<String, Boolean> entry : loginMap.entrySet()) {
            if (loginMap.containsKey(playername)) {
                boolean value = loginMap.get(playername);
                if (value == true) {
                    message.setText("You are already active");
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        }
        return false;
    }


    /**
     * This method will save the details(Player name & Active Player Status to the file
     *
     * @param playername - name user has entered
     * @param activePlayer - true or false value if player is currenly logged on or not
     */
    public void saveDetailsToFile(String playername,boolean activePlayer) {
        loginMap.put(playername, activePlayer);
        try {
            FileWriter fw = new FileWriter("login-data.txt", true);
            BufferedWriter bf = new BufferedWriter(fw);

            for (Map.Entry<String, Boolean> entry : loginMap.entrySet()) {
                bf.append(entry.getKey()).append(":").append(String.valueOf(entry.getValue()));
                bf.newLine();
            }
            bf.close();
        } catch (FileNotFoundException e) {
            createFile();
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method will retrieve all the key,value pairs stored in the login file to compare against
     *
     * @return mapFileContents - new hashmap of name and active/not-active flag
     */
    private Map<String, Boolean> getHashMapFromFile() {
        String filePath = "login-data.txt";
        Map<String, Boolean> mapFileContents = new HashMap<String, Boolean>();
        BufferedReader br = null;
        try{
            File file = new File(filePath);
            br = new BufferedReader( new FileReader(file) );
            String line = null;

            while ( (line = br.readLine()) != null ){
                String[] parts = line.split(":");
                String player_name = parts[0].trim();

                Boolean activeflag = Boolean.parseBoolean( parts[1].trim() );
                mapFileContents.put(player_name,activeflag);
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            if(br != null){
                try {
                    br.close();
                }catch(Exception e){};
            }
        }
        return mapFileContents;
    }


    /**
     * Method called when game has ended, searches hashmap for login data sets active flag to false
     * @param playername - name user entered
     */
    public void setActivePlayerFlagToFalse(String playername) {
        loginMap = getHashMapFromFile();
        Map<String, Boolean> newLoginDataMap = new HashMap<String, Boolean>();
        try {
            FileWriter fw = new FileWriter("login-data.txt", true);
            BufferedWriter bf = new BufferedWriter(fw);

            for(Map.Entry<String, Boolean> entry : loginMap.entrySet()) {
                if (loginMap.containsKey(playername)) {
                    newLoginDataMap.put(playername, false);
                    for (Map.Entry<String, Boolean> newEntry : newLoginDataMap.entrySet()) {
                        bf.append(newEntry.getKey()).append(":").append(String.valueOf(newEntry.getValue()));
                        bf.newLine();
                    }
                }
            }
            bf.close();
        } catch (FileNotFoundException e) {
            createFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}