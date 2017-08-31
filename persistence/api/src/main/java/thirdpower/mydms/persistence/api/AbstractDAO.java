package thirdpower.mydms.persistence.api;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import com.google.common.collect.ImmutableList;
import com.google.common.reflect.TypeToken;

import thirdpower.mydms.util.PagedQuery;


public abstract class AbstractDAO<E, K> {

  @Inject
  private EntityManager entityManager;

  @Inject
  private Provider<PagedQuery> pagedQueryProvider;

  private final TypeToken<E> entityTypeToken = new TypeToken<E>(getClass()) {};

  public E persist(final E entity) {
    entityManager.persist(entity);
    return entity;
  }

  public void remove(final E entity) {
    entityManager.remove(entity);
  }

  public E findById(final K id) {
    return entityManager.find(getEntityClass(), id);
  }

  public List<E> findAll() {
    CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

    PagedQuery pagedQuery = pagedQueryProvider.get();


    CriteriaQuery<E> criteriaQuery = criteriaBuilder.createQuery(getEntityClass());
    Root<E> from = buildFilteredQuery(criteriaQuery);
    CriteriaQuery<E> select = criteriaQuery.select(from);
    TypedQuery<E> entityQuery = entityManager.createQuery(select);

    if (pagedQuery != null) {
      Long count = fetchResultCount(criteriaBuilder);
      pagedQuery.setTotalSize(count.intValue());
      
      if (null != pagedQuery.getPageSize()) {
        Integer pageSize = pagedQuery.getPageSize();
        Integer pageOffset = pagedQuery.getPageOffset();
        
        if(pageSize*pageOffset > count) {
          return ImmutableList.of();
        }
        
        entityQuery = entityQuery.setFirstResult(pageSize * pageOffset) //
          .setMaxResults(pageSize);
      }
    }
    
    return entityQuery.getResultList();
  }

  private Long fetchResultCount(CriteriaBuilder criteriaBuilder) {
    CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
    countQuery.select(criteriaBuilder.count(buildFilteredQuery(countQuery)));
    Long count = entityManager.createQuery(countQuery)
      .getSingleResult();
    return count;
  }

  private Root<E> buildFilteredQuery(CriteriaQuery<?> countQuery) {
    return countQuery.from(getEntityClass());
    // TODO add wheres
  }

  @SuppressWarnings("unchecked")
  private Class<E> getEntityClass() {
    return (Class<E>) entityTypeToken.getRawType();
  }

  public static final class PagedResult<E> {
    private List<E> result;
    private Paging paging;
    private int totalResults;

    PagedResult(List<E> result, Paging paging, int totalResults) {
      this.result = result;
      this.paging = paging;
      this.totalResults = totalResults;
    }

    public List<E> getResult() {
      return result;
    }

    public Paging getPaging() {
      return paging;
    }

    public int getTotalResults() {
      return totalResults;
    }

  }

}
