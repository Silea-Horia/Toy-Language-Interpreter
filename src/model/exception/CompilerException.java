package model.exception;

// TODO MAKE THIS THE PARENT CLASS OF ALL OTHER EXCEPTIONS
public class CompilerException extends RuntimeException {
    public CompilerException(String message) {
        super(message);
    }
}
