package example;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import data.objectify.repository.ObjectifyRepository;

// @RepositoryRestResource
public interface CustomerRepository extends ObjectifyRepository<Customer, Long> {
}
