package main.dar.server;

import main.dar.server.exception.BadlyFormedHttpRequest;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Map;

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
        OutputStream output;
        try {
            inputStreamReader = new InputStreamReader(clientSocket.getInputStream());
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            output = new BufferedOutputStream(clientSocket.getOutputStream());
            System.out.println("Got a client !");

            HttpRequest request = HttpRequest.parse(bufferedReader);
            System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
            for (Map.Entry<String, String> c : request.getCookies().entrySet()) {
                System.out.println(c.getKey() + " " + c.getValue());
            }
            HttpResponse res = new HttpResponse();

            RouteBinding binding = router.match(request);
            if (binding != null) {
                HttpResponse handlerResponse = binding.process(request);
                if (handlerResponse != null)
                    res = handlerResponse;
            } else {
                File resource = router.getResource(request);
                if (resource != null) {
                    res.setStatusCode(200);
                    byte[] encoded = Files.readAllBytes(resource.toPath());

                    String[] parts = resource.getName().split("\\.");
                    String ext = parts[parts.length - 1];
                    switch (ext) {
                        case "js":
                            res.setContentType("application/js");
                            res.setBody(new String(encoded, StandardCharsets.UTF_8));
                            break;

                        case "css":
                            res.setContentType("text/css");
                            res.setBody(new String(encoded, StandardCharsets.UTF_8));
                            break;
                        case "jpg":
                            res.setContentType("image/jpeg");
                            res.setRawData(encoded);
                            break;

                    }

                }
            }

//            Cookie c = new Cookie("auth", request);
//            res.addHeader("Set-Cookie", "auth" + "=" + c.hashValue());
//            SessionManager.getInstance().addCookie(c);

            System.out.println(res.headingLine());
            System.out.println(res.headers());

            output.write(res.headingLine().getBytes());
            output.write(res.headers().getBytes());

            byte[] rawData = res.getRawData();
            if (rawData != null){
                output.write(rawData);
            }
            else {
                output.write(res.getBody().getBytes());
            }

            output.flush();
            output.close();

        } catch (IOException e) {
            e.printStackTrace();
            return;
        } catch (BadlyFormedHttpRequest badlyFormedHttpRequest) {
            badlyFormedHttpRequest.printStackTrace();
        }
//        catch (ParseException e) {
//            e.printStackTrace();
//        }

        try {
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
