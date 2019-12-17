package data.objectify.mapping;

import java.beans.PropertyDescriptor;

import java.lang.reflect.Field;

import org.springframework.data.annotation.Id;
import org.springframework.data.mapping.Association;
import org.springframework.data.mapping.PersistentEntity;
import org.springframework.data.mapping.model.AnnotationBasedPersistentProperty;
import org.springframework.data.mapping.model.SimpleTypeHolder;

public class ObjectifyPersistentProperty extends AnnotationBasedPersistentProperty<ObjectifyPersistentProperty> {
    public ObjectifyPersistentProperty(Field field, PropertyDescriptor propertyDescriptor,
			PersistentEntity<?, ObjectifyPersistentProperty> owner, SimpleTypeHolder simpleTypeHolder) {
        super(field, propertyDescriptor, owner, simpleTypeHolder);
    }

    @Override
    protected Association<ObjectifyPersistentProperty> createAssociation() {
        return new Association<ObjectifyPersistentProperty>(this, null);
    }

    public boolean isIdProperty() {
        return isAnnotationPresent(Id.class) 
               || isAnnotationPresent(com.googlecode.objectify.annotation.Id.class);
    }
}
