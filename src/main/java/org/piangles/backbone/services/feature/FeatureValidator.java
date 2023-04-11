package org.piangles.backbone.services.feature;

import java.util.ArrayList;
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
	
	private final List<String> errorMessages;

	public FeatureValidator()
	{
		errorMessages = new ArrayList<>();
	}

	public boolean isValid(Feature feature)
	{
		return validateIdFormat(feature);// && validateFeatureState(feature);
	}

	public String getErrorMessage()
	{
		return errorMessages.toString();
	}

	private boolean validateIdFormat(Feature feature)
	{
		final boolean valid;
		final String errorMessage = "Invalid FeatureId format: ";

		valid = feature.getFeatureId().matches(FEATURE_ID_PREFIX);

		if(!valid)
		{
			errorMessages.add(errorMessage + feature.getFeatureId());
		}

		return valid;
	}

	@SuppressWarnings("unused")
	private boolean validateFeatureState(Feature feature)
	{
		boolean valid = true;

		final String errorMessageSuffix = "IsViewable: " + feature.isViewable() + " IsEnabled: " + feature.isEnabled() + " IsActionable: " + feature.isActionable();
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
