package com.mulesoft.agent.eventtracking.log;

import com.mulesoft.agent.common.builders.MapMessageBuilder;
import com.mulesoft.agent.common.internalhandlers.AbstractLogInternalHandler;
import com.mulesoft.agent.configuration.Configurable;
import com.mulesoft.agent.configuration.Type;
import com.mulesoft.agent.domain.tracking.AgentTrackingNotification;
import org.apache.commons.lang.StringUtils;

import javax.inject.Named;
import javax.inject.Singleton;

/**
 * <p>
 * The Log Internal handler will store all the Event Notifications produced from the
 * Mule ESB flows in a configurable log file with a rolling file policy.
 * </p>
 */
@Singleton
@Named("mule.agent.tracking.handler.log")
public class EventTrackingLogInternalHandler extends AbstractLogInternalHandler<AgentTrackingNotification>
{
    /**
     * <p>
     * The name of the file to write to.
     * If the file, or any of its parent directories, do not exist, they will be created.
     * </p>
     */
    @Configurable(value = "$MULE_HOME/logs/events.log", type = Type.DYNAMIC)
    public String fileName;

    /**
     * <p>
     * The pattern of the file name of the archived log file.
     * It will accept both a date/time pattern compatible with SimpleDateFormat and/or
     * a %i which represents an integer counter.
     * </p>
     */
    @Configurable(value = "$MULE_HOME/logs/events-%d{yyyy-dd-MM}-%i.log", type = Type.DYNAMIC)
    public String filePattern;

    /**
     * <p>
     * A log4j2 PatternLayout (https://logging.apache.org/log4j/2.x/manual/layouts.html#PatternLayout).
     * You can print the properties of the object using the %map{key} notation, for example: %map{timestamp}
     * Default: null, so all the properties will be used as a JSON object.
     * </p>
     */
    @Configurable(type = Type.DYNAMIC)
    public String pattern;

    @Override
    protected String getFileName()
    {
        return this.fileName;
    }

    @Override
    protected String getFilePattern()
    {
        return this.filePattern;
    }

    @Override
    protected String getPattern()
    {
        if (StringUtils.isEmpty(this.pattern))
        {
            return super.getPattern();
        }
        return this.pattern;
    }

    @Override
    public String getTimestampGetterName()
    {
        return "getTimestamp";
    }

    @Override
    protected MapMessageBuilder getMessageBuilder()
    {
        return new MapMessageBuilder(this.getTimestampGetterName(), this.dateFormatPattern, AgentTrackingNotification.class);
    }
}