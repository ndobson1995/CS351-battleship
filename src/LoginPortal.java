import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class LoginPortal extends JFrame implements ActionListener{

    Scanner scanner = new Scanner(System.in);
    JPanel panel;
    JLabel username_label, message;
    JTextField username_text;
    JButton submit;


    public static void main(String[] args) {
        new LoginPortal();
    }

    LoginPortal (){
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
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        submit.addActionListener(this);
        add(panel, BorderLayout.CENTER);
        setTitle("LOGIN PORTAL");
        setSize(400,250);
        setVisible(true);
        //there's currently only one player - how do i extend this to have more than one?

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        String userName = username_text.getText();
        if (userName.trim().equals(userName)) {
            message.setText(" Hello " + userName + "");
            saveDetailsToFile(userName,true);
        } else {
            message.setText(" Invalid user.. ");
        }
    }


    /**
     * This method will take in the details and check the file to make sure the player is not currently logged in
     */
    private void checkDetailsAreValid(String playerOneName) {
        //check the json file to see if player name is found AND active player = true then return to method, "that player is already active"
//        if(playerName.equals(userName) && activePlayer.equals(true) ){
//            System.out.println("Login Unsuccessful. This user is already active");
//        }


//        else{
//            //if the player name does not exist then
//            saveDetailsToFile(playerOneName, true);
//            //System.out.println("Login Successful.");
//            //save details to the json file
//        }


    }
    /**
     * This method will save the details to the file
     */
    private void saveDetailsToFile(String playername,boolean activePlayer){
        //final String outputFilePath = "/login-data.txt";
        File creatingFile = new File("/login-data.txt");
        HashMap<String, Boolean> loginMap = new HashMap<String, Boolean>();
        loginMap.put("testusername one", true);
        loginMap.put("testusername two", true);
        //loginMap.put(playername, true);

        BufferedWriter bf = null;
        try{
            bf = new BufferedWriter(new FileWriter("login-data.txt") );
            for(Map.Entry<String, Boolean> entry : loginMap.entrySet()){
                bf.write( entry.getKey() + ":" + entry.getValue() );
                bf.newLine();
            }
            bf.flush();
        } catch(FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }



        System.out.println("heres the map of logins "+loginMap);
        //todo search the file for hashmap key with the same username and check if the active plyer is false


        //if its valid then set this in the hashmap and file
        loginMap.put(playername, activePlayer);



//        HashMap<String, Boolean> toggle = new HashMap<String, Boolean>();
//        toggle.put(playername, true);
//        if(toggle.get(playername) == Boolean.TRUE){
//            toggle.put(playername, false);
//        }


        System.out.println("Login Successful.");

//        File file = new File("/data.txt");
//        BufferedWriter bf = null;
//        try{
//            bf = new BufferedWriter( new FileWriter(file) );
//            //iterate map entries
//            for(Map.Entry<String, Boolean> entry : loginMap.entrySet()){
//
//                //put key and value separated by a colon
//                bf.write( entry.getKey() + ":" + entry.getValue() );
//
//                bf.newLine();
//            }
//            bf.flush();
//        }
//        catch(IOException e){
//        e.printStackTrace();
//        }

        // todo after you stop playing the game need to get back to the file and set active player to false
    }
}