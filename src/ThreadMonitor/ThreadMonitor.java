/**
 * CS351 Programming Assessment - Thread Monitoring System
 * Brief - This is the class that handles getting all active threads and displays them
 * as well functionality for searching and terminating a thread.
 * References - https://learning.oreilly.com/library/view/java-examples-in/0596006209/ch04s03.html
 * used this link as a base line for getting the threads and using the enumerate method.
 * Authors : Group 4
 */
package ThreadMonitor;
import java.util.Scanner;

class ThreadMonitor {

    /**
     * This method returns a list of all active threads.
     * @param threadGroup = passes in a parent thread-group
     * @return a list of threads - threadList
     */
    private static Thread[] getActiveThreads(ThreadGroup threadGroup){
        int noThreads = threadGroup.activeCount();
        Thread[] threadList = new Thread[noThreads];
        threadGroup.enumerate(threadList);

        return threadList;
    }

    /**
     * This method will return the a list of the active thread-groups.
     * @param threadGroup = takes in the parent thread-group
     * @return a list of thread-groups
     */
    private static ThreadGroup[] getActiveThreadGroups(ThreadGroup threadGroup){
        int noGroups = threadGroup.activeGroupCount();
        ThreadGroup[] threadGroupList = new ThreadGroup[noGroups];
        threadGroup.enumerate(threadGroupList);
        return threadGroupList;
    }

    /**
     * This method takes in the the parent thread, and using that to get all the threads and all the thread-groups.
     * I use the enumerate method to store all the active threads and thread-groups to an array
     * I pass the array lists into the printThreadInto() method to print the threads.
     * @param threadGroup = the parent thread-group
     */
    public static void getThreadData(ThreadGroup threadGroup) {
        Thread [] threadLists = getActiveThreads(threadGroup);
        ThreadGroup[] groupLists = getActiveThreadGroups(threadGroup);
        printThreadInfo(threadGroup,threadLists, groupLists);
    }


    /**
     * This method handles printing the thread-groups and the threads associated with that thread group.
     * @param threadGroup = takes in the thread-groups
     * @param threads = passes in the list of threads
     * @param groups = passes in the list of group threads.
     */
     public static void printThreadInfo(ThreadGroup threadGroup, Thread[] threads, ThreadGroup[] groups) {
        System.out.println("\n" + "Thread Group: " + threadGroup.getName( ) + " Max Priority: " + threadGroup.getMaxPriority( ));
        for (Thread listThread: threads){
            System.out.println("Thread: " + listThread.getName() + "\n\t- Priority: " + listThread.getPriority() + "\n\t- State: " + listThread.getState() + "\n\t- Thread ID: " + listThread.getId());
        }
        for(ThreadGroup groupThreads : groups){
            getThreadData(groupThreads);
        }
    }


    /**
     * The method gets the parent of the thread-groups.
     * The thread groups gets passed into another method called getThreadData().
     */
    static void getRootThreadGroup() {
        ThreadGroup currentThread = Thread.currentThread().getThreadGroup().getParent();
        ThreadGroup rootThread = null;

        if (currentThread.getParent() == null){
            rootThread = currentThread;
        } else {
            System.out.println("Not main thread: " + currentThread.getName());
        }
        if (rootThread != null) {
            getThreadData(rootThread);
        }
    }


    /**
     *This method allows the user to search for an active thread that's running.
     * It calls the method getActiveThreaads to get a list of the threads,
     * it then does a search for the thread.
     */
    static void searchForThread(){
        Scanner scan = new Scanner(System.in);
        System.out.println("What Thread Name would you like to search for? ");
        String option = scan.next();

        ThreadGroup main = Thread.currentThread().getThreadGroup().getParent();
        Thread[] thread = getActiveThreads(main);

        for (Thread listThread: thread){
            if (listThread.getName().contains(option)) {
                System.out.println("Thread: " + listThread.getName() + " Priority: " + listThread.getPriority() + " State: " + listThread.getState() + " Thread ID: " + listThread.getId());
            }
        }
    }

    /**
     *This method allows the user to search for the thread they wish to terminate.
     *It calls the method getActiveThreaads to get a list of the threads,
     * it then does a search for the thread and then terminates the thread.
     */
    static void deleteThread() {
        Scanner scan = new Scanner(System.in);
        System.out.println("What Thread Name would you like to terminate? ");
        String option = scan.next();

        ThreadGroup main = Thread.currentThread().getThreadGroup().getParent();
        Thread[] thread = getActiveThreads(main);

        for (Thread listThread: thread){
            if (listThread.getName().contains(option)) {
                listThread.interrupt();
                System.out.println("Thread: " + listThread.getName() + " Has Terminated");
            }
        }
    }
}