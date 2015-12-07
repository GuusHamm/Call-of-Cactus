package callofcactus.map;

/**
 * @author Teun
 */
public class MapFiles {

    public static final String COMPLICATEDMAP = "complicatedmap.tmx";

    public static String getFileName(MAPS map) {
        switch (map) {
            case COMPLICATEDMAP:
                return COMPLICATEDMAP;
            default:
                return COMPLICATEDMAP;
        }
    }

    public MAPS getMap(String mapname) {
        return MapFiles.MAPS.valueOf(mapname);
    }

    public enum MAPS {
        COMPLICATEDMAP
    }

}
