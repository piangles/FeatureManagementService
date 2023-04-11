package org.piangles.backbone.services.feature.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.piangles.backbone.services.feature.Feature;
import org.piangles.backbone.services.feature.FeatureType;
import org.piangles.backbone.services.feature.FeatureValidator;

public class ActiveFeatureHydrator
{
	private static final String FEATURE_ID = "feature_id";
	private static final String FEATURE_TYPE = "feature_type";
	private static final String DESCRIPTION = "description";
	private static final String ENABLED_BY_DEFAULT = "enabled_by_default";

	private final FeatureValidator validator = new FeatureValidator();

	public Feature apply(ResultSet resultSet) throws SQLException
	{
		Feature feature = null;

		feature =  new Feature(
				resultSet.getString(FEATURE_ID),
				resultSet.getString(DESCRIPTION),
				FeatureType.valueOf(resultSet.getString(FEATURE_TYPE)),
				resultSet.getBoolean(ENABLED_BY_DEFAULT)
			);



		if(!validator.isValid(feature))
		{
			throw new SQLException(validator.getErrorMessage());
		}

		return feature;
	}
}
