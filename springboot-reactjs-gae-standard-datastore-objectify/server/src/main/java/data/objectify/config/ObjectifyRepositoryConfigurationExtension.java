package data.objectify.config;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Subclass;

import java.lang.annotation.Annotation;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Locale;

import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.data.repository.config.RepositoryConfigurationExtensionSupport;

import org.springframework.data.repository.config.RepositoryConfigurationSource;

import data.objectify.mapping.ObjectifyMappingContext;
import data.objectify.repository.ObjectifyRepository;
import data.objectify.support.ObjectifyRepositoryFactoryBean;

public class ObjectifyRepositoryConfigurationExtension extends RepositoryConfigurationExtensionSupport {
    protected static final String MAPPING_CONTEXT_BEAN_NAME = "keyValueMappingContext";

    public ObjectifyRepositoryConfigurationExtension() {
    }

    @Override
    public String getModuleName() {
        return "Objectify";
    }

    @Override
    protected String getModulePrefix() {
        return getModuleName().toLowerCase(Locale.US);
    }

    public String getRepositoryFactoryClassName() {
        return ObjectifyRepositoryFactoryBean.class.getName();
    }

    @Override
    protected Collection<Class<? extends Annotation>> getIdentifyingAnnotations() {
        return Arrays.asList(Entity.class, Subclass.class);
    }

    @Override
    protected Collection<Class<?>> getIdentifyingTypes() {
        return Collections.<Class<?>> singleton(ObjectifyRepository.class);
    }

	@Override
	public void registerBeansForRoot(BeanDefinitionRegistry registry, RepositoryConfigurationSource configurationSource) {
        super.registerBeansForRoot(registry, configurationSource);
        
        RootBeanDefinition mappingContextDefinition = new RootBeanDefinition(ObjectifyMappingContext.class);
        mappingContextDefinition.setSource(configurationSource.getSource());
        
        registerIfNotAlreadyRegistered(mappingContextDefinition, registry, MAPPING_CONTEXT_BEAN_NAME, configurationSource);
    }
}
