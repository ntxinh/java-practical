package data.objectify.support;

import java.io.Serializable;

import org.springframework.data.mapping.context.MappingContext;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.core.support.RepositoryFactoryBeanSupport;
import org.springframework.data.repository.core.support.RepositoryFactorySupport;
import org.springframework.data.repository.query.RepositoryQuery;
import org.springframework.data.repository.query.parser.AbstractQueryCreator;
import org.springframework.util.Assert;

import data.objectify.mapping.ObjectifyMappingContext;

public class ObjectifyRepositoryFactoryBean <T extends Repository<S, ID>, S, ID extends Serializable>
        extends RepositoryFactoryBeanSupport<T, S, ID> {
    private Class<? extends AbstractQueryCreator<?, ?>> queryCreator;
    private Class<? extends RepositoryQuery> repositoryQueryType;
    private MappingContext<?, ?> mappingContext;

    public ObjectifyRepositoryFactoryBean(Class<? extends T> repositoryInterface) {
        super(repositoryInterface);
        this.setMappingContext(new ObjectifyMappingContext());
    }

    @Override
    public void setMappingContext(MappingContext<?, ?> mappingContext) {
        super.setMappingContext(mappingContext);
        this.mappingContext = mappingContext;
    }

    /**
     * Configures the {@link AbstractQueryCreator} to be used.
     * 
     * @param queryCreator must not be {@literal null}.
     */
    public void setQueryCreator(Class<? extends AbstractQueryCreator<?, ?>> queryCreator) {
        Assert.notNull(queryCreator, "Query creator type must not be null!");

        this.queryCreator = queryCreator;
    }

    /**
     * Configures the {@link RepositoryQuery} type to be created.
     *
     * @param repositoryQueryType must not be {@literal null}.
     * @since 1.1
     */
    public void setQueryType(Class<? extends RepositoryQuery> repositoryQueryType) {
        Assert.notNull(queryCreator, "Query creator type must not be null!");

        this.repositoryQueryType = repositoryQueryType;
    }

    @Override
    public void afterPropertiesSet() {
//        Assert.notNull(queryCreator, "Query creator type must not be null!");
//        Assert.notNull(repositoryQueryType, "RepositoryQueryType type type must not be null!");
//        Assert.notNull(mappingContext, "MappingContext must not be null!");

        super.afterPropertiesSet();
    }
    
    @Override
    protected RepositoryFactorySupport createRepositoryFactory() {
//        return new ObjectifyRepositoryFactory(queryCreator, repositoryQueryType);
        return new ObjectifyRepositoryFactory(queryCreator, repositoryQueryType, mappingContext);
    }
}
