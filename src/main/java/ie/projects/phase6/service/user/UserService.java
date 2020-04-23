package ie.projects.phase6.service.user;


import com.fasterxml.jackson.databind.ObjectMapper;
import ie.projects.phase6.domain.UserManager;
import ie.projects.phase6.domain.exceptions.NegativeCreditAmount;
import ie.projects.phase6.repository.user.UserDAO;
import ie.projects.phase6.utilities.ConvertDAOToDTO;
import ie.projects.phase6.utilities.JsonStringCreator;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.sql.SQLException;

@RestController
public class UserService {
    ObjectMapper mapper = new ObjectMapper();

    @RequestMapping(value = "/v1/profile", method = RequestMethod.GET, produces = "text/plain;charset=UTF-8")
    public String getUserProfile() throws IOException, SQLException {
        UserDAO userDao = UserManager.getInstance().getUserById("123456789123");
        return mapper.writeValueAsString(ConvertDAOToDTO.userDAO_DTO(userDao));
    }

    @RequestMapping(value = "/v1/credit", method = RequestMethod.POST, produces = "text/plain;charset=UTF-8")
    public String addUserCredit(@RequestBody AddCreditRequest request) throws IOException, NegativeCreditAmount, SQLException {
        UserManager.getInstance().addCredit("123456789123", request.getAmount());
        return mapper.writeValueAsString(JsonStringCreator.msgCreator("اعتبار شما با موفقیت افزایش یافت"));
    }
}
