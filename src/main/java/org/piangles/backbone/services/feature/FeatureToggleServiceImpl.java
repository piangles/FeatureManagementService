package org.piangles.backbone.services.feature;

import org.piangles.backbone.services.Locator;
import org.piangles.backbone.services.config.Configuration;
import org.piangles.backbone.services.feature.dao.ConfiguredFeatureHydrator;
import org.piangles.backbone.services.feature.dao.FeatureToggleServiceDAO;
import org.piangles.backbone.services.feature.dao.FeatureToggleServiceDAOImpl;
import org.piangles.backbone.services.feature.handlers.GetFeatureListForUserHandler;
import org.piangles.backbone.services.logging.LoggingService;

public class FeatureToggleServiceImpl implements FeatureToggleService
{
	private static final String COMPONENT_ID = "58185aed-c78d-4694-8e5f-f0a03d29cbd8";
	
	private final LoggingService logger  = Locator.getInstance().getLoggingService();
	
	private final FeatureToggleConfiguration ftConfig;
	
	private final FeatureToggleServiceDAO ftsDAO; 
	
	private final GetFeatureListForUserHandler getFeatureListForUserHandler;

	public FeatureToggleServiceImpl() throws Exception
	{
		final Configuration config = Locator.getInstance().getConfigService().getConfiguration(COMPONENT_ID);
		
		ftConfig = new FeatureToggleConfiguration(config);
		
		ftsDAO = new FeatureToggleServiceDAOImpl(new ConfiguredFeatureHydrator());
		
		this.getFeatureListForUserHandler = new GetFeatureListForUserHandler(logger, ftConfig, ftsDAO);
	}

	@Override
	public FeatureList getFeatureList(String userId) throws FeatureException
	{
		logger.info("Retrieving Features for userId: " + userId);

		return getFeatureListForUserHandler.handle(userId);
	}
}