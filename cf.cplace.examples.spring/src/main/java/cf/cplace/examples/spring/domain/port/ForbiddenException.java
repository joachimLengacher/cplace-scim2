package cf.cplace.examples.spring.domain.port;

/**
 * Indicates the current user has no permission to access an entity.
 */
public class ForbiddenException extends RuntimeException {

    private static final long serialVersionUID = -7973578322690559842L;

    public ForbiddenException() {
        super("Access forbidden");
    }
}
