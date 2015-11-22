package cz.muni.fi.pa165.util;

import java.util.Collection;
import java.util.List;

/**
 * The interface provides basic mapping operations.
 *
 * @author Karel Vaculik
 */
public interface EntityMapper {

    /**
     * Map collection of objects to list of objects of type T.
     *
     * @param objectsToMap the collection of objects
     * @param clazz Class object of the type T
     * @param <T> the type T
     * @return the result list of objects
     */
    <T> List<T> map(Collection<?> objectsToMap, Class<T> clazz);

    /**
     * Map entry object to another object of type T.
     *
     * @param objectToMap the entry object
     * @param clazz Class object of the type T
     * @param <T> the type T
     * @return the result object
     */
    <T> T map(Object objectToMap, Class<T> clazz);
}
