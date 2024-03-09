package co.edu.uniandes.dse.parcialejemplo.exceptions;

public final class ErrorMessage {
    // Para las entidades:
    public static final String MEDICO_NOT_FOUND = "The doctor with the given id was not found";
    public static final String ESPECIALIDAD_NOT_FOUND = "The especialidad with the given id was not found";

    private ErrorMessage() throws IllegalOperationException {
        throw new IllegalOperationException("Utility class");
    }
}
