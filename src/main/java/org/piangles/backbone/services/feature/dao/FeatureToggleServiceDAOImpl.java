package org.piangles.backbone.services.feature.dao;

import org.piangles.backbone.services.config.DefaultConfigProvider;
import org.piangles.backbone.services.feature.Feature;
import org.piangles.backbone.services.feature.FeatureList;
import org.piangles.backbone.services.feature.FeatureToggleService;
import org.piangles.core.dao.DAOException;
import org.piangles.core.dao.rdbms.AbstractDAO;
import org.piangles.core.resources.ResourceManager;

import java.util.List;

public final class FeatureToggleServiceDAOImpl extends AbstractDAO implements FeatureToggleServiceDAO
{
	private static final String COMPONENT_ID = "70cdde51-98b8-445c-87dd-fd5cc7da7288";
	private static final String GET_FEATURE_LIST = "feature.get_feature_list";

	private final FeatureListHydrator hydrator;

	public FeatureToggleServiceDAOImpl(FeatureListHydrator hydrator) throws Exception
	{
		super.init(ResourceManager.getInstance().getRDBMSDataStore(new DefaultConfigProvider(FeatureToggleService.NAME, COMPONENT_ID)));
		this.hydrator = hydrator;

	}

	@Override
	public FeatureList getFeatureList(String userId, String bizId) throws DAOException
	{
		final FeatureList featureList = new FeatureList(userId, bizId);

		final List<Feature> features = super.executeSPQueryList(GET_FEATURE_LIST, 1, (stmt) ->
		{
			stmt.setString(1, userId);
		}, (rs, call) -> hydrator.apply(rs));

		features.forEach(feature -> featureList.addFeature(feature));

		return featureList;
	}
}
