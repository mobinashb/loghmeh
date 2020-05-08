package ie.projects.phase7.domain.repeatedTasks;

import ie.projects.phase7.repository.finalizedCart.FinalizedCartRepository;

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
