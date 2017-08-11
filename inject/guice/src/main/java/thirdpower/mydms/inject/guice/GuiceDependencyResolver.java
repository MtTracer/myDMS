package thirdpower.mydms.inject.guice;

import java.util.Iterator;
import java.util.ServiceLoader;

import com.google.auto.service.AutoService;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;

import thirdpower.mydms.inject.api.DependencyFactory.DependencyResolver;

@AutoService(DependencyResolver.class)
public class GuiceDependencyResolver implements DependencyResolver {

	private Injector injector;

	public GuiceDependencyResolver() {
		injector = Guice.createInjector(ServiceLoader.load(Module.class));
	}

	@Override
	public <T> T getInstance(Class<T> clazz) {
		return injector.getInstance(clazz);
	}

}
