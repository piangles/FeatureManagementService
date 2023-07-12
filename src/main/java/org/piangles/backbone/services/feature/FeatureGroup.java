package org.piangles.backbone.services.feature;

import java.io.Serializable;

public class FeatureGroup implements Serializable
{
	private static final long serialVersionUID = 1L;

	private String groupName;
	
	private String featureId;

	private String featureDescription;
	
	private FeatureType featureType;

	private boolean enabled;

	public FeatureGroup(String groupName, String featureId, String featureDescription, FeatureType featureType, boolean enabled)
	{
		this.groupName = groupName;
		this.featureId = featureId;
		this.featureDescription = featureDescription;
		this.featureType = featureType;
		this.enabled = enabled;
	}
	
	public String getGroupName()
	{
		return groupName;
	}
	
	public String getFeatureId() {
		return featureId;
	}

	public String getFeatureDescription() {
		return featureDescription;
	}

	public FeatureType getFeatureType()
	{
		return featureType;
	}
	
	public boolean isEnabled()
	{
		return enabled;
	}
}
