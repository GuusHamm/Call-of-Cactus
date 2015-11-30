package callofcactus.multiplayer;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by Wouter Vanmulken on 29-11-2015.
 */
public class Command {

    public enum methods{
        GET, POST, CHANGE
    }

    methods method;
    List<Object[]> objects;


    public Command(methods method, List<Object[]> objectsToModify)
    {
        this.method = method;
        this.objects = objectsToModify;
    }
//TODO better implementation of object toString
    @Override
    public String toString() {
        return method.toString() + "," + objects;
    }

    public static Command fromString(String input)
    {
        String[] inputArray = input.split(",");
        List<Object[]>  k = new ArrayList<>();

        for(int i=1;i<inputArray.length;i++)
        {
            k.add(new Object[]{Integer.parseInt(inputArray[i]),inputArray[i++],inputArray[i++]});
        }
        return new Command(methods.valueOf(inputArray[0]),k);
    }
}
