package thirdpower.mydms.persistence.api;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.google.common.reflect.TypeToken;


public abstract class AbstractDAO<E, K> {

  @PersistenceContext
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
    final Class<E> entityType = (Class<E>) entityTypeToken.getRawType();
    return entityManager.find(entityType, id);
  }
}
