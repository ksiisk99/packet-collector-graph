package packet.collector.graph;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.ksi.*;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PacketService {
    private final NetworkTrafficCollector networkTrafficCollector = new NetworkTrafficCollector(new Pcap4jPacketHandler());
    private final ObjectMapper objectMapper = new ObjectMapper();

    public void start(int selectedNic, int time, WebSocketSession session) {
        networkTrafficCollector.collect(networkTraffic -> {
            try {
                String jsonResponse = objectMapper.writeValueAsString(networkTraffic);
                session.sendMessage(new TextMessage(jsonResponse));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }, selectedNic, time);
    }

    public List<String> getNetworkInterfaces() {
        return networkTrafficCollector.getNetworkInterfaces().stream()
                .map(NetworkInterfaceInfo::toString)
                .collect(Collectors.toList());
    }

    public void stop() {
        networkTrafficCollector.shutdown();
    }
}
