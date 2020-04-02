package ie.projects.phase1.services.repeatedTasks;

import ie.projects.phase1.core.Loghmeh;

import java.util.TimerTask;

public class CheckOrderStatus extends TimerTask {
    public void run() {
        Loghmeh.getInstance().getUsers().get(0).checkStates();
    }
}
