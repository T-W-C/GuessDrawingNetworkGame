package registration;

import java.io.PrintWriter;
import java.net.ServerSocket;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    private static int port = 1254;
    private static Set<String> chatNames = new HashSet<>(); // set of all client names so we can check for dupes upon registration
    private static Set<PrintWriter> chatWriters = new HashSet<>(); // set of all print writers for each client in the chat thread pool

    public Server(int port) {
        this.port = port;
    }

    static Set<String> getChatNames() {
        return chatNames;
    }

    static Set<PrintWriter> getChatWriters() {
        return chatWriters;
    }

    static void addChatWriter(PrintWriter pr) {
        chatWriters.add(pr);
    }

    public static void main(String[] args) throws Exception {
        System.out.println("The server is running..."); // running message

        ExecutorService pool = Executors.newFixedThreadPool(500); // thread pool for registration

        try(ServerSocket listener = new ServerSocket(port)) {
            while(true) {
                    pool.execute(new Handler(listener.accept()));
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }

        // shutdown thread pool once we are done
        pool.shutdown();
    }
}
