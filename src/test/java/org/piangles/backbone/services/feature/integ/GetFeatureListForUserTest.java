package org.piangles.backbone.services.feature.integ;

import org.piangles.backbone.services.Locator;
import org.piangles.backbone.services.feature.FeatureList;
import org.piangles.backbone.services.feature.FeatureManagementService;
import org.piangles.core.test.AbstractServiceTestClient;

public class GetFeatureListForUserTest extends AbstractServiceTestClient
{
	public static void main(String[] args)
	{
		new GetFeatureListForUserTest().start();
	}

	@Override
	public void runImpl() throws Exception
	{
		final FeatureManagementService featureToggleService = Locator.getInstance().getFeatureManagementService();

		final FeatureList featureList = featureToggleService.getFeatureList("d4a7277");

		System.out.println(featureList);
	}
}
