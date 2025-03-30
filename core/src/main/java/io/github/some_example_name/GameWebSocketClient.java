package io.github.some_example_name;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;

public class GameWebSocketClient extends WebSocketClient {

    private Main game;

    public GameWebSocketClient(URI serverUri, Main game) {
        super(serverUri);
        this.game = game;
    }

    @Override
    public void onOpen(ServerHandshake handshake) {
        System.out.println("Connected to server.");
    }

    @Override
    public void onMessage(String message) {
        System.out.println("Message from server: " + message);
        game.handleServerMessage(message); // call back to game logic
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        System.out.println("Disconnected from server.");
    }

    @Override
    public void onError(Exception ex) {
        ex.printStackTrace();
    }
}
