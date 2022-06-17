package helpers;

import java.io.File;
import java.io.FilenameFilter;

public class FileHandler {
    private String extension;
    private String directory;

    public FileHandler(String extension, String directory) {
        this.extension = extension;
        this.directory = directory;
    }

    public FileHandler(String directory) {
        this.directory = directory;
    }

    public File[] GetFiles() {
        File file = new File(directory);
        File[] foundFiles = file.listFiles(new FileFilter(this.extension));

        return foundFiles;
    }

    public File GetSelectedFile(String fileName) {
        File file = new File(directory);
        File[] foundFiles = file.listFiles(((dir, name) -> name.equals(fileName)));

        return foundFiles != null ? foundFiles[0] : null;
    }

    static class FileFilter implements FilenameFilter {
        private String extension;
        public  FileFilter(String ex) {
            this.extension = ex;
        }

        @Override
        public boolean accept(File dir, String name) {
            return name.endsWith(extension);
        }
    }
}
