package com.meetme.plugins.jira.gerrit.workflow;

import java.util.Map;

import com.atlassian.core.util.map.EasyMap;
import com.atlassian.jira.plugin.workflow.AbstractWorkflowPluginFactory;
import com.opensymphony.workflow.loader.AbstractDescriptor;
import com.opensymphony.workflow.loader.ConditionDescriptor;

public class NoOpenReviewsConditionFactoryImpl extends AbstractWorkflowPluginFactory {
    private static final String KEY_REVERSE = "reverse";

    @SuppressWarnings("unchecked")
    @Override
    public Map<String, ?> getDescriptorParams(Map<String, Object> conditionParams) {
        if (conditionParams != null && conditionParams.containsKey(KEY_REVERSE))
        {
            return EasyMap.build(KEY_REVERSE, extractSingleParam(conditionParams, KEY_REVERSE));
        }

        return EasyMap.build();
    }

    @Override
    protected void getVelocityParamsForEdit(Map<String, Object> velocityParams, AbstractDescriptor descriptor) {
        velocityParams.put(KEY_REVERSE, isReversed(descriptor));
    }

    @Override
    protected void getVelocityParamsForInput(Map<String, Object> velocityParams) {
        // Nothing to choose from, because boolean is only ON/OFF
    }

    @Override
    protected void getVelocityParamsForView(Map<String, Object> velocityParams, AbstractDescriptor descriptor) {
        velocityParams.put(KEY_REVERSE, isReversed(descriptor));
    }

    private boolean isReversed(AbstractDescriptor descriptor) {
        if (!(descriptor instanceof ConditionDescriptor)) {
            throw new IllegalArgumentException("Descriptor must be a ConditionDescriptor.");
        }

        ConditionDescriptor conditionDescriptor = (ConditionDescriptor) descriptor;

        String value = (String) conditionDescriptor.getArgs().get(KEY_REVERSE);
        return Boolean.parseBoolean(value);
    }
}