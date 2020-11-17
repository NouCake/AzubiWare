package de.united.azubiware;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.net.URISyntaxException;

public class AzubiWareMain extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;
	private int x;
	@Override
	public void create () {
		batch = new SpriteBatch();
		img = new Texture("badlogic.jpg");
		try {

			WebSocketClient socket = new WebSocketClient(new URI("ws://localhost:13030")) {
				@Override
				public void onOpen(ServerHandshake handshakedata) {
					System.out.println("Connected");
				}

				@Override
				public void onMessage(String message) {
					System.out.println(message);
					try{
						int newX = Integer.parseInt(message);
						x = newX;
					} catch (NumberFormatException e){
						e.printStackTrace();
					}
				}

				@Override
				public void onClose(int code, String reason, boolean remote) {
					System.out.println("Closed");
				}

				@Override
				public void onError(Exception ex) {
					System.out.println("Error");
				};
			};
			socket.connect();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.draw(img, x, 0);
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}
}
