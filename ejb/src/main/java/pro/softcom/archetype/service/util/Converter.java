package pro.softcom.archetype.service.util;

import java.util.ArrayList;
import java.util.List;

import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;

public class Converter {

    private static Mapper mapper;

    static {
        mapper = new DozerBeanMapper();
    }

    public static <T> T convert(Object source, Class<T> destinationClass) {
        T destination = null;

        if (source != null) {
            destination = mapper.map(source, destinationClass);
        }

        return destination;
    }

    public static <T> List<T> convert(List<?> list, Class<T> mappedListTypeClass) {
        List<T> result = null;

        if (list != null) {
            result = new ArrayList<T>();
            for (Object object : list) {
                result.add(convert(object, mappedListTypeClass));
            }
        }

        return result;
    }

}
