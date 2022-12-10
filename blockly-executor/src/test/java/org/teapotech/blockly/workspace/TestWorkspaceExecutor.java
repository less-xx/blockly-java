/**
 * 
 */
package org.teapotech.blockly.workspace;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.io.InputStream;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.teapotech.blockly.block.execute.BlockExecutorFactory;
import org.teapotech.blockly.block.execute.DefaultBlockExecutionContext;
import org.teapotech.blockly.block.execute.DefaultBlockExecutorFactory;
import org.teapotech.blockly.block.execute.WorkspaceExecution;
import org.teapotech.blockly.block.execute.WorkspaceExecution.Status;
import org.teapotech.blockly.execute.event.BlockEventListenerFactory;
import org.teapotech.blockly.execute.event.EventDispatcher;
import org.teapotech.blockly.execute.event.SimpleBlockEventListenerFactory;
import org.teapotech.blockly.execute.event.SimpleEventDispatcher;
import org.teapotech.blockly.execute.event.SimpleEventExchange;
import org.teapotech.blockly.execution.provider.DiskFileStorageProvider;
import org.teapotech.blockly.execution.provider.FileStorageProvider;
import org.teapotech.blockly.execution.provider.InMemoryKeyValueStorageProvider;
import org.teapotech.blockly.execution.provider.KeyValueStorageProvider;
import org.teapotech.blockly.model.Workspace;
import org.teapotech.blockly.resource.BlockOption;
import org.teapotech.blockly.resource.BlockOptionProvider;
import org.teapotech.blockly.user.UserDelegate;
import org.teapotech.blockly.util.BlockRegistry;
import org.teapotech.blockly.util.JsonHelper;
import org.teapotech.blockly.workspace.executor.WorkspaceExecutor;

/**
 * @author jiangl
 *
 */
public class TestWorkspaceExecutor {

    private static BlockExecutorFactory[] factories;
    private static BlockRegistry blockDefRegistry;
    private static JsonHelper jsonHelper = JsonHelper.newInstance().build();
    private static KeyValueStorageProvider kvStorageProvider = new InMemoryKeyValueStorageProvider();
    private static String workingDirPath = System.getProperty("java.io.tmpdir") + "/workspace/test";
    private static FileStorageProvider fileStorageProvider = new DiskFileStorageProvider(workingDirPath);
    private static SimpleEventExchange eventExchange = new SimpleEventExchange();
    private static EventDispatcher eventDispatcher = new SimpleEventDispatcher(eventExchange);
    private static BlockEventListenerFactory blockEventListenerFac = new SimpleBlockEventListenerFactory(eventExchange);
    private static UserDelegate executedBy = new UserDelegate() {

        @Override
        public String getUserName() {
            return "Test User";
        }

        @Override
        public String getUserId() {
            return "test_user";
        }

    };
//    private static UserFileResource TEST_USER_FILE_RESOURCE = new TestUserFileResource("test-user-file-resource1",
//            "Test User File Resource 1", "C:\\tmp");
//    private static List<UserFileResource> userFileResources = List.of(TEST_USER_FILE_RESOURCE);
//    private static UserFileResourceProvider userFileResourceProvider = new UserFileResourceProvider() {
//
//        @Override
//        public UserFileResource findUserFileResourceById(String id) {
//            return TEST_USER_FILE_RESOURCE;
//        }
//
//        @Override
//        public List<UserFileResource> findAll() {
//            return userFileResources;
//        }
//    };

    private static BlockOptionProvider blockOptionProvider = new BlockOptionProvider() {

        @Override
        public List<BlockOption> getOptions(String blockType) {
            return null;
        }
    };

    @BeforeAll
    static void init() throws Exception {
        blockDefRegistry = new BlockRegistry(blockOptionProvider, jsonHelper);
        blockDefRegistry.loadBlockExecutors();
        factories = new BlockExecutorFactory[] {
                DefaultBlockExecutorFactory.build(blockDefRegistry, blockEventListenerFac, jsonHelper) };
    }

