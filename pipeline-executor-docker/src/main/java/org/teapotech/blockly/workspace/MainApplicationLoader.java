package org.teapotech.blockly.workspace;

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

import java.io.File;
import java.io.FileInputStream;

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

    private String userId = "unknown";
    private String userName = "Unknown";
    private String workspaceId;
    private long instanceId = 1L;
    private File workingDir;
    private File workspaceConfigFile;
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
        options.addOption(new Option("h", "help", false, "Print command usage"));
        options.addOption(Option.builder().longOpt("user-id").desc("User ID who run this workspace").hasArg().build());
        options.addOption(Option.builder().longOpt("user-name").desc("User name who run this workspace").hasArg().build());
        options.addOption(Option.builder("w").longOpt("workspaceId").desc("workspace ID. Default is random value")
                .hasArg().build());
        options.addOption(Option.builder("i").longOpt("instance-id").desc("instance ID. Default is 1").hasArg().build());
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
            if(line.hasOption("i")){
                this.instanceId = Long.parseLong(line.getOptionValue("i"));
            }
            if (line.hasOption("user-id")) {
                this.userId = line.getOptionValue("user-id");
            }
            if (line.hasOption("user-name")) {
                this.userName = line.getOptionValue("user-name");
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
            WorkspaceExecutor wExecutor = new WorkspaceExecutor(w, context, jsonHelper);
            if (this.timeoutSec > 0) {
                wExecutor.setExecutionTimeout(this.timeoutSec);
            }
            wExecutor.startExecute();
            wExecutor.waitFor();
        }

    }

    private DefaultBlockExecutionContext createBlockExecutionContext(Workspace w, long instanceId) {

        File outputDir = new File(workingDir, w.getId() + "-" + instanceId);
        if(outputDir.mkdirs()) {
            LOG.info("Created output dir: {}", outputDir.getAbsolutePath());
        }
        UserDelegate executedBy = new UserDelegate() {

            @Override
            public String getUserName() {
                return userName;
            }

            @Override
            public String getUserId() {
                return userId;
            }
        };
        DefaultBlockExecutionContext context = new DefaultBlockExecutionContext(w, instanceId, executedBy,
                outputDir, blockExecutorFactories);
        context.setContextObject(EventDispatcher.class, eventDispatcher);
        // context.setContextObject(KeyValueStorageProvider.class, kvStorageProvider);
        // context.setContextObject(FileStorageProvider.class, fileStorageProvider);
        // context.setContextObject(UserFileResourceProvider.class,
        // userFileResourceProvider);
        return context;
    }

}
