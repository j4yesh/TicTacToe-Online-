package com.j4yesh.tictactoe.Utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;

public class JsonUtil {
    public static TextMessage makePayload(Object data) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return new TextMessage(mapper.writeValueAsString(data));
    }
}
