package org.piangles.backbone.services.feature.handlers;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.piangles.backbone.services.feature.FeatureException;
import org.piangles.backbone.services.feature.FeatureManagementConfiguration;
import org.piangles.backbone.services.feature.Group;
import org.piangles.backbone.services.feature.dao.FeatureManagementServiceDAO;
import org.piangles.backbone.services.logging.LoggingService;
import org.piangles.core.dao.DAOException;
import org.piangles.core.expt.NotFoundException;

public class AddUserToGroupHandler extends AbstractHandler
{
	public AddUserToGroupHandler(LoggingService logger, FeatureManagementConfiguration fmConfig, FeatureManagementServiceDAO fmsDAO)
	{
		super(logger, fmConfig, fmsDAO);
	}

	public void handle(String userId, String groupId) throws FeatureException
	{
		try
		{
			List<Group> groups = getFMSDAO().getAllGroups();
			
			Map<String, Group> groupMap = convertToGroupMap(groups); groups.stream().collect(Collectors.toMap(g -> g.getGroupId(), g -> g));
			

			if (!groupMap.containsKey(groupId))
			{
				throw new NotFoundException("Unable to find Group with groupId: " + groupId);
			}
			
			getFMSDAO().addUserToGroup(userId, groupId);
		}
		catch(DAOException e)
		{
			String message = "Failed to add userId: " + userId + " to groupId: " + groupId;
			logger.error(message + ". Reason: " +  e.getMessage(), e);
			
			throw new FeatureException(message);
		}
	}
}
