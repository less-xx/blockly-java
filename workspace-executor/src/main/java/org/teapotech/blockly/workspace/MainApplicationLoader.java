package org.teapotech.blockly.workspace;

import java.io.File;
import java.io.FileInputStream;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.teapotech.blockly.block.execute.BlockExecutorFactory;
import org.teapotech.blockly.block.execute.DefaultBlockExecutionContext;
import org.teapotech.blockly.execute.event.EventDispatcher;
import org.teapotech.blockly.model.Workspace;
import org.teapotech.blockly.user.UserDelegate;
import org.teapotech.blockly.util.JsonHelper;
import org.teapotech.blockly.workspace.executor.WorkspaceExecutor;

@SpringBootApplication
@ComponentScan(basePackages = { "org.teapotech.blockly" })
public class MainApplicationLoader implements CommandLineRunner {
    private static Logger LOG = LoggerFactory.getLogger(MainApplicationLoader.class);
    @Autowired
    BlockExecutorFactory[] blockExecutorFactories;

    @Autowired
    EventDispatcher eventDispatcher;

    @Autowired
    JsonHelper jsonHelper;

    private String userId = "system";
    private String workspaceId;
    private long instanceId = 1L;
    private File workingDir;
    private File workspaceConfigFile;
    private UserDelegate executedBy;
    private int timeoutSec = -1;

    /**
     * @param args
     */
    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(MainApplicationLoader.class);
        app.setBannerMode(Banner.Mode.OFF);
        app.setWebApplicationType(WebApplicationType.NONE);
        System.exit(SpringApplication.exit(app.run(args)));
    }

    @Override
    public void run(String... args) throws Exception {
        final Options options = new Options();
        options.addOption(new Option("d", "dir", true, "Working directory"));
        options.addOption(new Option("f", "file", true, "Workspace configuration file"));
        options.addOption(new Option("h", "help", false, "Pring command usage"));
        options.addOption(new Option("u", "user", true, "User ID that execute this workspace"));
        options.addOption(Option.builder("w").longOpt("workspaceId").desc("workspace ID. Default is random value")
                .hasArg().build());
        options.addOption(Option.builder("i").longOpt("instanceId").desc("instance ID. Default is 1").hasArg().build());
        options.addOption(Option.builder().longOpt("timeout").desc("execution timeout in seconds.").hasArg().build());
        CommandLineParser parser = new DefaultParser();
        try {
            CommandLine line = parser.parse(options, args);
            if (line.hasOption("d")) {
                String wdir = line.getOptionValue("d");
                workingDir = new File(wdir);
                if (!workingDir.exists() && !workingDir.canWrite()) {
                    throw new ParseException(workingDir.getAbsolutePath() + " doesn't exist or is not writeable.");
                }
            }
            if (line.hasOption("f")) {
                workspaceConfigFile = new File(line.getOptionValue("f"));
                if (!workspaceConfigFile.exists() && !workspaceConfigFile.canRead()) {
                    throw new ParseException(
                            workspaceConfigFile.getAbsolutePath() + " doesn't exist or is not readable.");
                }
            }
            if (line.hasOption("u")) {
                this.userId = line.getOptionValue("u");
            }
            if (line.hasOption("timeout")) {
                this.timeoutSec = Integer.parseInt(line.getOptionValue("timeout"));
            }
            if (workspaceConfigFile == null) {
                throw new ParseException("Missing workspace configuration file.");
            }
            if (workingDir == null) {
                workingDir = workspaceConfigFile.getParentFile();
                LOG.info("Working dir: {}", workingDir.getAbsolutePath());
            }
        } catch (ParseException exp) {
            System.err.println(exp.getMessage());
            HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp("run-workspace", options);
            return;
        }

        executeWorkspace();
    }

    private void executeWorkspace() throws Exception {
        try (FileInputStream fis = new FileInputStream(workspaceConfigFile);) {
            Workspace w = jsonHelper.getObject(fis, Workspace.class);
            if (workspaceId == null) {
                workspaceId = RandomStringUtils.randomAlphanumeric(12);
            }
            w.setId(workspaceId);
            DefaultBlockExecutionContext context = createBlockExecutionContext(w, instanceId);
            WorkspaceExecutor wExecutor = new WorkspaceExecutor(w, context);
            if (this.timeoutSec > 0) {
                wExecutor.setExecutionTimeout(this.timeoutSec);
            }
            wExecutor.startExecute();
            wExecutor.waitFor();
        }

    }

    private DefaultBlockExecutionContext createBlockExecutionContext(Workspace w, long instanceId) {

        File outputDir = new File(workingDir, w.getId() + "-" + instanceId);
        outputDir.mkdirs();
        LOG.info("Created output dir: {}", outputDir.getAbsolutePath());
        executedBy = new UserDelegate() {

            @Override
            public String getUserName() {
                return userId;
            }

            @Override
            public String getUserId() {
                return userId;
            }
        };
        DefaultBlockExecutionContext context = new DefaultBlockExecutionContext(w.getId(), instanceId, executedBy,
                outputDir, blockExecutorFactories);
        context.setContextObject(EventDispatcher.class, eventDispatcher);
        // context.setContextObject(KeyValueStorageProvider.class, kvStorageProvider);
        // context.setContextObject(FileStorageProvider.class, fileStorageProvider);
        // context.setContextObject(UserFileResourceProvider.class,
        // userFileResourceProvider);
        return context;
    }

}
