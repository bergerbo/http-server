import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {

    public static void main(String[] args) throws Exception {
        ServerSocket serverSocket = new ServerSocket(8080);
        Socket socket = serverSocket.accept();

        BufferedReader in = new BufferedReader (new InputStreamReader(socket.getInputStream ()));
        while (true)
        {
            String cominginText = "";
            try
            {
                cominginText = in.readLine ();
                System.out.println (cominginText);
            }
            catch (IOException e)
            {
                //error ("System: " + "Connection to server lost!");
                System.exit (1);
                break;
            }
        }
    }

}
