package packet.collector.graph.controller;

import org.ksi.NetworkInterfaceInfo;
import org.ksi.NetworkTrafficCollector;
import org.ksi.Pcap4jPacketHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class PacketController {
    private final NetworkTrafficCollector networkTrafficCollector;

    public PacketController() {
        this.networkTrafficCollector = new NetworkTrafficCollector(new Pcap4jPacketHandler());
    }

    @GetMapping("/home")
    public String home(Model model) {
        List<NetworkInterfaceInfo> networkInterfaceInfos = networkTrafficCollector.getNetworkInterfaces();
        model.addAttribute("networkInterfaceInfos", networkInterfaceInfos);
        return "home";
    }

}
