//import javax.swing.*;
//import java.awt.*;
//import java.awt.event.WindowAdapter;
//import java.awt.event.WindowEvent;
//
//public class Player {
//
//    private static JFrame main;
//    private JMenuItem quit, help;
//
//    public Player(){
//
//        mainGUI();
//        Board board = new Board();
//        board.print();
//        board.setLocation(500, 100);
//        board.setUndecorated(true);
//    }
//
//
//    public void mainGUI(){
//        main = new JFrame("BATTLESHIP");
//        main.setSize(1000,500);
//        main.setLayout(new GridLayout(1,2));
//        main.addWindowListener(new WindowAdapter() {
//            public void windowClosing(WindowEvent windowEvent){
//                System.exit(0);
//            }
//        });
//        JMenuBar menuBar = new JMenuBar();
//        JMenu menu = new JMenu("Menu");
//        quit = new JMenuItem("Quit");
//        help = new JMenuItem("Help");
//        quit.addActionListener(e -> {
//            if(e.getSource() == quit){
//                int result = JOptionPane.showConfirmDialog((Component) e.getSource(),
//                        "Close this application?");
//                if (result == JOptionPane.YES_OPTION) {
//                    System.exit(0);
//                } else if (result == JOptionPane.NO_OPTION) {
//                    System.out.println("Do nothing");
//                }
//            }
//        });
//
//        help.addActionListener(e -> {
//            if(e.getSource() == help)
//                JOptionPane.showMessageDialog(main,"Welcome");
//        });
//
//        menu.add(quit);
//        menu.add(help);
//        menuBar.add(menu);
//        main.setJMenuBar(menuBar);
//        main.setVisible(true);
//    }
//
//    public static void addToMain(JPanel add){
//        main.add(add);
//    }
//
//    public static JFrame returnMain(){
//        return main;
//    }
//}
