package org.piangles.backbone.services.feature;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public final class UserGroup implements Serializable
{
	private static final long serialVersionUID = 1L;

	private String groupName;
	
	private List<String> userIds;
	
	public UserGroup(String groupName)
	{
		this.groupName = groupName;
		this.userIds = new ArrayList<String>(); 
	}
	
	public String getGroupName()
	{
		return groupName;
	}
	
	public List<String> getUserIds()
	{
		return userIds;
	}
	
	public void add(String userId)
	{
		userIds.add(userId);
	}
}
