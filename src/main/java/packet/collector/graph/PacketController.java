package packet.collector.graph;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class PacketController {
    private final PacketService packetService;

    public PacketController(PacketService packetService) {
        this.packetService = packetService;
    }

    @GetMapping("/monitor")
    public String graph(Model model) {
        model.addAttribute("networkInterfaces", packetService.getNetworkInterfaces());
        return "monitor";
    }
}
