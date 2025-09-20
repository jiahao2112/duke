package exceptions;

public class FileException extends GrootException {
    public FileException(String message) {
        super(message);
    }

    public static class UnableToCreateDirectoryException extends FileException {
        public UnableToCreateDirectoryException() {
            super("Unable to create directory.");
        }
    }

    public static class UnableToCreateFileException extends FileException {
        public UnableToCreateFileException() {
            super("Error while creating file.");
        }
    }

    public static class FileDoesNotExistException extends FileException {
        public FileDoesNotExistException() {
            super("tasklist.txt does not exist. Check that tasklist.txt exists in data folder.");
        }
    }

    public static class UnableToWriteFileException extends FileException {
        public UnableToWriteFileException() {
            super("Error while writing file.");
        }
    }

    public static class FileCorruptedException extends FileException {
        public FileCorruptedException() {
            super("Tasklist file is corrupted.");
        }
    }
}
