package org.piangles.backbone.services.feature;

import java.io.Serializable;

public final class Group implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	private final String groupId;
	private final String description;
	
	public Group(String groupId, String description)
	{
		this.groupId = groupId;
		this.description = description; 
	}

	public String getGroupId()
	{
		return groupId;
	}

	public String getDescription()
	{
		return description;
	}
}
