package callofcactus.multiplayer;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Teun on 13-12-2015.
 */
public class ServerCommandQueue {

    private final HashMap<Command, PrintWriter> commands = new HashMap<>();

    public void addCommand(Command c, PrintWriter printWriter) {
        synchronized (commands) {
            commands.put(c, printWriter);
        }
    }

    public Map.Entry<Command, PrintWriter> getNext() {
        Map.Entry<Command, PrintWriter> keypair = null;
        synchronized (commands) {
            if (!commands.entrySet().iterator().hasNext()) {
                return null;
            }

            keypair = commands.entrySet().iterator().next();
            commands.remove(keypair.getKey());
        }
        return keypair;
    }
}
