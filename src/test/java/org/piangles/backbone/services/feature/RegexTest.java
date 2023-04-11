package org.piangles.backbone.services.feature;

public class RegexTest
{
	public static void main(String[] args)
	{
		final String REGEX = "piangles-(fe|be|both)-.*$";

		String featureId = "piangles-both-card_payment"; //should pass

		System.out.println(featureId.matches(REGEX));

		featureId = "naiy84kj"; //should fail

		System.out.println(featureId.matches(REGEX));

		featureId = "hop-fe-card"; //should fail

		System.out.println(featureId.matches(REGEX));

		featureId = "zuro-id-card"; //should fail

		System.out.println(featureId.matches(REGEX));
	}
}
