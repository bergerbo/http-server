import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {

    private static InputStreamReader inputStreamReader;
    private static BufferedReader bufferedReader;
    private static BufferedWriter out;

    public static void main(String[] args) throws Exception {
        ServerSocket serverSocket = new ServerSocket(8080);
        Socket socket = serverSocket.accept();

        inputStreamReader = new InputStreamReader(socket.getInputStream());
        bufferedReader = new BufferedReader(inputStreamReader);
        while (true)
        {
            String cominginText = "";
            try
            {
                cominginText = bufferedReader.readLine ();
                System.out.println (cominginText);

                HttpResponse res = new HttpResponse("yeaaa", "shit happens", 500);
                System.out.println(res.content());

                out = new BufferedWriter(
                        new OutputStreamWriter(
                                new BufferedOutputStream(socket.getOutputStream()), "UTF-8")
                );
                out.write(res.content());
                out.flush();
                out.close();
                socket.close();
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
