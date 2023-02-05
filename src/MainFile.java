import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class MainFile {

    public static void main(String[] args) {
        File filePath = new File(".\\2 BaseJava\\BaseJava\\.gitignore");
        try {
            System.out.println(filePath.getCanonicalPath());
        } catch (IOException e) {
            throw new RuntimeException("Error", e);
        }
        File dir = new File("./2 BaseJava/BaseJava/src");
        System.out.println(dir.isDirectory());
        String[] list = dir.list();
        if(list != null) {
            for (String name : list) {
                System.out.println(name);
            }
        }

        printFiles(dir);

        try (FileInputStream fis = new FileInputStream(filePath)) {
            System.out.println(fis.read());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }

    private static void printFiles(File dir) {
        File[] list = dir.listFiles();
        if (list == null) return;
        for (File item : list) {
            if (item.isDirectory()) {
                printFiles(item);
            }
            System.out.println(item);
        }
    }
}
