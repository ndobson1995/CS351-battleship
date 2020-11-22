import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class LoginPortal extends JFrame implements ActionListener{

    private JPanel panel;
    private JLabel username_label, message;
    private JTextField username_text;
    private JButton submit;
    private Map<String, Boolean> loginMap = new HashMap<>();

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
    }

    protected void createPage() {
        setVisible(true);
    }

    /**
     * @return username entered into the text field
     */
    protected String playerNamePopulated() {
        return username_text.getText();
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
        Boolean value;
        createFile();
        value = checkDetailsAreValid(userName);
        if (!value) {
            message.setText(" Hello " + userName.trim().toUpperCase() + "");
            saveDetailsToFile(userName,true);
        }else{
            message.setText("This user is already active.");
        }
    }

    /**
     * This method handles the name taken from scanner input
     *
     * @param userName - username of player
     */
    protected boolean loginCLI(String userName) throws NullPointerException {
        Boolean value = null;
        createFile();
        value = checkDetailsAreValid(userName);
        if (!value){
            System.out.println(" Hello " + userName.trim().toUpperCase() + "");
            saveDetailsToFile(userName,true);
            System.out.println("Successful login.");
            return true;
        }else{
            System.out.println("This user is already active.");
            return false;
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
    private boolean checkDetailsAreValid(String playername) {
        loginMap = getHashMapFromFile();
        for(Map.Entry<String, Boolean> entry : loginMap.entrySet()) {
            if (loginMap.containsKey(playername)) {
                boolean value = loginMap.get(playername);
                if (value) {
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
    protected synchronized void saveDetailsToFile(String playername, boolean activePlayer) {
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
        Map<String, Boolean> mapFileContents = new HashMap<>();
        BufferedReader br = null;
        try{
            File file = new File(filePath);
            br = new BufferedReader( new FileReader(file) );
            String line;

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
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return mapFileContents;
    }


    /**
     * Method called when game has ended, searches hashmap for login data sets active flag to false
     * @param playername - name user entered
     */
    protected void setActivePlayerFlagToFalse(String playername) {
        loginMap = getHashMapFromFile();
        Map<String, Boolean> newLoginDataMap = new HashMap<>();
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