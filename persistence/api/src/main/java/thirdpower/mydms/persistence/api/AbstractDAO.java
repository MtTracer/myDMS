package thirdpower.mydms.persistence.api;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.google.common.reflect.TypeToken;


public abstract class AbstractDAO<E extends AbstractDAO<E, K>, K> {

  @PersistenceContext
  private EntityManager entityManager;

  private final TypeToken<E> entityTypeToken = new TypeToken<E>(getClass()) {};

  public void persist(final E entity) {
    entityManager.persist(entity);
  }

  public void remove(final E entity) {
    entityManager.remove(entity);
  }

  public E findById(final K id) {
    final Class<E> entityType = (Class<E>) entityTypeToken.getType();
    return entityManager.find(entityType, id);
  }
}
