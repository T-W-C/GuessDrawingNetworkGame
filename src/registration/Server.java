package registration;

import java.io.PrintWriter;
import java.net.ServerSocket;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    private static int port = 1254;
    private static Set<String> chatNames = new HashSet<>(); // set of all client names so we can check for dupes upon registration
    private static Map<String, Set<PrintWriter>> writers = new HashMap<>(); // hash map of all printers with the key being the type of client the writer is for

    public Server(int port) {
        this.port = port;
    }

    static Set<String> getChatNames() {
        return chatNames;
    }

    static Map<String, Set<PrintWriter>> getAllWriters() {
        return writers; // return the hashMap of writers
    }

    static Set<PrintWriter> getTypeOfWriters(String type) {
        return writers.get(type); // return the set of writers for a given type e.g. type "REGISTRATION"
    }

    static void addWriter(String type, PrintWriter pr) {
        if(getTypeOfWriters(type) == null) { // checks to see if a set of a certain type of writers already exists
            writers.put(type, new HashSet<PrintWriter>()); // if it doesnt create a new set in the hashMap
            getTypeOfWriters(type).add(pr); // add the provided writer to it
        }
        else {
            getTypeOfWriters(type).add(pr); // if set exists then add the provided writer to it
        }
    }

    public static void main(String[] args) throws Exception {
        System.out.println("The server is running..."); // running message

        ExecutorService pool = Executors.newFixedThreadPool(500); // thread pool for registration

        try(ServerSocket listener = new ServerSocket(port)) {
            while(true) {
                    pool.execute(new Handler(listener.accept())); // execute the runnable object
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }

        // shutdown thread pool once we are done
        pool.shutdown();
    }
}
