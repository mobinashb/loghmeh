package ie.projects.phase1.services.carts;

import com.fasterxml.jackson.databind.ObjectMapper;
import ie.projects.phase1.core.Loghmeh;
import ie.projects.phase1.core.User;
import ie.projects.phase1.services.repeatedTasks.CheckOrderStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Timer;

@RestController
public class CartHandler {
    Loghmeh loghmeh = Loghmeh.getInstance();
    ObjectMapper mapper = new ObjectMapper();


    @RequestMapping(value = "/v1/cart", method = RequestMethod.POST, produces = "text/plain;charset=UTF-8")
    public String addOrder(@RequestBody CartRequest request) throws IOException {
        return "{\"msg\": " + "\"" + loghmeh.getUsers().get(0).addToCart(request.getFoodName(), request.getNumber(), request.getRestaurantId(), request.isParty()) + "\"}";
    }

    @RequestMapping(value = "/v1/cart", method = RequestMethod.GET, produces = "text/plain;charset=UTF-8")
    public String getcart() throws IOException{
        User user = loghmeh.getUsers().get(0);
        return mapper.writeValueAsString(user.getCart());
    }

    @RequestMapping(value = "/v1/cart", method = RequestMethod.PUT, produces = "text/plain;charset=UTF-8")
    public String editCart(@RequestBody CartRequest request) throws IOException{
        return "{\"msg\": " + "\"" + loghmeh.getUsers().get(0).getCart().editCart(request.getFoodName(), request.getNumber(), request.isParty()) + "\"}";
    }

//    @RequestMapping(value = "/v1/cart", method = RequestMethod.DELETE)
//    public String editCart(@RequestBody CartRequest request) throws IOException{
//        hnooz moonde
//    }
    @RequestMapping(value = "/v1/cart/finalize", method = RequestMethod.GET, produces = "text/plain;charset=UTF-8")
    public String finalizeOrder(){
        User user = loghmeh.getUsers().get(0);
        String result = user.finalizeOrder();
        if(result == "") {
            Timer timer = new Timer();
            timer.schedule(new CheckOrderStatus(), 0, 2000);
            return "{\"msg\": " + "\"" + "Your order registered successfully" + "\"}";
        }
        return "{\"msg\": " + "\"" + result  + "\"}";

    }
}
