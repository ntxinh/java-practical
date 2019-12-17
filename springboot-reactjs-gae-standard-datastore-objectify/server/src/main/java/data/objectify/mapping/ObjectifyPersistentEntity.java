package data.objectify.mapping;

import org.springframework.data.mapping.model.BasicPersistentEntity;
import org.springframework.data.util.TypeInformation;

public class ObjectifyPersistentEntity<T> extends BasicPersistentEntity<T, ObjectifyPersistentProperty> {
    public ObjectifyPersistentEntity(TypeInformation<T> information) {
        super(information);
    }
}
