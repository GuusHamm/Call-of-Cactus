package callofcactus.io;

import org.joda.time.DateTime;

/**
 * Created by Guus on 2015-12-17.
 */
public class Logger {
    private static Logger instance;
    private boolean showError = true;
    private boolean showSucces = false;
    private boolean showInput = false;
    private boolean showServerCommunication = true;
    private boolean showInfo = true;

    public Logger() {

    }

    public static Logger getInstance() {
        if (instance == null) {
            instance = new Logger();
        }
        return instance;
    }

    public void LogException(Exception e, Class fromClass) {
        logEvent(e.getStackTrace().toString(), TypeEnum.Info, fromClass);
        System.out.println(String.format("(%s-%s): Error : %s", fromClass.getName(), DateTime.now().toLocalTime().toString(), e.getClass().getName()));
    }

    public void logEvent(String Message, TypeEnum type, Class fromClass) {
        switch (type) {
            case Error:
                if (showError) {
                    System.out.println(String.format("%s (%s %s): %s", type.name().toUpperCase(), fromClass.getSimpleName(), DateTime.now().secondOfDay().getAsShortText(), Message));
                }
                break;
            case Succes:
                if (showSucces) {
                    System.out.println(String.format("%s (%s %s): %s", type.name().toUpperCase(), fromClass.getSimpleName(), DateTime.now().secondOfDay().getAsShortText(), Message));
                }
                break;
            case Input:
                if (showInput) {
                    System.out.println(String.format("%s (%s %s): %s", type.name().toUpperCase(), fromClass.getSimpleName(), DateTime.now().secondOfDay().getAsShortText(), Message));
                }
                break;
            case ServerCommunication:
                if (showServerCommunication) {
                    String classString = fromClass.getSimpleName();
                    System.out.println(String.format("%s (%s %s): %s", type.name().toUpperCase(), fromClass.getSimpleName(), DateTime.now().secondOfDay().getAsShortText(), Message));
                }
                break;
            case Info:
                if (showInfo) {
                    System.out.println(String.format("%s (%s %s): %s", type.name().toUpperCase(), fromClass.getSimpleName(), DateTime.now().secondOfDay().getAsShortText(), Message));
                }
                break;
        }
    }

    public enum TypeEnum {
        Error,
        Succes,
        Input,
        ServerCommunication,
        Info
    }
}
