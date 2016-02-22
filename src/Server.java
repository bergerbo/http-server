import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by iShavgula on 16/02/16.
 */
public class Server {
    public void startServer() {
        final ExecutorService clientProcessingPool = Executors.newFixedThreadPool(10);

        Runnable serverTask = () -> {
            try {
                ServerSocket serverSocket = new ServerSocket(8080);
                System.out.println("Waiting for clients to connect...");
                while (true) {
                    Socket clientSocket = serverSocket.accept();
                    clientProcessingPool.submit(new ClientTask(clientSocket));
                }
            } catch (IOException e) {
                System.err.println("Unable to process client request");
                e.printStackTrace();
            }
        };
        Thread serverThread = new Thread(serverTask);
        serverThread.start();

    }

    private class ClientTask implements Runnable {
        private final Socket clientSocket;

        private ClientTask(Socket clientSocket) {
            this.clientSocket = clientSocket;
        }

        @Override
        public void run() {
            InputStreamReader inputStreamReader = null;
            BufferedWriter bufferedWriter;
            try {
                inputStreamReader = new InputStreamReader(clientSocket.getInputStream());
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                bufferedWriter = new BufferedWriter(
                        new OutputStreamWriter(
                                new BufferedOutputStream(clientSocket.getOutputStream()), "UTF-8")
                );
                System.out.println("Got a client !");

                // TODO: Parse the request and send corresponding response
                HttpResponse res = new HttpResponse("yeaaa", "shit happens", 500);
                System.out.println(res.content());

                bufferedWriter.write(res.content());
                bufferedWriter.flush();
                bufferedWriter.close();

            } catch (IOException e) {
                e.printStackTrace();
                return;
            }

            try {
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
