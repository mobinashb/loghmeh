package ie.projects.phase6.utilities;

public class JsonStringCreator {
    public static String msgCreator(String msg){
        return "{\"msg\": " + "\"" + msg + "\"}";
    }
}
