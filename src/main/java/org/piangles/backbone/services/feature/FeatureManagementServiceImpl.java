package org.piangles.backbone.services.feature;

import org.piangles.backbone.services.Locator;
import org.piangles.backbone.services.config.Configuration;
import org.piangles.backbone.services.feature.dao.ConfiguredFeatureHydrator;
import org.piangles.backbone.services.feature.dao.FeatureManagementServiceDAO;
import org.piangles.backbone.services.feature.dao.FeatureManagementServiceDAOImpl;
import org.piangles.backbone.services.feature.handlers.AddUserToGroupHandler;
import org.piangles.backbone.services.feature.handlers.GetFeatureListForUserHandler;
import org.piangles.backbone.services.feature.handlers.GetGroupsWithFeaturesHandler;
import org.piangles.backbone.services.feature.handlers.RemoveUserFromGroupHandler;
import org.piangles.backbone.services.logging.LoggingService;
import org.piangles.core.dao.DAOException;

import java.util.List;
import java.util.Map;

public class FeatureManagementServiceImpl implements FeatureToggleService
{
	private static final String COMPONENT_ID = "58185aed-c78d-4694-8e5f-f0a03d29cbd8";
	
	private final LoggingService logger  = Locator.getInstance().getLoggingService();
	
	private final FeatureManagementConfiguration ftConfig;
	
	private final FeatureManagementServiceDAO ftsDAO; 

	private final AddUserToGroupHandler addUserToGroupHandler; 
	private final RemoveUserFromGroupHandler removeUserFromGroupHandler; 
	private final GetFeatureListForUserHandler getFeatureListForUserHandler;
	
	private final GetGroupsWithFeaturesHandler getGroupsWithFeaturesHandler;

	public FeatureManagementServiceImpl() throws Exception
	{
		final Configuration config = Locator.getInstance().getConfigService().getConfiguration(COMPONENT_ID);
		
		ftConfig = new FeatureManagementConfiguration(config);
		
		ftsDAO = new FeatureManagementServiceDAOImpl(new ConfiguredFeatureHydrator());

		this.addUserToGroupHandler = new AddUserToGroupHandler(logger, ftConfig, ftsDAO);
		this.removeUserFromGroupHandler = new RemoveUserFromGroupHandler(logger, ftConfig, ftsDAO);

		this.getFeatureListForUserHandler = new GetFeatureListForUserHandler(logger, ftConfig, ftsDAO);

		this.getGroupsWithFeaturesHandler = new GetGroupsWithFeaturesHandler(logger, ftConfig, ftsDAO);
		
	}

	@Override
	public void addUserToGroup(String userId, String groupId) throws FeatureException
	{
		logger.info("Adding userId: " + userId + " to group with groupId: " + groupId);

		addUserToGroupHandler.handle(userId, groupId);
	}


	@Override
	public void removeUserFromGroup(String userId, String groupId) throws FeatureException
	{
		logger.info("Removing userId: " + userId + " from group with groupId: " + groupId);

		removeUserFromGroupHandler.handle(userId, groupId);
	}

	@Override
	public FeatureList getFeatureList(String userId) throws FeatureException
	{
		logger.info("Retrieving Features for userId: " + userId);

		return getFeatureListForUserHandler.handle(userId);
	}

    @Override
    public void updateFeature(UpdateFeatureRequest request) throws FeatureException
    {
        logger.info(String.format("Received a request to update featureId: %s of groupId: %s to enabled: %b", request.getFeatureId(), request.getGroupId(), request.isEnabled()));

        try
        {
            ftsDAO.updateFeature(request);
        }
        catch (DAOException e)
        {
            final String errMsg = String.format("Failed to update featureId: %s of groupId: %s to enabled: %b due to: %s", request.getFeatureId(), request.getGroupId(), request.isEnabled(), e.getMessage());
            logger.error(errMsg, e);
            throw new FeatureException(errMsg);
        }
    }

	public Map<String, List<Feature>> getAllGroupsWithFeatures() throws FeatureException 
	{
		logger.info("Received a request to getAllGroupsWithFeatures");
		return getGroupsWithFeaturesHandler.handle();
	}

}