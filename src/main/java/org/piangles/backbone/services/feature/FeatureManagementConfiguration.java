package org.piangles.backbone.services.feature;

import org.piangles.backbone.services.config.Configuration;

public class FeatureManagementConfiguration
{
	private static final String ENABLED_BY_DEFAULT = "EnabledByDefault";
	
	
	private final boolean enabledByDefault; //viewables / actionable
	
	public FeatureManagementConfiguration(Configuration config)
	{
		enabledByDefault = Boolean.parseBoolean(config.getValue(ENABLED_BY_DEFAULT));
	}
	
	public boolean isEnabledByDefault()
	{
		return enabledByDefault;
	}
}