    private DefaultBlockExecutionContext createBlockExecutionContext(Workspace w, long instanceId) {
        File workingDir = new File(workingDirPath, w.getId() + "-" + instanceId);
        workingDir.mkdirs();
        DefaultBlockExecutionContext context = new DefaultBlockExecutionContext(w.getId(), instanceId, executedBy,
                workingDir, factories);
        context.setContextObject(EventDispatcher.class, eventDispatcher);
        context.setContextObject(KeyValueStorageProvider.class, kvStorageProvider);
        context.setContextObject(FileStorageProvider.class, fileStorageProvider);
        // context.setContextObject(UserFileResourceProvider.class,
        // userFileResourceProvider);
        return context;
    }

    @Test
    public void testRunWorkspace_01() throws Exception {
        try (InputStream in = getClass().getClassLoader().getResourceAsStream("workspace01.json");) {
            long testInstanceId = 1;
            Workspace w = jsonHelper.getObject(in, Workspace.class);
            w.setId("testRunWorkspace_01");
            DefaultBlockExecutionContext context = createBlockExecutionContext(w, testInstanceId);
            WorkspaceExecutor wExecutor = new WorkspaceExecutor(w, context);
            wExecutor.execute();
            wExecutor.waitFor(2000);
            WorkspaceExecution execution = wExecutor.getWorkspaceExecution();
            assertEquals(Status.Succeeded, execution.getStatus());
        }
    }

    @Test
    public void testRunWorkspace_02() throws Exception {
        try (InputStream in = getClass().getClassLoader().getResourceAsStream("workspace02.json");) {
            long testInstanceId = 2;
            Workspace w = jsonHelper.getObject(in, Workspace.class);
            w.setId("testRunWorkspace_02");
            DefaultBlockExecutionContext context = createBlockExecutionContext(w, testInstanceId);
            WorkspaceExecutor wExecutor = new WorkspaceExecutor(w, context);
            wExecutor.setExecutionTimeout(2);
            wExecutor.execute();
            wExecutor.waitFor(5000);
            WorkspaceExecution execution = wExecutor.getWorkspaceExecution();
            assertEquals(Status.Timeout, execution.getStatus());
            System.out.println(execution.getMessage());
        }
    }

