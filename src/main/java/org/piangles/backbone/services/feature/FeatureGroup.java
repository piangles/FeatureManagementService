package org.piangles.backbone.services.feature;

import java.util.ArrayList;
import java.util.List;

public class FeatureGroup
{
	private String groupName;
	
	private List<String> featureIds;
	
	public FeatureGroup(String groupName)
	{
		this.groupName = groupName;
		this.featureIds = new ArrayList<String>(); 
	}
	
	public String getGroupName()
	{
		return groupName;
	}
	
	public List<String> getFeatureIds()
	{
		return featureIds;
	}
	
	public void add(String featureId)
	{
		featureIds.add(featureId);
	}
}
