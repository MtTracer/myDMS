package thirdpower.mydms.persistence.api;

import com.google.common.collect.ImmutableList;
import com.google.common.reflect.TypeToken;
import thirdpower.mydms.util.PagedQuery;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;


public abstract class AbstractRepository<E, K> {

  @Inject
  private EntityManager entityManager;

  @Inject
  private Provider<PagedQuery> pagedQueryProvider;

  private final TypeToken<E> entityTypeToken = new TypeToken<E>(getClass()) {};

  public E persist(final E entity) {
    entityManager.getTransaction()
      .begin();
    entityManager.persist(entity);
    entityManager.getTransaction()
      .commit();
    return entity;
  }

  public void remove(E entity) {
    entityManager.getTransaction()
      .begin();
    entity = entityManager.merge(entity);
    entityManager.remove(entity);
    entityManager.getTransaction()
      .commit();
  }

  public E findById(final K id) {
    return entityManager.find(getEntityClass(), id);
  }

  public List<E> findAll() {
    final CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

    final PagedQuery pagedQuery = pagedQueryProvider.get();


    final CriteriaQuery<E> criteriaQuery = criteriaBuilder.createQuery(getEntityClass());
    final Root<E> from = buildFilteredQuery(criteriaQuery);
    final CriteriaQuery<E> select = criteriaQuery.select(from);
    TypedQuery<E> entityQuery = entityManager.createQuery(select);

    if (pagedQuery != null) {
      final Long count = fetchResultCount(criteriaBuilder);
      pagedQuery.setTotalSize(count.intValue());

      if (null != pagedQuery.getPageSize()) {
        final Integer pageSize = pagedQuery.getPageSize();
        final Integer pageOffset = pagedQuery.getPageOffset();

        if (pageSize * pageOffset > count) {
          return ImmutableList.of();
        }

        entityQuery = entityQuery.setFirstResult(pageSize * pageOffset) //
          .setMaxResults(pageSize);
      }
    }

    return entityQuery.getResultList();
  }

  private Long fetchResultCount(final CriteriaBuilder criteriaBuilder) {
    final CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
    countQuery.select(criteriaBuilder.count(buildFilteredQuery(countQuery)));
    return entityManager.createQuery(countQuery)
      .getSingleResult();
  }

  private Root<E> buildFilteredQuery(final CriteriaQuery<?> countQuery) {
    return countQuery.from(getEntityClass());
    // TODO add wheres
  }

  @SuppressWarnings("unchecked")
  private Class<E> getEntityClass() {
    return (Class<E>) entityTypeToken.getRawType();
  }

  protected final EntityManager getEntityManager() {
    return entityManager;
  }

  public static final class PagedResult<E> {
    private final List<E> result;
    private final Paging paging;
    private final int totalResults;

    PagedResult(final List<E> result, final Paging paging, final int totalResults) {
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