    @Test
    public void testRunWorkspace_03() throws Exception {
        try (InputStream in = getClass().getClassLoader().getResourceAsStream("workspace03.json");) {
            long testInstanceId = 3;
            Workspace w = jsonHelper.getObject(in, Workspace.class);
            w.setId("testRunWorkspace_03");
            DefaultBlockExecutionContext context = createBlockExecutionContext(w, testInstanceId);
            WorkspaceExecutor wExecutor = new WorkspaceExecutor(w, context);
            wExecutor.execute();
            wExecutor.waitFor(5000);
            WorkspaceExecution execution = wExecutor.getWorkspaceExecution();
            assertEquals(Status.Failed, execution.getStatus());
            System.out.println(execution.getMessage());
        }
    }

//    @Test
//    public void testRunWorkspace_02() throws Exception {
//        try (InputStream in = getClass().getClassLoader().getResourceAsStream("test_workspace_exec_02.xml");) {
//            long testInstanceId = 1;
//            Workspace w = BlockXmlUtils.loadWorkspace(in);
//            DefaultBlockExecutionContext context = createBlockExecutionContext(w, testInstanceId);
//            WorkspaceExecutor wExecutor = new WorkspaceExecutor(w, context);
//            wExecutor.execute();
//            wExecutor.waitFor(5000);
//        }
//    }
//
//    @Test
//    public void testRunWorkspace_03() throws Exception {
//        try (InputStream in = getClass().getClassLoader().getResourceAsStream("test_workspace_exec_03.xml");) {
//            long testInstanceId = 1;
//            Workspace w = BlockXmlUtils.loadWorkspace(in);
//            DefaultBlockExecutionContext context = createBlockExecutionContext(w, testInstanceId);
//            WorkspaceExecutor wExecutor = new WorkspaceExecutor(w, context);
//            wExecutor.setExecutionTimeout(1);
//            wExecutor.execute();
//            WorkspaceExecution wexec = wExecutor.getWorkspaceExecution();
//            System.out.println(wexec);
//            wExecutor.waitFor(5000);
//            wexec = wExecutor.getWorkspaceExecution();
//            System.out.println(wexec);
//        }
//    }
//
//    @Test
//    public void testFindAllPrimesInRange() throws Exception {
//        try (InputStream in = getClass().getClassLoader().getResourceAsStream("test_workspace_exec_04.xml");) {
//            long testInstanceId = 1;
//            Workspace w = BlockXmlUtils.loadWorkspace(in);
//            DefaultBlockExecutionContext context = createBlockExecutionContext(w, testInstanceId);
//            WorkspaceExecutor wExecutor = new WorkspaceExecutor(w, context);
//            wExecutor.execute();
//            wExecutor.waitFor(30000);
//            WorkspaceExecution wexec = wExecutor.getWorkspaceExecution();
//            System.out.println(wexec);
//        }
//    }
//
//    @Test
//    public void testRunWorkspace_05() throws Exception {
//        try (InputStream in = getClass().getClassLoader().getResourceAsStream("test_workspace_exec_05.xml");) {
//            long testInstanceId = 1;
//            Workspace w = BlockXmlUtils.loadWorkspace(in);
//            DefaultBlockExecutionContext context = createBlockExecutionContext(w, testInstanceId);
//            WorkspaceExecutor wExecutor = new WorkspaceExecutor(w, context);
//            wExecutor.setExecutionTimeout(1);
//            wExecutor.execute();
//            wExecutor.waitFor(5000);
//            WorkspaceExecution wexec = wExecutor.getWorkspaceExecution();
//            System.out.println(wexec);
//        }
//    }
//
//    @Test
//    public void testFindAllPrimesInRangeWithEvent() throws Exception {
//        try (InputStream in = getClass().getClassLoader().getResourceAsStream("test_workspace_exec_06.xml");) {
//            long testInstanceId = 1;
//            Workspace w = BlockXmlUtils.loadWorkspace(in);
//            DefaultBlockExecutionContext context = createBlockExecutionContext(w, testInstanceId);
//            WorkspaceExecutor wExecutor = new WorkspaceExecutor(w, context);
//            wExecutor.execute();
//            wExecutor.waitFor(30000);
//            WorkspaceExecution wexec = wExecutor.getWorkspaceExecution();
//            System.out.println(wexec);
//        }
//    }
//
//    @Test
//    public void testPauseResumeExecution() throws Exception {
//        try (InputStream in = getClass().getClassLoader().getResourceAsStream("test_workspace_exec_05.xml");) {
//            long testInstanceId = 1;
//            Workspace w = BlockXmlUtils.loadWorkspace(in);
//            DefaultBlockExecutionContext context = createBlockExecutionContext(w, testInstanceId);
//            WorkspaceExecutor wExecutor = new WorkspaceExecutor(w, context);
//            context.setDebugMode(true);
//            context.addBreakPoint("controls_if_2kHyeIyW");
//            wExecutor.execute();
//            WorkspaceExecution wexec = wExecutor.getWorkspaceExecution();
//            while (wexec.getStatus() != Status.Stopped) {
//                if (wexec.getStatus() == Status.Paused) {
//                    wExecutor.resumeExecution();
//                    System.out.println(wexec);
//                }
//                wexec = wExecutor.getWorkspaceExecution();
//                Thread.sleep(500);
//            }
//            wexec = wExecutor.getWorkspaceExecution();
//            System.out.println(wexec);
//        }
//    }
//
//    @Test
//    public void testWatchFileSystemBlock() throws Exception {
//        try (InputStream in = getClass().getClassLoader().getResourceAsStream("test_workspace_exec_07.xml");) {
//            long testInstanceId = 1;
//            Workspace w = BlockXmlUtils.loadWorkspace(in);
//            DefaultBlockExecutionContext context = createBlockExecutionContext(w, testInstanceId);
//            WorkspaceExecutor wExecutor = new WorkspaceExecutor(w, context);
//            wExecutor.execute();
//            wExecutor.waitFor(30000);
//            WorkspaceExecution wexec = wExecutor.getWorkspaceExecution();
//            System.out.println(wexec);
//            wExecutor.stopExecution();
//        }
//    }
}
