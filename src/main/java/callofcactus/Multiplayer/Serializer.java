package callofcactus.multiplayer;

import callofcactus.entities.Entity;

import java.io.*;
import java.util.Base64;

/**
 * Created by Wouter Vanmulken on 30-11-2015.
 */
public class Serializer {

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
    public Entity[] deserialeDesiredObjects64(String deserializeSTring) {

        Entity[] o =null;
        try {
            byte[] data = Base64.getDecoder().decode(deserializeSTring);
            ObjectInputStream ois = new ObjectInputStream(
                    new ByteArrayInputStream(data));
            o = (Entity[]) ois.readObject();
            ois.close();

            System.out.println("size o :" + o.length);

        } catch (Exception e) {
            System.out.println(e);
        }
        return o;
    }
}
