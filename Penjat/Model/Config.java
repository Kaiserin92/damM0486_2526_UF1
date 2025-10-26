import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class Config implements Serializable {
    private String version;
    private int maxIntents;

    public Config(String version, int maxIntents) {
        this.version = version;
        this.maxIntents = maxIntents;
    }

    public String getVersion() {
        return version;
    }

    public int getMaxIntents() {
        return maxIntents;
    }

    public static int readMaxIntents() {
        File configPath = new File("model", "config.bin");
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(configPath))) {
            Config config = (Config) ois.readObject();
            return config.getMaxIntents();
        } catch (IOException | ClassNotFoundException e) {
            return 10;
        }
    }

    public static void writeConfig(Config config) {

        File configPath = new File("model", "config.bin");
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(configPath))) {
            oos.writeObject(config);
            System.out.println("Configuració desada: Versió " + config.getVersion() + " / " + config.getMaxIntents()
                    + " intents.");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
