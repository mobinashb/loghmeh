package ie.projects.phase6.service.user;


import com.fasterxml.jackson.databind.ObjectMapper;
import ie.projects.phase6.domain.UserManager;
import ie.projects.phase6.domain.exceptions.DuplicateEmail;
import ie.projects.phase6.domain.exceptions.LoginFailure;
import ie.projects.phase6.domain.exceptions.NegativeCreditAmount;
import ie.projects.phase6.repository.user.UserDAO;
import ie.projects.phase6.service.user.request.AddCreditRequest;
import ie.projects.phase6.service.user.request.LoginRequest;
import ie.projects.phase6.service.user.request.SignupRequest;
import ie.projects.phase6.utilities.ConvertDAOToDTO;
import ie.projects.phase6.utilities.JsonStringCreator;
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
