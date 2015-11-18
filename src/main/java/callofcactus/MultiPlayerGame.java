package callofcactus;

import com.sun.xml.internal.ws.developer.Serialization;

import java.lang.annotation.Annotation;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * Created by guushamm on 16-11-15.
 */
public class MultiPlayerGame extends Game implements Remote,Serialization, ITest {

    public MultiPlayerGame() throws RemoteException {
        UnicastRemoteObject.exportObject(this,8008);
    }

    @Override
    public String encoding() {
        return null;
    }

    @Override
    public Class<? extends Annotation> annotationType() {
        return null;
    }

//    @Override
//    public void playRandomHitSound() throws RemoteException{
//
//    }
//
//    @Override
//    public void playRandomBulletSound() throws RemoteException{
//
//    }
}
