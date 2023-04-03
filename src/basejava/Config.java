package basejava;

import java.io.*;
import java.util.Properties;

public class Config {

    private static final Config INSTANCE = new Config();
    protected static final File PROPS = new File("config\\resumes.properties");
    private Properties props = new Properties();
    private File storageDir;

    private Config() {
        try(InputStream is = new FileInputStream(PROPS)) {
            props.load(is);
            storageDir = new File(props.getProperty("storage.dir"));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new IllegalStateException("Invalid config file " + PROPS.getAbsolutePath());
        }
    }

    public static Config get() {
        return INSTANCE;
    }

    public File getStorageDir() {
        return storageDir;
    }
}
