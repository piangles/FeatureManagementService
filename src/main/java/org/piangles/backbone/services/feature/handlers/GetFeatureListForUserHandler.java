package org.piangles.backbone.services.feature.handlers;

import java.util.List;
import java.util.Map;

import org.piangles.backbone.services.feature.Feature;
import org.piangles.backbone.services.feature.FeatureException;
import org.piangles.backbone.services.feature.FeatureList;
import org.piangles.backbone.services.feature.FeatureManagementConfiguration;
import org.piangles.backbone.services.feature.dao.FeatureManagementServiceDAO;
import org.piangles.backbone.services.logging.LoggingService;
import org.piangles.core.dao.DAOException;

public class GetFeatureListForUserHandler extends AbstractHandler
{
	public GetFeatureListForUserHandler(LoggingService logger, FeatureManagementConfiguration fmConfig, FeatureManagementServiceDAO fmsDAO)
	{
		super(logger, fmConfig, fmsDAO);
	}

	public FeatureList handle(String userId) throws FeatureException
	{
		FeatureList featureList = new FeatureList(userId);

		try
		{
			logger.info("Retrieving AllActiveFeatures for userId: " + userId);
			List<Feature> activeFeatures = getFMSDAO().getAllActiveFeatures(userId);
			
			logger.info("Retrieving AllConfiguredFeatures for userId: " + userId);
			List<Feature> configuredFeatures = getFMSDAO().getAllConfiguredFeatures(userId);
			
			Map<String, Feature> configuredFeaturesMap = convertToFeatureMap(configuredFeatures);
			
			Feature configuredFeature = null;
			for (Feature activeFeature : activeFeatures)
			{
				configuredFeature = configuredFeaturesMap.get(activeFeature.getFeatureId()); 
				if (configuredFeature != null)
				{
					featureList.addFeature(configuredFeature);
				}
				else
				{
					featureList.addFeature(activeFeature);
				}
			}
			
		}
		catch (DAOException e)
		{
			final String message = "Unable to get FeatureList for userId: " + userId;
			logger.error(message + ". Reason: " + e.getMessage(), e);
			throw new FeatureException(message);
		}
		
		logger.info("FeatureList for userId: " + userId + " " + featureList);

		return featureList;
	}
}
