
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author rabbah
 */
public class MyObject implements Serializable {
    private static final long serialVersionUID = 1L;
    static String country = "MROROCCO";
    private int age;
    private String name;
    transient int height;

    public byte[] toByte() {   
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {   
            ObjectOutputStream out = new ObjectOutputStream(bos);
            out.writeObject(this);
            out.flush();
            return bos.toByteArray();
        } catch (IOException ex) {
            Logger.getLogger(MyObject.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public static MyObject fromByte(byte[] myBytesObject) {
        ByteArrayInputStream bis = new ByteArrayInputStream(myBytesObject);
        ObjectInput in = null;
        MyObject o = null;
        try {
          in = new ObjectInputStream(bis);
          o = (MyObject) in.readObject(); 
          return o;
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(MyObject.class.getName()).log(Level.SEVERE, null, ex);
            return o;
        } finally {
          try {
            if (in != null) {
              in.close();
            }
          } catch (IOException ex) {
            // ignore close exception
          }
        }
    }
    public static String getCountry() {
        return country;
    }

    public static void setCountry(String country) {
        MyObject.country = country;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
    
    
}
