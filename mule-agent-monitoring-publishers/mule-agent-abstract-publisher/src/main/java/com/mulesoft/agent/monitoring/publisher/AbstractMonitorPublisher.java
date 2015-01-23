/**
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package com.mulesoft.agent.monitoring.publisher;

import com.mulesoft.agent.AgentEnableOperationException;
import com.mulesoft.agent.configuration.Configurable;
import com.mulesoft.agent.configuration.PostConfigure;
import com.mulesoft.agent.handlers.InternalMessageHandler;
import com.mulesoft.agent.services.OnOffSwitch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public abstract class AbstractMonitorPublisher<M> implements InternalMessageHandler<M>
{
    private final static Logger LOGGER = LoggerFactory.getLogger(AbstractMonitorPublisher.class);

    @Configurable("true")
    protected boolean enabled;

    @PostConfigure
    public void createSwitcher()
    {
        enabledSwitch = OnOffSwitch.newNullSwitch(enabled);
    }

    @Override
    public void enable(boolean state) throws AgentEnableOperationException
    {
        enabledSwitch.switchTo(state);
    }

    @Override
    public boolean isEnabled()
    {
        return enabledSwitch.isEnabled();
    }

    protected OnOffSwitch enabledSwitch;

    @Override
    public boolean handle(M metrics)
    {
        if (this.isEnabled())
        {
            return doHandle(metrics);
        }

        LOGGER.debug("Skipped handling of message, handler is not enabled");

        return false;
    }

    protected abstract boolean doHandle(M metrics);

}
