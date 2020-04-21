package ie.projects.phase6.domain.repeatedTasks;

import ie.projects.phase6.domain.exceptions.RestaurantNotFound;
import ie.projects.phase6.repository.finalizedCart.FinalizedCartRepository;

import java.sql.SQLException;
import java.util.TimerTask;

public class CheckOrderStatus extends TimerTask {
    public void run() {
        try {
            FinalizedCartRepository.getInstance().checkOrdersStates();
        }
        catch (SQLException e1){
            System.out.println("Can't check orders status");
            e1.printStackTrace();
        }
    }
}
