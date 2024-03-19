package org.piangles.backbone.services.feature;

import org.piangles.core.email.EmailSupport;
import org.piangles.core.services.remoting.AbstractContainer;
import org.piangles.core.services.remoting.ContainerException;

public class FeatureManagementServiceContainer extends AbstractContainer
{
	public static void main(String[] args)
	{
		FeatureManagementServiceContainer container = new FeatureManagementServiceContainer();
		try
		{
			container.performSteps(args);
		}
		catch (ContainerException e)
		{
			EmailSupport.notify(e, e.getMessage());
			System.exit(-1);
		}
	}

	public FeatureManagementServiceContainer()
	{
		super(FeatureManagementService.NAME);
	}

	@Override
	protected Object createServiceImpl() throws ContainerException
	{
		FeatureManagementService service = null;
		try
		{
			service =  new FeatureManagementServiceImpl();
		}
		catch (Exception e)
		{
			throw new ContainerException(e);
		}

		return service;
	}
}
