package org.piangles.backbone.services.feature.dao;

import org.piangles.backbone.services.feature.FeatureGroup;
import org.piangles.backbone.services.feature.FeatureType;

import java.sql.ResultSet;
import java.sql.SQLException;

public class FeatureGroupHydrator
{
	private static final String FEATURE_ID = "feature_id";
	private static final String GROUP_ID = "group_id";
	private static final String DESCRIPTION = "description";
	private static final String FEATURE_TYPE = "feature_type";
	private static final String ENABLED_BY_DEFAULT = "enabled_by_default";
	
	public FeatureGroup apply(ResultSet resultSet) throws SQLException
	{
		return  new FeatureGroup(
				resultSet.getString(GROUP_ID),
				resultSet.getString(FEATURE_ID),
				resultSet.getString(DESCRIPTION),
				FeatureType.valueOf(resultSet.getString(FEATURE_TYPE)),
				resultSet.getBoolean(ENABLED_BY_DEFAULT));
	}
}
