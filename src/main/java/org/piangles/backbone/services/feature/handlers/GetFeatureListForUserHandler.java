package org.piangles.backbone.services.feature.handlers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.piangles.backbone.services.feature.Feature;
import org.piangles.backbone.services.feature.FeatureException;
import org.piangles.backbone.services.feature.FeatureList;
import org.piangles.backbone.services.feature.FeatureToggleConfiguration;
import org.piangles.backbone.services.feature.dao.FeatureToggleServiceDAO;
import org.piangles.backbone.services.logging.LoggingService;
import org.piangles.core.dao.DAOException;

public class GetFeatureListForUserHandler
{
	private final LoggingService logger;
	@SuppressWarnings("unused")
	private final FeatureToggleConfiguration ftConfig;
	private final FeatureToggleServiceDAO ftsDAO;

	public GetFeatureListForUserHandler(LoggingService logger, FeatureToggleConfiguration ftConfig, FeatureToggleServiceDAO ftsDAO)
	{
		this.logger = logger;
		this.ftConfig = ftConfig;
		this.ftsDAO = ftsDAO;
	}

	public FeatureList handle(String userId) throws FeatureException
	{
		FeatureList featureList = new FeatureList(userId);

		try
		{
			logger.info("Retrieving AllActiveFeatures for userId: " + userId);
			List<Feature> activeFeatures = ftsDAO.getAllActiveFeatures(userId);
			
			logger.info("Retrieving AllConfiguredFeatures for userId: " + userId);
			List<Feature> configuredFeatures = ftsDAO.getAllConfiguredFeatures(userId);
			
			Map<String, Feature> configuredFeaturesMap = convertToMap(configuredFeatures);
			
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
	
	private Map<String, Feature> convertToMap(List<Feature> features)
	{
		Map<String, Feature> featureMap = new HashMap<>();
		
		if (features != null)
		{
			featureMap = features.stream().collect(Collectors.toMap(Feature::getFeatureId, feature -> feature));
		}
		
		return featureMap;
	}
}
