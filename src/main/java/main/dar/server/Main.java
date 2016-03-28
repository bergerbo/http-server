package main.dar.server;

public class Main {

    public static void main(String[] args) throws Exception {

        Router.getInstance();

        new Server(Router.getInstance()).startServer();
    }
}
