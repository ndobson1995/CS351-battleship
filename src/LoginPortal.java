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
    HashMap<String, Boolean> loginMap = new HashMap<String, Boolean>();


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

        //todo there's currently only one player - how do i extend this to have more than one?

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String userName = username_text.getText();
        Boolean value = checkDetailsAreValid(userName);
        if (value==false){
            message.setText(" Hello " + userName.trim() + "");
            saveDetailsToFile(userName,true);
        }else{
            message.setText("This user is already active.");
            }
    }


    /**
     * This method will take in the details and check the file to make sure the player is not currently logged in
     */
    private boolean checkDetailsAreValid(String playername) {
        //BufferedWriter bf = null;
       // try{
            //bf = new BufferedReader(new FileReader("login-data.txt") );


        //todo IS THIS SEARCHING IN THE FILE OR THE HASHMAP?!!
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
//        } catch(FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    return false;
//    }

    /**
     * This method will save the details to the file
     */
    private void saveDetailsToFile(String playername,boolean activePlayer){
        loginMap.put(playername, activePlayer);
        //BufferedWriter bf = null;
        try{
            FileWriter fw = new FileWriter("login-data.txt",true);
            BufferedWriter bf = new BufferedWriter(fw);

            for(Map.Entry<String, Boolean> entry : loginMap.entrySet()){
                bf.append( entry.getKey() + ":" + entry.getValue() );
                bf.newLine();
            }
            bf.close();
        } catch(FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("debug --> here's the map of logins " + loginMap);


        // todo after you stop playing the game need to get back to the file and set active player to false
    }
}