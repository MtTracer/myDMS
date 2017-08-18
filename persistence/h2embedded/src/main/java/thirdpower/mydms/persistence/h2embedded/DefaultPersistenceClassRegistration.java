package thirdpower.mydms.persistence.h2embedded;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.ArrayList;
import java.util.List;

import thirdpower.mydms.persistence.api.PersistenceClassRegistration;

final class DefaultPersistenceClassRegistration implements PersistenceClassRegistration {

  private final List<Class<?>> persistenceClasses = new ArrayList<>();

  @Override
  public PersistenceClassRegistration register(final Class<?> persistenceClass) {
    persistenceClasses.add(checkNotNull(persistenceClass));
    return this;
  }

  List<Class<?>> getPersistenceClasses() {
    return persistenceClasses;
  }
}
