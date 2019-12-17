package data.objectify.repository;

import java.io.Serializable;

import org.springframework.data.repository.PagingAndSortingRepository;

public interface ObjectifyRepository <T, ID extends Serializable> extends PagingAndSortingRepository<T, ID> {
}
