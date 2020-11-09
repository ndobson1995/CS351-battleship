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


    public static void main(String[] args) {
        new LoginPortal();
    }

    LoginPortal (){
        username_label = new JLabel();
        username_label.setText("    User Name :");
        username_text = new JTextField();
        //username_text = new JTextField(Playername);

        panel = new JPanel(new GridLayout(2, 1));
        //panel.add(username_label);
        panel.add(username_label);
        panel.add(username_text);

        submit = new JButton("SUBMIT");
        message = new JLabel();
        panel.add(message);
        panel.add(submit);

        submit.addActionListener(this);
        add(panel, BorderLayout.CENTER);
        setTitle("LOGIN PORTAL");
        setSize(400,250);
        setVisible(true);

        //todo there's currently only one player - how do i extend this to have more than one?

        System.out.println("PLAYER NAME POPULATED IS " + username_text.toString());
    }

    public String playerNamePopulated() {
        String playernameToReturn = username_text.toString();
        System.out.println(playernameToReturn);
        return playernameToReturn;

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String userName = username_text.getText();
        Boolean value = null;
        try {
            value = checkDetailsAreValid(userName);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        if (value==false){
            message.setText(" Hello " + userName.trim().toUpperCase() + "");
            saveDetailsToFile(userName,true);
        }else{
            message.setText("This user is already active.");
            }
    }

    /**
     * This method will take in the details and check the file to make sure the player is not currently logged in
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
     * This method will save the details to the file
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
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //System.out.println("debug --> here's the map of logins " + loginMap);
    }


    private Map<String, Boolean> getHashMapFromFile() throws IOException {
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
                //System.out.println(player_name+":"+activeflag);
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

    public void setActivePlayerFlagToFalse() {

        //todo search the map keys using the playername variable and change the value to false



    }
}