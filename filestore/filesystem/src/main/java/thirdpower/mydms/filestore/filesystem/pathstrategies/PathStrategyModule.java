package thirdpower.mydms.filestore.filesystem.pathstrategies;

import com.google.auto.service.AutoService;
import com.google.inject.AbstractModule;
import com.google.inject.Module;

@AutoService(Module.class)
public class PathStrategyModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(PathStrategy.class).toProvider(ConfiguredPathStrategyProvider.class);
	}


}
