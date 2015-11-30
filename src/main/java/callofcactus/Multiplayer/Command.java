package callofcactus.multiplayer;


import org.json.JSONObject;

/**
 * Created by Wouter Vanmulken on 29-11-2015.
 */
public class Command {

    public enum methods {
        GET, POST, CHANGE
    }

    methods method;

    public methods getMethod() {
        return method;
    }

    public Object[] getObjects() {
        return objects;
    }

    public String getFieldToChange() {
        return fieldToChange;
    }

    public String getNewValue() {
        return newValue;
    }

    Object[] objects;
    String fieldToChange="";
    String newValue="";

    public Command(methods method, Object[] objectsToModify) {
        this.method = method;
        this.objects = objectsToModify;
    }

    public Command(methods method, Object[] objectsToModify, String fieldToChange, String newValue) {
        this.method = method;
        this.objects = objectsToModify;
        this.fieldToChange = fieldToChange;
        this.newValue = newValue;
    }

    //TODO better implementation of object toString
    @Override
    public String toString() {

        JSONObject obj = new JSONObject();
        obj.put("method", method.toString());
        obj.put("value", new Serializer().serialeDesiredObjects64(objects));

        if(fieldToChange.isEmpty()) {
            obj.put("field", fieldToChange);
            obj.put("newvalue", newValue);
        }
        return obj.toString();
    }

    public static Command fromString(String input) {

        JSONObject obj = new JSONObject(input);
        JSONObject method = obj.getJSONObject("method");
        JSONObject value = obj.getJSONObject("value");

        JSONObject field=null;
        JSONObject newValue=null;

        if(obj.has("field")){
            field = obj.getJSONObject("field");
            newValue = obj.getJSONObject("newValue");
        }

        if(field==null){
            return new Command(methods.valueOf(method.toString()), (new Serializer().deserialeDesiredObjects64(value.toString())), field.toString(),newValue.toString());
        }
        return new Command(methods.valueOf(method.toString()), (new Serializer().deserialeDesiredObjects64(value.toString())));
    }

}
