package com.inghubs.casestudy.exception;

/**
 * Extending RuntimeException in Java allows you to create an unchecked exception,
 * meaning it doesn't require explicit handling in the code (like a try-catch block or declaring it with throws in method signatures).
 */
public class NotFoundException extends RuntimeException {
    public NotFoundException(String message) {
        super(message);
    }
}
