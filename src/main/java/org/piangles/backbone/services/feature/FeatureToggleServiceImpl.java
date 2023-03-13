package org.piangles.backbone.services.feature;

import org.piangles.backbone.services.feature.handlers.GetFeatureListHandler;
import org.piangles.backbone.services.logging.LoggingService;

public class FeatureToggleServiceImpl implements FeatureToggleService
{
	private final LoggingService logger;
	private final GetFeatureListHandler getFeatureListForUser;

	public FeatureToggleServiceImpl(LoggingService logger, GetFeatureListHandler getFeatureListForUser)
	{
		this.logger = logger;
		this.getFeatureListForUser = getFeatureListForUser;
	}

	@Override
	public FeatureList getFeatures(String userId, String bizId) throws FeatureException
	{
		logger.info("Retrieving Features for userId: " + userId + " bizId: " + bizId);
		
		return getFeatureListForUser.apply(userId, bizId);
	}
}