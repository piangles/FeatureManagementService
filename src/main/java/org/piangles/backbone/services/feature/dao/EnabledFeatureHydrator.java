package org.piangles.backbone.services.feature.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.piangles.backbone.services.feature.Feature;
import org.piangles.backbone.services.feature.FeatureType;
import org.piangles.backbone.services.feature.FeatureValidator;

public class EnabledFeatureHydrator
{
	private static final String FEATURE_ID = "feature_id";
	private static final String FEATURE_TYPE = "feature_type";
	private static final String DESCRIPTION = "description";
	private static final String ENABLED = "enabled";
	private static final String VIEWABLE = "viewable";
	private static final String ACTIONABLE = "actionable";

	public Feature apply(ResultSet resultSet) throws SQLException
	{
		Feature feature = null;

		feature =  new Feature(
				resultSet.getString(FEATURE_ID),
				resultSet.getString(DESCRIPTION),
				FeatureType.valueOf(resultSet.getString(FEATURE_TYPE)),
				resultSet.getBoolean(ENABLED),
				resultSet.getBoolean(VIEWABLE),
				resultSet.getBoolean(ACTIONABLE)
			);


		final FeatureValidator validator = new FeatureValidator(feature);

		if(!validator.isValid())
		{
			throw new SQLException(validator.getErrorMessage());
		}

		return feature;
	}
}
