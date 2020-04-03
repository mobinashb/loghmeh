package ie.projects.phase1.services.deliveries;

import com.fasterxml.jackson.databind.ObjectMapper;
import ie.projects.phase1.core.Loghmeh;
import ie.projects.phase1.core.User;
import ie.projects.phase1.services.carts.CartRequest;
import org.springframework.web.bind.annotation.RequestBody;
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
        System.out.println("++++++++++++++++++++++++++++++++++++");
        System.out.println(user.getAllOrders().size());
        System.out.println(mapper.writeValueAsString(user.getAllOrders()));
        return mapper.writeValueAsString(user.getAllOrders());
    }
}
