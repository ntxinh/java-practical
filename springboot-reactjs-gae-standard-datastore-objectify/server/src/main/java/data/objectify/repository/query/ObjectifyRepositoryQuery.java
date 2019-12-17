package data.objectify.repository.query;

import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.CompositeFilterOperator;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.data.mapping.PropertyPath;
import org.springframework.data.repository.query.ParameterAccessor;
import org.springframework.data.repository.query.ParametersParameterAccessor;
import org.springframework.data.repository.query.QueryMethod;
import org.springframework.data.repository.query.RepositoryQuery;
import org.springframework.data.repository.query.ResultProcessor;
import org.springframework.data.repository.query.parser.Part;
import org.springframework.data.repository.query.parser.PartTree;
import org.springframework.data.repository.query.parser.PartTree.OrPart;

public class ObjectifyRepositoryQuery implements RepositoryQuery {
    private QueryMethod queryMethod;

    public ObjectifyRepositoryQuery(QueryMethod queryMethod) {
        this.queryMethod = queryMethod;
    }

    public Object execute(Object[] parameters) {
        ParameterAccessor accessor = new ParametersParameterAccessor(getQueryMethod().getParameters(), parameters);
        ResultProcessor processor = queryMethod.getResultProcessor().withDynamicProjection(accessor);
        
        return processor.processResult(doExecute(parameters));
    }

    public QueryMethod getQueryMethod() {
        return queryMethod;
    }
    
    protected Object doExecute(Object[] parameters) {
        Class<?> domainClass = queryMethod.getEntityInformation().getJavaType();
        int paramIndex = 0;
        
        PartTree tree = new PartTree(getQueryMethod().getName(), getQueryMethod().getEntityInformation().getJavaType());
        System.out.println(tree);
        
        List<Filter> orFilters = new ArrayList<Filter>();
        for (Iterator<OrPart> orPartIter = tree.iterator(); orPartIter.hasNext();) {
            OrPart orPart = orPartIter.next();
            
            List<Filter> andFilters = new ArrayList<Filter>();
            for (Iterator<Part> partIter = orPart.iterator(); partIter.hasNext();) {
                Part part = partIter.next();
                PropertyPath propertyPath  = part.getProperty();
                String propName = propertyPath.getSegment();
                Object propValue = parameters[paramIndex++];
                
                FilterOperator operator = getFilterOperation(part);
                Filter filter = new Query.FilterPredicate(propName, operator, propValue);
                andFilters.add(filter);
            }
            if (andFilters.size() == 1) {
                orFilters.add(andFilters.get(0));
            } else if (andFilters.size() >= 2){
                orFilters.add(CompositeFilterOperator.and(andFilters));
            }
        }
        
        com.googlecode.objectify.cmd.Query q = ofy().load().type(domainClass);
        if (orFilters.size() == 1) {
            q = q.filter(orFilters.get(0));
        } else if (orFilters.size() >= 2){
            q = q.filter(CompositeFilterOperator.or(orFilters));
        }
        return q.list();
    }

    private FilterOperator getFilterOperation(Part part){
        FilterOperator operator = null;
        switch (part.getType()) {
            case LESS_THAN:
                operator = Query.FilterOperator.LESS_THAN;
                break;
            case LESS_THAN_EQUAL:
                operator = Query.FilterOperator.LESS_THAN_OR_EQUAL;
                break;
            case GREATER_THAN:
                operator = Query.FilterOperator.GREATER_THAN;
                break;
            case GREATER_THAN_EQUAL:
                operator = Query.FilterOperator.GREATER_THAN_OR_EQUAL;
                break;
            case SIMPLE_PROPERTY:
                operator = Query.FilterOperator.EQUAL;
                break;
            case NEGATING_SIMPLE_PROPERTY:
                operator = Query.FilterOperator.NOT_EQUAL;
                break;
            case IN:
                operator = Query.FilterOperator.IN;
                break;
            default:
                throw new UnsupportedOperationException("Unsupported Part Type: " + part.getType());
        }
        
        return operator;
    }
}
