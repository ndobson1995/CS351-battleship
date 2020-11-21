package ThreadMonitor;

import java.util.Scanner;

class ThreadMonitor {
    private static void getThreadData(ThreadGroup threadGroup) {
        int noThreads = threadGroup.activeCount();
        int noGroups = threadGroup.activeGroupCount();
        Thread[] threadList = new Thread[noThreads];
        ThreadGroup[] groupsList = new ThreadGroup[noGroups];

        threadGroup.enumerate(threadList);
        threadGroup.enumerate(groupsList);

        printThreadInfo(threadGroup,threadList, groupsList);
    }

    private static void printThreadInfo(ThreadGroup threadGroup, Thread[] threads, ThreadGroup[] groups) {
        System.out.println("\n" + "Thread Group: " + threadGroup.getName( ) + " Max Priority: " + threadGroup.getMaxPriority( ));

        for (Thread listThread: threads){
            System.out.println("Thread: " + listThread.getName() + "\n\t- Priority: " + listThread.getPriority() + "\n\t- State: " + listThread.getState() + "\n\t- Thread ID: " + listThread.getId());
        }
        for(ThreadGroup groupThreads : groups){
            getThreadData(groupThreads);
        }
    }


    public static void getRootThreadGroup() {
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

    public static void searchForThread(){
        Scanner scan = new Scanner(System.in);
        System.out.println("What Thread Name would you like to search for?");
        String option = scan.next();
        ThreadGroup main = Thread.currentThread().getThreadGroup().getParent();
        int num_threads = main.activeCount();
        Thread[] threads = new Thread[num_threads];

        main.enumerate(threads);

        for (Thread listThread: threads){
            if (listThread.getName().contains(option)) {
                System.out.println("Thread: " + listThread.getName() + " Priority: " + listThread.getPriority() + " State: " + listThread.getState() + " Thread ID: " + listThread.getId());
            }
        }
    }


    public static void deleteThread() throws InterruptedException {
        Scanner scan = new Scanner(System.in);
        System.out.println("What Thread Name would you like to terminate");
        String option = scan.next();
        ThreadGroup main = Thread.currentThread().getThreadGroup().getParent();
        int num_threads = main.activeCount();
        Thread[] threads = new Thread[num_threads];
        main.enumerate(threads);

        for (Thread listThread: threads){
            if (listThread.getName().contains(option)) {
                listThread.interrupt();
                listThread.wait(7000);
                System.out.println("Thread: " + listThread.getName() + "Has Terminated");
            }
        }
    }
}