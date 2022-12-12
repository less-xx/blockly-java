package org.teapotech.blockly.block.execute;

import java.io.File;

import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.PatternLayout;
import ch.qos.logback.classic.joran.JoranConfigurator;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.ConsoleAppender;
import ch.qos.logback.core.FileAppender;
import ch.qos.logback.core.encoder.LayoutWrappingEncoder;

public class WorkspaceLoggerFactory {

    public static Logger createLogger(String workspaceId, long instanceId, String logLevel, File workingDir) {

        LoggerContext context = (LoggerContext) LoggerFactory.getILoggerFactory();
        JoranConfigurator configurator = new JoranConfigurator();
        configurator.setContext(context);

        ConsoleAppender<ILoggingEvent> ca = new ConsoleAppender<>();
        ca.setContext(context);
        ca.setName("wexec-console" + workspaceId + "-" + instanceId);
        LayoutWrappingEncoder<ILoggingEvent> encoder = new LayoutWrappingEncoder<ILoggingEvent>();
        encoder.setContext(context);

        PatternLayout layout = new PatternLayout();
        layout.setPattern("%d{HH:mm:ss.SSS} [%thread] %-5level %logger{36} - %msg%n");
        layout.setContext(context);
        layout.start();
        encoder.setLayout(layout);

        ca.setEncoder(encoder);
        ca.start();

        FileAppender<ILoggingEvent> f = new FileAppender<>();
        f.setEncoder(encoder);
        f.setLayout(layout);
        f.setContext(context);
        f.setFile(workingDir.getAbsolutePath() + File.separator + "workspace-exec.log");
        f.setAppend(false);
        f.setName("wexec-log-" + workspaceId + "-" + instanceId);
        f.start();

        Logger rootLogger = context.getLogger(workspaceId + "-" + instanceId);
        rootLogger.addAppender(ca);
        rootLogger.addAppender(f);
        rootLogger.setLevel(Level.valueOf(logLevel));
        return rootLogger;
    }

}
