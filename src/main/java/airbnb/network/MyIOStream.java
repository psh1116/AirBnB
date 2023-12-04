package airbnb.network;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class MyIOStream {
    public ObjectOutputStream oos;
    public ObjectInputStream ois;

    public MyIOStream(ObjectOutputStream oos, ObjectInputStream ois) {
        this.oos = oos;
        this.ois = ois;
    }
}
