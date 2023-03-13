package org.piangles.backbone.services.feature.handlers;

import org.piangles.backbone.services.feature.FeatureException;
import org.piangles.backbone.services.feature.FeatureList;
import org.piangles.backbone.services.feature.FeatureToggleConfiguration;
import org.piangles.backbone.services.feature.dao.FeatureToggleServiceDAO;
import org.piangles.backbone.services.logging.LoggingService;
import org.piangles.core.dao.DAOException;

public class GetFeatureListHandler
{
	private final LoggingService logger;
	private final FeatureToggleConfiguration ftConfig;
	private final FeatureToggleServiceDAO ftsDAO;

	public GetFeatureListHandler(LoggingService logger, FeatureToggleConfiguration ftConfig, FeatureToggleServiceDAO ftsDAO)
	{
		this.logger = logger;
		this.ftConfig = ftConfig;
		this.ftsDAO = ftsDAO;
	}

	public FeatureList apply(String userId, String bizId) throws FeatureException
	{
		FeatureList featureList = null;

		try
		{
			featureList = ftsDAO.getFeatureList(userId, bizId);
		}
		catch (DAOException e)
		{
			final String message = "Unable to get FeatureList for userId: " + userId + " and bizId: " + bizId;
			logger.error(message + ". Reason: " + e.getMessage(), e);
			throw new FeatureException(message);
		}

		return featureList;
	}
}
