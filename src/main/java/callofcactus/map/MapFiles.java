package callofcactus.map;

/**
 * Created by Teun on 21-11-2015.
 */
public class MapFiles {

    public static final String COMPLICATEDMAP = "complicatedmap.tmx";

    public enum MAPS {
        COMPLICATEDMAP
    }

    public MAPS getMap(String mapname) {
        return MapFiles.MAPS.valueOf(mapname);
    }

    public static String getFileName(MAPS map) {
        switch (map) {
            case COMPLICATEDMAP:
                return COMPLICATEDMAP;
        }
        throw new NullPointerException("Could not find file name");
    }

}
