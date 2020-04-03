package ie.projects.phase1.services.repeatedTasks;

import ie.projects.phase1.core.Loghmeh;

import java.util.TimerTask;

public class UpdatePartyRestaurants extends TimerTask{
    public void run() {
        Loghmeh.getInstance().addAllToLoghmeh("http://138.197.181.131:8080/foodparty", "party");
    }
}

