import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {

    private static InputStreamReader inputStreamReader;
    private static BufferedReader bufferedReader;
    private static BufferedWriter out;

    public static void main(String[] args) throws Exception {
        ServerSocket serverSocket = new ServerSocket(8082);
        Socket socket = serverSocket.accept();

        inputStreamReader = new InputStreamReader(socket.getInputStream());
        bufferedReader = new BufferedReader(inputStreamReader);
        while (true)
        {
            try
            {
                HttpRequest req = HttpRequest.parse(bufferedReader);

            }
            catch (IOException e)
            {
                //error ("System: " + "Connection to server lost!");
                System.exit (1);
                break;
            }
        }

        serverSocket.close();
    }

}
