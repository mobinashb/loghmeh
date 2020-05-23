package loghmeh.service.user;


import com.fasterxml.jackson.databind.ObjectMapper;
import loghmeh.domain.UserManager;
import loghmeh.domain.exceptions.DuplicateEmail;
import loghmeh.domain.exceptions.LoginFailure;
import loghmeh.domain.exceptions.NegativeCreditAmount;
import loghmeh.repository.user.UserDAO;
import loghmeh.service.user.request.AddCreditRequest;
import loghmeh.service.user.request.LoginRequest;
import loghmeh.service.user.request.SignupRequest;
import loghmeh.utilities.ConvertDAOToDTO;
import loghmeh.utilities.JsonStringCreator;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.sql.SQLException;

@RestController
public class UserService {
    ObjectMapper mapper = new ObjectMapper();

    @RequestMapping(value = "/v1/login", method = RequestMethod.POST, produces = "text/plain;charset=UTF-8")
    public String loginUser(@RequestBody LoginRequest request) throws SQLException, LoginFailure {
        return UserManager.getInstance().authenticateUser(request.getEmail(), request.getPassword(), request.getIsGoogleAuth(), request.getId_token());
    }

    @RequestMapping(value = "/v1/user", method = RequestMethod.POST, produces = "text/plain;charset=UTF-8")
    public String registerUser(@RequestBody SignupRequest request) throws DuplicateEmail, SQLException {
        UserManager.getInstance().registerUser(request.getFirstName(), request.getLastName(), request.getEmail(), request.getPassword());
        return JsonStringCreator.msgCreator("ثبت‌نام شما با موفقیت انجام شد");
    }

    @RequestMapping(value = "/v1/user", method = RequestMethod.GET, produces = "text/plain;charset=UTF-8")
    public String getUserProfile(@RequestAttribute("id") String userId) throws IOException, SQLException {
        UserDAO userDao = UserManager.getInstance().getUserById(userId);
        return mapper.writeValueAsString(ConvertDAOToDTO.userDAO_DTO(userDao));
    }

    @RequestMapping(value = "/v1/credit", method = RequestMethod.POST, produces = "text/plain;charset=UTF-8")
    public String addUserCredit(@RequestBody AddCreditRequest request, @RequestAttribute("id") String userId) throws IOException, NegativeCreditAmount, SQLException {
        UserManager.getInstance().addCredit(userId, request.getAmount());
        return mapper.writeValueAsString(JsonStringCreator.msgCreator("اعتبار شما با موفقیت افزایش یافت"));
    }

}
