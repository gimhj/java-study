package tutoring.Project.auth.exeption;

public class ExpiredMemberException extends RuntimeException {

    public ExpiredMemberException() {
        super();
    }

    public ExpiredMemberException(String message) {
        super(message);
    }

    public ExpiredMemberException(String message, Throwable cause) {
        super(message, cause);
    }

    public ExpiredMemberException(Throwable cause) {
        super(cause);
    }

    protected ExpiredMemberException(
        String message, Throwable cause, boolean enableSuppression,
        boolean writableStackTrace
    ) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
