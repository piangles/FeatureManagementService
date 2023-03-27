package org.piangles.backbone.services.feature.integ;

import org.piangles.backbone.services.Locator;
import org.piangles.backbone.services.feature.FeatureList;
import org.piangles.backbone.services.feature.FeatureToggleService;
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
		final FeatureToggleService featureToggleService = Locator.getInstance().getFeatureToggleService();

		final FeatureList featureList = featureToggleService.getFeatures("d4a7277", "bizId");

		System.out.println(featureList);
	}
}
