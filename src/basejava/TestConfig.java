package basejava;

import java.io.*;
import java.util.Properties;

public class TestConfig {

    private static final TestConfig INSTANCE = new TestConfig();
    private static final File PROPS = new File("config\\resumes.properties");
    private Properties props = new Properties();
    private File storageDir;

    private static int ST = 10;
    private int t;

    public static void main(String[] args) {
        try (FileReader fr = new FileReader(PROPS);
             BufferedReader br = new BufferedReader(fr)) {
            System.out.println("TestConfig is");
            while(br.ready()) {
                System.out.println(String.valueOf(br.readLine()));
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private TestConfig(){
        t = 9;
        System.out.println("TestConfig Конструктор");
        try (FileReader fr = new FileReader(PROPS);
             BufferedReader br = new BufferedReader(fr)) {
            System.out.println("TestConfig is");
            while(br.ready()) {
                System.out.println(String.valueOf(br.readLine()));
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
/*
        try(InputStream is = new FileInputStream(PROPS)) {
            props.load(is);
            storageDir = new File(props.getProperty("storage.dir"));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new IllegalStateException("Invalid config file " + PROPS.getAbsolutePath());
        }
*/
    }

    public static int getST () {
        return ST;
    }

    public int getT () {
        return t;
    }

    public static TestConfig get() {
        return INSTANCE;
    }
}
