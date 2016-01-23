package cz.muni.fi.pa165.hateoas;

/**
 * @author Karel Vaculik
 */

public class ExceptionResource {

    private String simpleName;
    private String message;

    public ExceptionResource(String name, String msg) {
        simpleName = name;
        message = msg;
    }

    public String getSimpleName() {
        return simpleName;
    }

    public String getMessage() {
        return message;
    }
}
