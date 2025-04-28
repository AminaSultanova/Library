package service;

public abstract class BaseService {

    protected void validateNotNull(Object obj, String message) {
        if (obj == null) {
            throw new IllegalArgumentException(message);
        }
    }

    protected void validateString(String str, String fieldName) {
        if (str == null || str.trim().isEmpty()) {
            throw new IllegalArgumentException(fieldName + " не может нулевым или быть пустым");
        }
    }

    protected void validatePositiveNumber(int number, String fieldName) {
        if (number <= 0) {
            throw new IllegalArgumentException(fieldName + " должен быть положительным");
        }
    }


    protected void validateNumberInRange(int number, int min, int max, String fieldName) {
        if (number < min || number > max) {
            throw new IllegalArgumentException(
                    String.format("%s должен быть между %d и %d", fieldName, min, max)
            );
        }
    }
}