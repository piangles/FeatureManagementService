package org.piangles.backbone.services.feature;

import org.piangles.core.email.EmailSupport;
import org.piangles.core.services.remoting.AbstractContainer;
import org.piangles.core.services.remoting.ContainerException;

public class FeatureToggleServiceContainer extends AbstractContainer
{
	public static void main(String[] args)
	{
		FeatureToggleServiceContainer container = new FeatureToggleServiceContainer();
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

	public FeatureToggleServiceContainer()
	{
		super(FeatureToggleService.NAME);
	}

	@Override
	protected Object createServiceImpl() throws ContainerException
	{
		FeatureToggleService service = null;
		try
		{
			service =  new FeatureServiceFactory().create();
		}
		catch (Exception e)
		{
			throw new ContainerException(e);
		}

		return service;
	}
}
