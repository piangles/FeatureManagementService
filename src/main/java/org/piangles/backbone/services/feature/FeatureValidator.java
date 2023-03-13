package org.piangles.backbone.services.feature;

import java.util.List;

/**
 * Given a Feature validates it
 * Rule 1. Ensure Id is sticking to the format.
 * Rule 2. Make sure the booleans are correct. 
 *
 */
public class FeatureValidator
{

	private static final String FEATURE_ID_PREFIX = "piangles-(fe|be|both)-.*$";
	private List<String> errorMessages;

	private final Feature feature;

	public FeatureValidator(Feature feature)
	{
		this.feature = feature;
	}

	public boolean isValid()
	{
		return !validateIdFormat() || !validateFeatureState();
	}

	private boolean validateIdFormat()
	{
		final boolean valid;
		final String errorMessage = "Invalid FeatureId format.";

		valid = feature.getFeatureId().matches(FEATURE_ID_PREFIX);

		if(!valid)
		{
			errorMessages.add(errorMessage);
		}

		return valid;
	}

	public String getErrorMessage()
	{
		return errorMessages.get(0);
	}

	private boolean validateFeatureState()
	{
		boolean valid = true;

		final String errorMessageSuffix = "IsViewable: " + feature.isViewable() + " IsEnabled: " + feature.isEnabled()
				+ " IsActionable: " + feature.isActionable();
		String errorMessage = "";

		if(feature.isEnabled() && !feature.isViewable())
		{
			valid = false;
			errorMessage = "If Feature is enabled, it should be automatically viewable.".concat(errorMessageSuffix);
		}

		if(feature.isActionable() && (!feature.isEnabled() || !feature.isViewable()))
		{
			valid = false;
			errorMessage = "If Feature is actionable, it should be enabled and viewable.".concat(errorMessageSuffix);
		}

		if(!valid)
		{
			errorMessages.add(errorMessage);
		}

		return valid;
	}



}
