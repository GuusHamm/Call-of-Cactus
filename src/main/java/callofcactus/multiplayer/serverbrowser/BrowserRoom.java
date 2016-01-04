package callofcactus.multiplayer.serverbrowser;

/**
 * Created by Teun on 4-1-2016.
 */
public class BrowserRoom {

    private int id;
    private int hostid;
    private String name;
    private String hostip;

    public BrowserRoom(int id, int hostid, String name, String hostip) {
        this.id = id;
        this.hostid = hostid;
        this.hostip = hostip;
        this.name = name;
    }

    public BrowserRoom(int hostid, String name, String hostip) {
        this.hostid = hostid;
        this.hostip = hostip;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public int getHostid() {
        return hostid;
    }

    public String getName() {
        return name;
    }

    public String getHostip() {
        return hostip;
    }
}
