package data.objectify.support;

import com.googlecode.objectify.Key;
import static com.googlecode.objectify.ObjectifyService.ofy;

import com.googlecode.objectify.cmd.Query;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import org.springframework.data.domain.Sort.Order;
import org.springframework.data.repository.core.EntityInformation;

import data.objectify.repository.ObjectifyRepository;

public class SimpleObjectifyRepository <T, ID extends Serializable> implements ObjectifyRepository<T, ID> {
    private final EntityInformation<T, ID> metadata;
    
    public SimpleObjectifyRepository(EntityInformation<T, ID> metadata) {
        this.metadata = metadata;
        
        Class<ID> idType = metadata.getIdType();
        if ((idType != String.class) && (idType != Long.class)){
            throw new RuntimeException("Id Type must be String or Long");
        }
    }

    public Iterable<T> findAll(Sort sort) {
        Class<T> entityClass = metadata.getJavaType();
        Query<T> query = ofy().load().type(entityClass);

//        Order order;
//        for (Iterator<Order> iter=sort.iterator(); iter.hasNext(); ){
//            order=iter.next();
//            String condition = order.getProperty();
//            if (order.getDirection().isDescending()){
//                condition = "-" + condition;
//            }
//            query = query.order(condition);
//        }
        
        query = sortQuery(query, sort);
        
        return query.iterable();
    }

    public Page<T> findAll(Pageable pageable) {
        Class<T> entityClass = metadata.getJavaType();
        Query<T> query = ofy().load().type(entityClass);
        query = sortQuery(query, pageable.getSort());
        
        query = query.offset(pageable.getOffset());
        query = query.limit(pageable.getPageSize());

        // TODO
        long total = count();
        PageImpl<T> result = new PageImpl<T>(query.list(), pageable, total);
        return result;
    }

    public <S extends T> S save(S entity) {
        ofy().save().entity(entity).now();
        return entity;
    }

    public <S extends T> Iterable<S> save(Iterable<S> entities) {
        ofy().save().entities(entities).now();
        return entities;
    }

    public T findOne(ID id) {
        Key<T> key = getKey(id);
        return ofy().load().key(key).now();
    }

    public boolean exists(ID id) {
        Key<T> key = getKey(id);
        return ofy().load().key(key).now() != null;
    }

    public Iterable<T> findAll() {
        Class<T> entityClass = metadata.getJavaType();
        return ofy().load().type(entityClass).list();
    }

    public Iterable<T> findAll(Iterable<ID> ids) {
        Class<T> entityClass = metadata.getJavaType();
        List<Key<T>> keys = new ArrayList<Key<T>>();
        for (ID id : ids){
            keys.add(getKey(id));
        }
        return ofy().load().type(entityClass).filterKey("in", keys).iterable();
    }

    public long count() {
        Class<T> entityClass = metadata.getJavaType();
        return ofy().load().type(entityClass).keys().list().size();
    }

    public void delete(ID id) {
        Key<T> key = getKey(id);
        ofy().delete().key(key).now();
    }

    public void delete(T entity) {
        ofy().delete().entity(entity).now();
    }

    public void delete(Iterable<? extends T> entities) {
        ofy().delete().entities(entities).now();
    }

    public void deleteAll() {
        Class<T> entityClass = metadata.getJavaType();
        Iterable<Key<T>> keys = ofy().load().type(entityClass).keys();
        ofy().delete().keys(keys);
    }
    
    private Key<T> getKey(ID id){
        Key<T> key = null;
        Class<?> idType = metadata.getIdType();
        Class<T> entityClass = metadata.getJavaType();
        if (idType == String.class){
            key = Key.create(entityClass, (String)id);
        } else if (idType == Long.class){
            key = Key.create(entityClass, (Long)id);
        } else {
            throw new RuntimeException();
        }
        
        return key;
    }
    
    private Query<T> sortQuery(Query<T> query, Sort sort){
        if (sort == null){
            return query;
        }
        
        Order order;
        for (Iterator<Order> iter=sort.iterator(); iter.hasNext(); ){
            order=iter.next();
            String condition = order.getProperty();
            if (order.getDirection().isDescending()){
                condition = "-" + condition;
            }
            query = query.order(condition);
        }
        
        return query;
    }
}
