package org.piangles.backbone.services.feature.dao;

import org.piangles.backbone.services.feature.FeatureList;
import org.piangles.core.dao.DAOException;

public interface FeatureToggleServiceDAO
{
	FeatureList getFeatureList(String userId, String bizId) throws DAOException;
}
