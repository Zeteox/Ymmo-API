package fr.loic_delprat.ymmo_api.exeption;

public class ConflictException extends RuntimeException{
    public ConflictException(String message) {
        super(message);
    }
}
