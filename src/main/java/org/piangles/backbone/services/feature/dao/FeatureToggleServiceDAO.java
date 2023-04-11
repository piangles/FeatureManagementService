package org.piangles.backbone.services.feature.dao;

import java.util.List;

import org.piangles.backbone.services.feature.Feature;
import org.piangles.core.dao.DAOException;

public interface FeatureToggleServiceDAO
{
	public List<Feature> getAllActiveFeatures(String userId) throws DAOException;
	public List<Feature> getAllEnabledFeatures(String userId) throws DAOException;
}
