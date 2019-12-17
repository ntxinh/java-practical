package data.objectify.config;

import java.lang.annotation.Annotation;

import org.springframework.data.repository.config.RepositoryBeanDefinitionRegistrarSupport;
import org.springframework.data.repository.config.RepositoryConfigurationExtension;

public class ObjectifyRepositoriesRegistrar extends RepositoryBeanDefinitionRegistrarSupport {
    public ObjectifyRepositoriesRegistrar() {
    }

    @Override
    protected Class<? extends Annotation> getAnnotation() {
        return EnableObjectifyRepositories.class;
    }

    @Override
    protected RepositoryConfigurationExtension getExtension() {
        return new ObjectifyRepositoryConfigurationExtension();
    }
}
