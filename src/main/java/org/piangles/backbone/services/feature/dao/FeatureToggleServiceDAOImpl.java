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
	private static final String GET_ALL_CONFIGURED_FEATURES = "feature.get_all_configured_features";

	private final ActiveFeatureHydrator activeFeatureHydrator;
	private final ConfiguredFeatureHydrator configuredFeatureHydrator;

	public FeatureToggleServiceDAOImpl(ConfiguredFeatureHydrator hydrator) throws Exception
	{
		super.init(ResourceManager.getInstance().getRDBMSDataStore(new DefaultConfigProvider(FeatureToggleService.NAME, COMPONENT_ID)));
		
		this.activeFeatureHydrator = new ActiveFeatureHydrator();
		this.configuredFeatureHydrator = new ConfiguredFeatureHydrator();
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
	public List<Feature> getAllConfiguredFeatures(String userId) throws DAOException
	{
		List<Feature> configuredFeatures = null;
		
		configuredFeatures = super.executeSPQueryList(GET_ALL_CONFIGURED_FEATURES, 1, (stmt) ->
		{
			stmt.setString(1, userId);
		}, (rs, call) -> configuredFeatureHydrator.apply(rs));

		return configuredFeatures;
	}
}
