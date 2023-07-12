package org.piangles.backbone.services.feature.handlers;

import org.piangles.backbone.services.feature.Feature;
import org.piangles.backbone.services.feature.FeatureException;
import org.piangles.backbone.services.feature.FeatureManagementConfiguration;
import org.piangles.backbone.services.feature.FeatureGroup;
import org.piangles.backbone.services.feature.dao.FeatureManagementServiceDAO;
import org.piangles.backbone.services.logging.LoggingService;
import org.piangles.core.dao.DAOException;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class GetGroupsWithFeaturesHandler extends AbstractHandler
{
	public GetGroupsWithFeaturesHandler(LoggingService logger, FeatureManagementConfiguration fmConfig, FeatureManagementServiceDAO fmsDAO)
	{
		super(logger, fmConfig, fmsDAO);
	}

	public Map<String, List<Feature>> handle() throws FeatureException
	{
		Map<String, List<Feature>> groupsWithFeatures = null;
		try
		{
			logger.info("Retrieving AllGroupsWithFeatures...");
			List<FeatureGroup> featureGroups = getFMSDAO().getAllFeatureGroups();
			
			if (featureGroups != null && !featureGroups.isEmpty()) 
			{
				//group features by groupName and collect into a map of groupName and list of features
				groupsWithFeatures = featureGroups.stream()
						.collect(Collectors.groupingBy(FeatureGroup::getGroupName,
								Collectors.mapping(featureGroup -> 
												new Feature(featureGroup.getFeatureId(), 
														featureGroup.getFeatureDescription(),
														featureGroup.getFeatureType(),
														featureGroup.isEnabled()),
										Collectors.toList())));
			}
		}
		catch (DAOException e)
		{
			final String message = "Unable to AllGroupsWithFeatures";
			logger.error(message + ". Reason: " + e.getMessage(), e);
			throw new FeatureException(message);
		}
		
		return groupsWithFeatures;
	}
}
