package io.github.some_example_name;

import com.badlogic.gdx.Gdx;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;

public class GameClient extends WebSocketClient {

    private final Main game;

    public GameClient(URI serverUri, Main game) {
        super(serverUri);
        this.game = game;
    }

    @Override
    public void onOpen(ServerHandshake handshake) {
        System.out.println("Connected to WebSocket server");
    }

    @Override
    public void onMessage(String message) {
        Gdx.app.postRunnable(() -> game.handleServerMessage(message));
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        System.out.println("Disconnected: " + reason);
    }

    @Override
    public void onError(Exception ex) {
        ex.printStackTrace();
    }
}
