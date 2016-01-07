/*
 * (c) 2003-2015 MuleSoft, Inc. This software is protected under international copyright
 * law. All use of this software is subject to MuleSoft's Master Subscription Agreement
 * (or other master license agreement) separately entered into in writing between you and
 * MuleSoft. If such an agreement is not in place, you may not use the software.
 */

package com.mulesoft.agent.common.internalhandler.splunk.transport.config;

import com.mulesoft.agent.AgentConfigurationException;
import com.mulesoft.agent.common.internalhandler.AbstractSplunkInternalHandler;
import org.apache.commons.lang.StringUtils;

/**
 * @author Walter Poch
 *         created on 10/23/15
 */
public class TCPTransportConfig extends AbstractSplunkConfig
{

    @Override
    public String toString()
    {
        return "TCPTransportConfig{} " + super.toString();
    }

    public static class Builder
    {
        private TCPTransportConfig config = new TCPTransportConfig();

        public Builder(AbstractSplunkInternalHandler internalHandler) throws AgentConfigurationException
        {
            try
            {
                config.setHost(internalHandler.host);
                config.setPort(internalHandler.port);
            }
            catch (Exception ex)
            {
                throw new AgentConfigurationException("There was an error reading the Splunk Configuration.", ex);
            }

            if (StringUtils.isBlank(this.config.getHost()))
            {
                throw new AgentConfigurationException("The host property cannot be blank.");
            }

            if (this.config.getPort() <= 0)
            {
                throw new AgentConfigurationException("The port property must be positive.");
            }
        }

        public TCPTransportConfig build()
        {
            return this.config;
        }
    }
}
