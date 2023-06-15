package org.piangles.backbone.services.feature.handlers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.piangles.backbone.services.feature.Feature;
import org.piangles.backbone.services.feature.FeatureManagementConfiguration;
import org.piangles.backbone.services.feature.Group;
import org.piangles.backbone.services.feature.dao.FeatureManagementServiceDAO;
import org.piangles.backbone.services.logging.LoggingService;

public class AbstractHandler
{
	protected final LoggingService logger;
	private final FeatureManagementConfiguration fmConfig;
	private final FeatureManagementServiceDAO fmsDAO;

	public AbstractHandler(LoggingService logger, FeatureManagementConfiguration fmConfig, FeatureManagementServiceDAO fmsDAO)
	{
		this.logger = logger;
		this.fmConfig = fmConfig;
		this.fmsDAO = fmsDAO;
	}

	protected final FeatureManagementConfiguration getFMConfig()
	{
		return fmConfig;
	}
	
	protected final FeatureManagementServiceDAO getFMSDAO()
	{
		return fmsDAO;
	}
	
	protected final Map<String, Feature> convertToFeatureMap(List<Feature> features)
	{
		Map<String, Feature> featureMap = new HashMap<>();
		
		if (features != null)
		{
			featureMap = features.stream().collect(Collectors.toMap(Feature::getFeatureId, feature -> feature));
		}
		
		return featureMap;
	}

	protected final Map<String, Group> convertToGroupMap(List<Group> groups)
	{
		Map<String, Group> groupMap = new HashMap<>();
		
		if (groups != null)
		{
			groupMap = groups.stream().collect(Collectors.toMap(Group::getGroupId, group -> group));
		}
		
		return groupMap;
	}
}
