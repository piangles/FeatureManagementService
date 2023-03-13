package org.piangles.backbone.services.feature;

import org.piangles.backbone.services.config.Configuration;
import org.piangles.backbone.services.feature.dao.FeatureListHydrator;
import org.piangles.backbone.services.feature.dao.FeatureToggleServiceDAO;
import org.piangles.backbone.services.feature.dao.FeatureToggleServiceDAOImpl;
import org.piangles.backbone.services.feature.handlers.GetFeatureListHandler;
import org.piangles.backbone.services.logging.LoggingService;

import com.zurohq.core.util.Factory;

public class FeatureServiceFactory extends Factory<FeatureToggleService>
{
	private static final String COMPONENT_ID = "58185aed-c78d-4694-8e5f-f0a03d29cbd8"; 
			
	public FeatureToggleService create() throws Exception
	{
		FeatureToggleService service = null;
		
		final Configuration config = this.serviceLocator.getConfigService().getConfiguration(COMPONENT_ID);
		
		final FeatureToggleConfiguration ftConfig = new FeatureToggleConfiguration(config);

		final LoggingService logger = this.serviceLocator.getLoggingService();
		
		final FeatureToggleServiceDAO ftsDAO = new FeatureToggleServiceDAOImpl(new FeatureListHydrator());
		
		final GetFeatureListHandler getFeatureListForUserHandler = new GetFeatureListHandler(
				logger,
				ftConfig,
				ftsDAO
		);
		
		service = new FeatureToggleServiceImpl(
			logger,
			getFeatureListForUserHandler
		);

		return service;
	}
}
