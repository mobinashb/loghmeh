package ie.projects.phase1.services.deliveries;

import com.fasterxml.jackson.databind.ObjectMapper;
import ie.projects.phase1.core.Loghmeh;
import ie.projects.phase1.core.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class DeliveryHandler {
    Loghmeh loghmeh = Loghmeh.getInstance();
    ObjectMapper mapper = new ObjectMapper();


    @RequestMapping(value = "/v1/carts", method = RequestMethod.GET, produces = "text/plain;charset=UTF-8")
    public String getAllCarts() throws IOException {
        User user = loghmeh.getUsers().get(0);
        return mapper.writeValueAsString(user.getAllOrders());
    }
}
