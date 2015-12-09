package callofcactus.multiplayer;

import callofcactus.entities.Entity;

import java.io.*;
import java.util.Base64;

/**
 * Created by Wouter Vanmulken on 30-11-2015.
 */
public class Serializer {

    /**
     * Serializes a array of objects and serializes them and encodes them to a base64 format
     * @param objectsToSerialize
     * @return
     */
    public String serialeDesiredObjects64(Object[] objectsToSerialize){

        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream( baos );
            oos.writeObject( objectsToSerialize );
            oos.close();
            return Base64.getEncoder().encodeToString(baos.toByteArray());

        } catch (IOException e) {
            e.printStackTrace();
        }

        return "";
    }
    /**
     * Deserializes a String to an array of objects and deserializes them and decodes them from a base64 format
     * @param deserializeString
     * @return
     */
    public Entity[] deserialeDesiredObjects64(String deserializeString) {

        Object[] o =null;
        try {
            byte[] data = Base64.getDecoder().decode(deserializeString);
            ObjectInputStream ois = new ObjectInputStream(
                    new ByteArrayInputStream(data));
            o = (Entity[]) ois.readObject();
            ois.close();

            System.out.println("size o :" + o.length);

        } catch (Exception e) {
            System.out.println(e);
        }
        return (Entity[]) o;
    }
}
