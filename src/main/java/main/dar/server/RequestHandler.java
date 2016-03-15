package main.dar.server;

import main.dar.server.exception.BadlyFormedHttpRequest;

import java.io.*;
import java.net.NoRouteToHostException;
import java.net.Socket;

/**
 * Created by hoboris on 3/7/16.
 */
class RequestHandler implements Runnable {
    private final Socket clientSocket;
    private Router router;

    RequestHandler(Socket clientSocket, Router router) {
        this.clientSocket = clientSocket;
        this.router = router;
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

            HttpRequest request = HttpRequest.parse(bufferedReader);


            RouteBinding binding = router.match(request);
            HttpResponse res = new HttpResponse("Internal Error", 500);
            if (binding != null) {
                HttpResponse handlerResponse = binding.process(request);
                if(handlerResponse != null)
                    res = handlerResponse;
            }

//            System.out.println(res.content());

            bufferedWriter.write(res.content());
            bufferedWriter.flush();
            bufferedWriter.close();

        } catch (IOException e) {
            e.printStackTrace();
            return;
        } catch (BadlyFormedHttpRequest badlyFormedHttpRequest) {
            badlyFormedHttpRequest.printStackTrace();
        }

        try {
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
