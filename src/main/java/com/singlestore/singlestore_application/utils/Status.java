package com.singlestore.singlestore_application.utils;

/**
 * The application processing statuses with standardized codes and messages.
 */
public enum Status {

    // Success Codes
    SUCCESS("200", "Operation completed successfully."),

    // General Error Codes
    FAILED("400", "Operation failed due to a bad request."),
    NOT_FOUND("404", "Requested resource not found."),
    ERROR("500", "Internal server error occurred."),
    SERVICE_UNAVAILABLE("503", "The service is currently unavailable."),
    TIMEOUT_ERROR("504", "Request timeout: processing took too long."),
    FORBIDDEN("403", "Access to the requested resource is forbidden."),

    // Processing Codes
    PROCESSING("102", "Request is being processed."),

    // Specific Error Codes
    VALIDATION_ERROR("422", "Validation error: input data is not valid."),
    DATABASE_ERROR("522", "Database error: unable to connect to the database."),
    AUTHENTICATION_ERROR("401", "Authentication error: invalid credentials."),
    CONFLICT("409", "There is a conflict with the current state of the resource."),
    BAD_GATEWAY("542", "Received an invalid response from the upstream server."),

    // Informational Codes
    INFO("100", "Informational response: request received.");

    private final String code;
    private final String message;

    /**
     * Constructor for StatusMessage.
     *
     * @param code    The status code.
     * @param message The status message.
     */
    Status(String code, String message) {
        this.code = code;
        this.message = message;
    }

    /**
     * Gets the status code.
     *
     * @return The status code.
     */
    public String getCode() {
        return code;
    }

    /**
     * Gets the status message.
     *
     * @return The status message.
     */
    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return code + ": " + message;
    }

}
