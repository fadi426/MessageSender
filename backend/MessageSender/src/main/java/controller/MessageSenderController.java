package controller;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import model.Message;
import model.MessageMixIn;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
public class MessageSenderController {

//    @Autowired
//    private MessageSenderService messageSenderService;

    @CrossOrigin(origins = "http://localhost:8081")
    @RequestMapping(method = RequestMethod.POST, value = "/rest")
    public Message RestMessage(@RequestBody String message) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES);
        mapper.addMixIn(Message.class, MessageMixIn.class);

        Message ms = mapper.readValue(message, Message.class);
        System.out.println(ms.getName() + " " + ms.getContent() + " " + ms.getStartTime());

        return ms;
    }

    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public Message message(String message) throws Exception {

        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES);
        mapper.addMixIn(Message.class, MessageMixIn.class);

        Message ms = mapper.readValue(message, Message.class);
        System.out.println(ms.getName() + " " + ms.getContent() + " " + ms.getStartTime());

        return ms;
    }
}
