package org.piangles.backbone.services.feature.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.piangles.backbone.services.feature.Group;

public class GroupHydrator
{
	private static final String GROUP_ID = "group_id";
	private static final String DESCRIPTION = "description";

	public Group apply(ResultSet resultSet) throws SQLException
	{
		Group group = null;

		group =  new Group(
				resultSet.getString(GROUP_ID),
				resultSet.getString(DESCRIPTION)
			);

		return group;
	}
}
