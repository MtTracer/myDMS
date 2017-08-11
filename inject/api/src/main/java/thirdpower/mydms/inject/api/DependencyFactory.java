package thirdpower.mydms.inject.api;

import java.util.Iterator;
import java.util.ServiceLoader;

public class DependencyFactory {

	private static final class LazyHolder {
		private static final DependencyFactory INSTANCE = new DependencyFactory();
	}
	
	public static final <T> T getInstance(Class<T> clazz) {
		return LazyHolder.INSTANCE.resolveInstance(clazz);
	}

	private DependencyResolver dependencyResolver;
	
	private DependencyFactory() {
		Iterator<DependencyResolver> iterator = ServiceLoader.load(DependencyResolver.class).iterator();
		dependencyResolver = iterator.next();
		
		if(iterator.hasNext()) {
			//TODO warn
		}
	}
	
	private <T> T resolveInstance(Class<T> clazz) {
		return dependencyResolver.getInstance(clazz);
	}
	
	public interface DependencyResolver {
		<T> T getInstance(Class<T> clazz);
	}
}
