package com.chzzkGamble.chzzk.chat;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import com.chzzkGamble.chzzk.api.ChzzkApiService;
import com.chzzkGamble.exception.ChzzkException;
import com.chzzkGamble.exception.ChzzkExceptionCode;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;

public class ChzzkWebSocketClient {

    private static final String CHZZK_CHAT_SERVER = "wss://kr-ss2.chat.naver.com/chat";

    private final WebSocketClient client = new StandardWebSocketClient();
    private final WebSocketHandler handler;
    private WebSocketSession session;

    public ChzzkWebSocketClient(ChzzkApiService apiService,
                                ApplicationEventPublisher publisher,
                                String channelId,
                                String channelName,
                                UUID gambleId) {
        this.handler = new ChzzkSessionHandler(apiService, publisher, channelId, channelName, gambleId);
    }

    public void connect() {
        try {
            session = client.execute(handler, CHZZK_CHAT_SERVER).get(30, TimeUnit.SECONDS);
        } catch (TimeoutException | ExecutionException | InterruptedException e) {
            throw new ChzzkException(ChzzkExceptionCode.CHAT_CONNECTION_ERROR, e.getMessage());
        }
    }

    public void disconnect() {
        if (session == null) {
            throw new ChzzkException(ChzzkExceptionCode.CHAT_IS_DISCONNECTED);
        }
        try {
            session.close();
            session = null;
        } catch (IOException e) {
            throw new ChzzkException(ChzzkExceptionCode.CHAT_DISCONNECTION_ERROR);
        }
    }

    public boolean isConnected() {
        return session == null || session.isOpen();
    }
}
