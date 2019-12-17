package data.objectify.mapping;

import static com.googlecode.objectify.ObjectifyService.ofy;
import java.beans.PropertyDescriptor;

import java.lang.reflect.Field;

import org.springframework.data.mapping.context.AbstractMappingContext;
import org.springframework.data.mapping.model.SimpleTypeHolder;
import org.springframework.data.util.TypeInformation;

public class ObjectifyMappingContext extends
		AbstractMappingContext<ObjectifyPersistentEntity<?>, ObjectifyPersistentProperty> {
    public ObjectifyMappingContext() {
    }

    @Override
    protected <T> ObjectifyPersistentEntity createPersistentEntity(TypeInformation<T> typeInformation) {
        return new ObjectifyPersistentEntity<T>(typeInformation);
    }

    @Override
    protected ObjectifyPersistentProperty createPersistentProperty(Field field, PropertyDescriptor descriptor,
                                                                   ObjectifyPersistentEntity<?> owner,
                                                                   SimpleTypeHolder simpleTypeHolder) {
        return new ObjectifyPersistentProperty(field, descriptor, owner, simpleTypeHolder);
    }
    
    public boolean hasPersistentEntityFor(Class<?> type) {
        // TODO
        try {
            ofy().factory().getMetadata(type);
            return true;
        }catch(Exception e){
            return false;
        }
    }

    private void unsupportedOperation() {
        throw new UnsupportedOperationException();
    }
}
