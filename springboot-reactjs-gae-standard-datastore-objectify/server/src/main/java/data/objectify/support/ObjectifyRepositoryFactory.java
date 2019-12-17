package data.objectify.support;

import java.io.Serializable;

import org.springframework.data.mapping.context.MappingContext;
import org.springframework.data.repository.core.EntityInformation;
import org.springframework.data.repository.core.RepositoryInformation;
import org.springframework.data.repository.core.RepositoryMetadata;
import org.springframework.data.repository.core.support.RepositoryFactorySupport;
import org.springframework.data.repository.query.EvaluationContextProvider;
import org.springframework.data.repository.query.QueryLookupStrategy;
import org.springframework.data.repository.query.QueryLookupStrategy.Key;
import org.springframework.data.repository.query.RepositoryQuery;
import org.springframework.data.repository.query.parser.AbstractQueryCreator;

public class ObjectifyRepositoryFactory extends RepositoryFactorySupport {
    private final Class<? extends AbstractQueryCreator<?, ?>> queryCreator;
    private final Class<? extends RepositoryQuery> repositoryQueryType;
    private final MappingContext<?, ?> mappingContext;
    
    public ObjectifyRepositoryFactory(Class<? extends AbstractQueryCreator<?, ?>> queryCreator,
                                      Class<? extends RepositoryQuery> repositoryQueryType,
                                      MappingContext<?, ?> mappingContext) {
        this.queryCreator = queryCreator;
        this.repositoryQueryType = repositoryQueryType;
        this.mappingContext = mappingContext;
    }

    @Override
    public <T, ID extends Serializable> EntityInformation<T, ID> getEntityInformation(Class<T> domainClass) {
        return new ObjectifyEntityInformation<T, ID>(domainClass);
    }

    @Override
    protected Object getTargetRepository(RepositoryInformation repositoryInformation) {
        EntityInformation<?, Serializable> entityInformation = getEntityInformation(repositoryInformation.getDomainType());
        return super.getTargetRepositoryViaReflection(repositoryInformation, entityInformation);
    }

    @Override
    protected Class<?> getRepositoryBaseClass(RepositoryMetadata metadata) {
        return SimpleObjectifyRepository.class;
    }

    @Override
    protected QueryLookupStrategy getQueryLookupStrategy(Key key, EvaluationContextProvider evaluationContextProvider) {
        return new ObjectifyQueryLookupStrategy(key, evaluationContextProvider);
    }
}
