package org.piangles.backbone.services.feature;

import org.piangles.backbone.services.Locator;
import org.piangles.backbone.services.config.Configuration;
import org.piangles.backbone.services.feature.dao.ConfiguredFeatureHydrator;
import org.piangles.backbone.services.feature.dao.FeatureManagementServiceDAO;
import org.piangles.backbone.services.feature.dao.FeatureManagementServiceDAOImpl;
import org.piangles.backbone.services.feature.handlers.AddUserToGroupHandler;
import org.piangles.backbone.services.feature.handlers.GetFeatureListForUserHandler;
import org.piangles.backbone.services.feature.handlers.RemoveUserFromGroupHandler;
import org.piangles.backbone.services.logging.LoggingService;

public class FeatureManagementServiceImpl implements FeatureToggleService
{
	private static final String COMPONENT_ID = "58185aed-c78d-4694-8e5f-f0a03d29cbd8";
	
	private final LoggingService logger  = Locator.getInstance().getLoggingService();
	
	private final FeatureManagementConfiguration ftConfig;
	
	private final FeatureManagementServiceDAO ftsDAO; 

	private final AddUserToGroupHandler addUserToGroupHandler; 
	private final RemoveUserFromGroupHandler removeUserFromGroupHandler; 
	private final GetFeatureListForUserHandler getFeatureListForUserHandler;

	public FeatureManagementServiceImpl() throws Exception
	{
		final Configuration config = Locator.getInstance().getConfigService().getConfiguration(COMPONENT_ID);
		
		ftConfig = new FeatureManagementConfiguration(config);
		
		ftsDAO = new FeatureManagementServiceDAOImpl(new ConfiguredFeatureHydrator());

		this.addUserToGroupHandler = new AddUserToGroupHandler(logger, ftConfig, ftsDAO);
		this.removeUserFromGroupHandler = new RemoveUserFromGroupHandler(logger, ftConfig, ftsDAO);

		this.getFeatureListForUserHandler = new GetFeatureListForUserHandler(logger, ftConfig, ftsDAO);
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
}