package thirdpower.mydms.persistence.api;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import com.google.common.reflect.TypeToken;


public abstract class AbstractDAO<E, K> {

  @Inject
  private EntityManager entityManager;

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

  @SuppressWarnings("unchecked")
  private Class<E> getEntityClass() {
    return (Class<E>) entityTypeToken.getRawType();
  }
  
  public List<E> findAll() {
    CriteriaBuilder cb = entityManager.getCriteriaBuilder();
    CriteriaQuery<E> cq = cb.createQuery(getEntityClass());
    Root<E> rootEntry = cq.from(getEntityClass());
    CriteriaQuery<E> all = cq.select(rootEntry);
    TypedQuery<E> allQuery = entityManager.createQuery(all);
    return allQuery.getResultList();
  }
}
