package thirdpower.mydms.persistence.api;

public interface PersistenceClassProvider {

  void registerPersistenceClasses(PersistenceClassRegistration registration);
}
