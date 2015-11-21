package cz.muni.fi.pa165.util;

import java.util.Collection;
import java.util.List;

/**
 * Created by vaculik on 21.11.15.
 */
public interface EntityMapper {

    <T> List<T> map(Collection<?> objectsToMap, Class<T> clazz);

    <T> T map(Object objectToMap, Class<T> clazz);
}
