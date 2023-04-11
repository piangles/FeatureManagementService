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
			List<Feature> activeFeatures = ftsDAO.getAllActiveFeatures(userId);
			List<Feature> enabledFeatures = ftsDAO.getAllEnabledFeatures(userId);
			
			Map<String, Feature> enabledFeaturesMap = convertToMap(enabledFeatures);
			
			Feature enabledFeature = null;
			for (Feature activeFeature : activeFeatures)
			{
				enabledFeature = enabledFeaturesMap.get(activeFeature.getFeatureId()); 
				if (enabledFeature != null)
				{
					featureList.addFeature(enabledFeature);
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
