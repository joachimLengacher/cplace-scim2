package cf.cplace.examples.spring.domain.port;

/**
 * Indicates that an entity was not found.
 */
public class NotFoundException extends RuntimeException {

    private static final long serialVersionUID = -7973578322690559842L;

    public NotFoundException(String id) {
        super(String.format("Entity with id '%s' not found", id));
    }
}
