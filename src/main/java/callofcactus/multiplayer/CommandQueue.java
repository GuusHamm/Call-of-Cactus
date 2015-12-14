package callofcactus.multiplayer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Teun on 13-12-2015.
 */
public class CommandQueue {

    private final List<Command> queue;

    public CommandQueue() {
        this.queue = new ArrayList<>();
    }

    public Command getNext() {
        Command c = null;
        if (queue.size() > 0) {
            synchronized (queue) {
                c = queue.get(0);
                queue.remove(c);
            }
        }
        return c;
    }

    public void addCommand(Command c) {
        synchronized (queue) {
            queue.add(c);
        }
    }
}