package org.piangles.backbone.services.feature.dao;

import java.util.List;

import org.piangles.backbone.services.config.DefaultConfigProvider;
import org.piangles.backbone.services.feature.Feature;
import org.piangles.backbone.services.feature.FeatureGroup;
import org.piangles.backbone.services.feature.FeatureToggleService;
import org.piangles.backbone.services.feature.Group;
import org.piangles.backbone.services.feature.UpdateFeatureRequest;
import org.piangles.core.dao.DAOException;
import org.piangles.core.dao.rdbms.AbstractDAO;
import org.piangles.core.resources.ResourceManager;

public final class FeatureManagementServiceDAOImpl extends AbstractDAO implements FeatureManagementServiceDAO
{
	private static final String COMPONENT_ID = "70cdde51-98b8-445c-87dd-fd5cc7da7288";
	
	private static final String GET_ALL_GROUPS = "feature.get_all_groups";
	private static final String ADD_USER_TO_GROUP = "feature.add_user_to_group";
	private static final String REMOVE_USER_FROM_GROUP = "feature.remove_user_from_group";
	
	private static final String GET_ALL_ACTIVE_FEATURES = "feature.get_all_active_features";
	private static final String GET_ALL_CONFIGURED_FEATURES = "feature.get_all_configured_features";
    private static final String TOGGLE_FEATURE_FROM_GROUP = "feature.toggle_feature_group";
	private static final String GET_ALL_GROUPS_WITH_FEATURES = "feature.get_all_groups_with_features";

	private final ActiveFeatureHydrator activeFeatureHydrator;
	private final GroupHydrator groupHydrator;
	private final ConfiguredFeatureHydrator configuredFeatureHydrator;
	private final FeatureGroupHydrator featureGroupHydrator;

	public FeatureManagementServiceDAOImpl(ConfiguredFeatureHydrator hydrator) throws Exception
	{
		super.init(ResourceManager.getInstance().getRDBMSDataStore(new DefaultConfigProvider(FeatureToggleService.NAME, COMPONENT_ID)));
		
		this.activeFeatureHydrator = new ActiveFeatureHydrator();
		this.groupHydrator = new GroupHydrator();
		this.configuredFeatureHydrator = new ConfiguredFeatureHydrator();
		this.featureGroupHydrator = new FeatureGroupHydrator();
	}

	@Override
	public List<Group> getAllGroups() throws DAOException
	{
		List<Group> groups = null;
		
		groups = super.executeSPQueryList(GET_ALL_GROUPS, 0, (stmt) ->
		{
		}, (rs, call) -> groupHydrator.apply(rs));

		
		return groups;
	}
	
	@Override
	public void addUserToGroup(String userId, String groupId) throws DAOException
	{
		super.executeSP(ADD_USER_TO_GROUP, 2, (call)->{
			call.setString(1, userId);
			call.setString(2, groupId);
		});
	}


	@Override
	public void removeUserFromGroup(String userId, String groupId) throws DAOException
	{
		super.executeSP(REMOVE_USER_FROM_GROUP, 2, (call)->{
			call.setString(1, userId);
			call.setString(2, groupId);
		});
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

  @Override
  public void updateFeature(UpdateFeatureRequest request) throws DAOException
  {
      super.executeSP(TOGGLE_FEATURE_FROM_GROUP, 3, (call)->{
          call.setString(1, request.getGroupId());
          call.setString(2, request.getFeatureId());
          call.setBoolean(3, request.isEnabled());
      });
  }

	@Override
	public 	List<FeatureGroup> getAllFeatureGroups() throws DAOException
	{
		List<FeatureGroup> featureGroupList = null;

		featureGroupList = super.executeSPQueryList(GET_ALL_GROUPS_WITH_FEATURES, 0, (stmt) ->
		{}, (rs, call) -> featureGroupHydrator.apply(rs));

		return featureGroupList;
	}
}
