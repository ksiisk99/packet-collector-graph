package packet.collector.graph.websocket;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import packet.collector.graph.PacketService;

import java.util.Map;

@Component
public class WebSocketHandler extends TextWebSocketHandler {
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final PacketService packetService;

    public WebSocketHandler(PacketService packetService) {
        this.packetService = packetService;
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();

        Map<String, Integer> data = objectMapper.readValue(payload, Map.class);

        Integer networkInterfaceIndex = data.get("networkInterfaceIndex");
        Integer interval = data.get("interval");

        packetService.start(networkInterfaceIndex, interval, session);
    }
}
