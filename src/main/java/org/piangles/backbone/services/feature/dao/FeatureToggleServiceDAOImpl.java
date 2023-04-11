package org.piangles.backbone.services.feature.dao;

import java.util.List;

import org.piangles.backbone.services.config.DefaultConfigProvider;
import org.piangles.backbone.services.feature.Feature;
import org.piangles.backbone.services.feature.FeatureToggleService;
import org.piangles.core.dao.DAOException;
import org.piangles.core.dao.rdbms.AbstractDAO;
import org.piangles.core.resources.ResourceManager;

public final class FeatureToggleServiceDAOImpl extends AbstractDAO implements FeatureToggleServiceDAO
{
	private static final String COMPONENT_ID = "70cdde51-98b8-445c-87dd-fd5cc7da7288";
	
	private static final String GET_ALL_ACTIVE_FEATURES = "feature.get_all_active_features";
	private static final String GET_ALL_ENABLED_FEATURES = "feature.get_all_enabled_features";

	private final ActiveFeatureHydrator activeFeatureHydrator;
	private final EnabledFeatureHydrator enabledFeatureHydrator;

	public FeatureToggleServiceDAOImpl(EnabledFeatureHydrator hydrator) throws Exception
	{
		super.init(ResourceManager.getInstance().getRDBMSDataStore(new DefaultConfigProvider(FeatureToggleService.NAME, COMPONENT_ID)));
		
		this.activeFeatureHydrator = new ActiveFeatureHydrator();
		this.enabledFeatureHydrator = new EnabledFeatureHydrator();
	}

	@Override
	public List<Feature> getAllActiveFeatures(String userId) throws DAOException
	{
		List<Feature> activeFeatures = null;
		
		activeFeatures = super.executeSPQueryList(GET_ALL_ACTIVE_FEATURES, 0, (stmt) ->
		{
		}, (rs, call) -> activeFeatureHydrator.apply(rs));

		
		return activeFeatures;
	}

	@Override
	public List<Feature> getAllEnabledFeatures(String userId) throws DAOException
	{
		List<Feature> enabledFeatures = null;
		
		enabledFeatures = super.executeSPQueryList(GET_ALL_ENABLED_FEATURES, 1, (stmt) ->
		{
			stmt.setString(1, userId);
		}, (rs, call) -> enabledFeatureHydrator.apply(rs));

		return enabledFeatures;
	}
}
