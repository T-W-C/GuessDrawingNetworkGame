package registrationWithGui;

import java.io.ObjectOutputStream;
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
    private static Map<String, Set<ObjectOutputStream>> writers = new HashMap<>(); // hash map of all printers with the key being the type of client the writer is for
    static enum QueryType { REGISTRATION; }

    public Server(int port) {
        this.port = port;
    }

    public static Set<String> getChatNames() {
        return chatNames;
    }

    public static Map<String, Set<ObjectOutputStream>> getAllWriters() {
        return writers; // return the hashMap of writers
    }

    public static Set<ObjectOutputStream> getTypeOfWriters(String type) {
        return writers.get(type); // return the set of writers for a given type e.g. type "REGISTRATION"
    }

    public static void addWriter(String type, ObjectOutputStream os) {
        if(getTypeOfWriters(type) == null) { // checks to see if a set of a certain type of writers already exists
            writers.put(type, new HashSet<ObjectOutputStream>()); // if it doesnt create a new set in the hashMap
            getTypeOfWriters(type).add(os); // add the provided writer to it
        }
        else {
            getTypeOfWriters(type).add(os); // if set exists then add the provided writer to it
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
