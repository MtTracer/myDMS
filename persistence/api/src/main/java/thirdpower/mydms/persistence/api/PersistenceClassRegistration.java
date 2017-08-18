package thirdpower.mydms.persistence.api;

public interface PersistenceClassRegistration {
  PersistenceClassRegistration register(Class<?> persistenceClass);
}
