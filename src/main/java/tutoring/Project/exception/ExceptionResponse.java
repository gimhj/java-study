package tutoring.Project.exception;

import lombok.Getter;

@Getter
public class ExceptionResponse {

    private int status;
    private String serviceName;
    private String message;

    public ExceptionResponse(int status, String serviceName, String message) {
        this.status = status;
        this.serviceName = serviceName;
        this.message = message;
    }
}
