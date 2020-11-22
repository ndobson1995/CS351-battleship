/**
 * CS351 Programming Assessment - Thread Monitoring System
 * Brief - This is the class that handles the main menu for the thread monitoring systems.
 * Authors : Group 4
 */
package ThreadMonitor;
import java.util.Scanner;

class Main implements Runnable {
    @Override
    public void run() {
        int counter = 0;
        while(counter < 5) {
            ThreadMonitor.getRootThreadGroup();
            try {
                counter++;
                Thread.sleep(60000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Returning to main menu");
    }


    /**
     * The methon run the main menu for interacting with the threads.
     * @param args
     */
    public static void main(String[] args) {
        Main main = new Main();

        Scanner scan = new Scanner(System.in);
        int option;

        while (true) {
            System.out.println("Thread Monitoring System");
            System.out.println("------------------------");
            System.out.println("Please enter your choice");
            System.out.println("1: List all Threads");
            System.out.println("2: Search Thread Name");
            System.out.println("3: Terminate Thread");
            System.out.println("4: Exit Application");

            String choiceStr = scan.nextLine();
            if (choiceStr.matches("[0-9][0-9]*")) {
                option = Integer.parseInt(choiceStr);
                switch (option) {
                    case 1:
                        Thread thread1 = new Thread(main);
                        thread1.start();
                        break;
                    case 2:
                        ThreadMonitor.searchForThread();
                        break;
                    case 3:
                        ThreadMonitor.deleteThread();
                        break;
                    case 4:
                        System.out.println("Exiting..");
                        System.exit(0);
                        break;
                    default:
                        System.out.println("Enter a relevant number...");
                }
            }
            else{
                System.err.println("Please enter a number\n");
            }
        }
    }


}
