package ie.projects.phase1.server.users;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import ie.projects.phase1.core.Loghmeh;
import ie.projects.phase1.core.User;
import ie.projects.phase1.exceptions.NegativeCreditAmount;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
public class UserHandler {
    Loghmeh loghmeh = Loghmeh.getInstance();
    ObjectMapper mapper = new ObjectMapper();
    String[] userFilterParams = {"location", "credit", "orders", "undeliveredOrders", "cart", "orderId"};
    FilterProvider filter = new SimpleFilterProvider().addFilter("user", SimpleBeanPropertyFilter.serializeAllExcept(userFilterParams));
    ObjectWriter writer = mapper.writer(filter);

    @RequestMapping(value = "/v1/profile", method = RequestMethod.GET, produces = "text/plain;charset=UTF-8")
    public String getUserProfile() throws IOException {
        User user = loghmeh.getUsers().get(0);
        return writer.writeValueAsString(user);
    }

    @RequestMapping(value = "/v1/credit", method = RequestMethod.POST, produces = "text/plain;charset=UTF-8")
    public String getRestaurant(@RequestBody AddCreditRequest request) throws IOException, NegativeCreditAmount {
        User user = loghmeh.getUsers().get(0);
        user.addCredit(request.getAmount());
        return writer.writeValueAsString(user);
    }
}
