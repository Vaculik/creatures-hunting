package cz.muni.fi.pa165.util;

import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by vaculik on 21.11.15.
 */
@Service
public class EntityMapperImpl implements EntityMapper {

    @Autowired
    private Mapper mapper;

    @Override
    public <T> List<T> map(Collection<?> objectsToMap, Class<T> clazz) {
        List<T> results = new LinkedList<>();
        for (Object o : objectsToMap) {
            results.add(map(o, clazz));
        }
        return results;
    }

    @Override
    public <T> T map(Object objectToMap, Class<T> clazz) {
        return (objectToMap == null) ? null : mapper.map(objectToMap, clazz);
    }
}
