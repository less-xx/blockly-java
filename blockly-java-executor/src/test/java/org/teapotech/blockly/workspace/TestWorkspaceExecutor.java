/**
 * 
 */
package org.teapotech.blockly.workspace;

import java.io.File;
import java.io.InputStream;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.teapotech.blockly.block.def.BlockExecutorRegistry;
import org.teapotech.blockly.block.executor.BlockExecutorFactory;
import org.teapotech.blockly.block.executor.DefaultBlockExecutionContext;
import org.teapotech.blockly.event.BlockEventListenerFactory;
import org.teapotech.blockly.event.EventDispatcher;
import org.teapotech.blockly.event.SimpleBlockEventListenerFactory;
import org.teapotech.blockly.event.SimpleEventDispatcher;
import org.teapotech.blockly.event.SimpleEventExchange;
import org.teapotech.blockly.execution.provider.DiskFileStorageProvider;
import org.teapotech.blockly.execution.provider.FileStorageProvider;
import org.teapotech.blockly.execution.provider.InMemoryKeyValueStorageProvider;
import org.teapotech.blockly.execution.provider.KeyValueStorageProvider;
import org.teapotech.blockly.model.Workspace;
import org.teapotech.blockly.util.BlockXmlUtils;
import org.teapotech.blockly.workspace.executor.WorkspaceExecutor;

/**
 * @author jiangl
 *
 */
public class TestWorkspaceExecutor {

	private static BlockExecutorFactory factory;
	private static BlockExecutorRegistry blockDefRegistry;
	private static KeyValueStorageProvider kvStorageProvider = new InMemoryKeyValueStorageProvider();
	private static String workingDirPath = System.getProperty("java.io.tmpdir") + "/tmp/workspace/test";
	private static FileStorageProvider fileStorageProvider = new DiskFileStorageProvider(workingDirPath);
	private static SimpleEventExchange eventExchange = new SimpleEventExchange();
	private static EventDispatcher eventDispatcher = new SimpleEventDispatcher(eventExchange);
	private static BlockEventListenerFactory blockEventListenerFac = new SimpleBlockEventListenerFactory(eventExchange);

	private static String testWorkspaceId = "test-workspace-id";

	@BeforeAll
	static void init() throws Exception {
		blockDefRegistry = new BlockExecutorRegistry();
		factory = BlockExecutorFactory.build(blockDefRegistry);
	}

	@Test
	public void testRunWorkspace_01() throws Exception {
		try (InputStream in = getClass().getClassLoader().getResourceAsStream("test_workspace_exec_01.xml");) {
			long testInstanceId = 1;
			Workspace w = BlockXmlUtils.loadWorkspace(in);
			File workingDir = new File(workingDirPath);
			DefaultBlockExecutionContext context = new DefaultBlockExecutionContext(testWorkspaceId, testInstanceId,
					workingDir, factory, kvStorageProvider, fileStorageProvider, eventDispatcher);
			WorkspaceExecutor wExecutor = new WorkspaceExecutor(w, context);
			wExecutor.execute();

			Thread.sleep(5000);
		}
	}

	@Test
	public void testRunWorkspace_02() throws Exception {
		try (InputStream in = getClass().getClassLoader().getResourceAsStream("test_workspace_exec_02.xml");) {
			long testInstanceId = 1;
			Workspace w = BlockXmlUtils.loadWorkspace(in);
			File workingDir = new File(workingDirPath);
			DefaultBlockExecutionContext context = new DefaultBlockExecutionContext(testWorkspaceId, testInstanceId,
					workingDir, factory, kvStorageProvider, fileStorageProvider, eventDispatcher);
			WorkspaceExecutor wExecutor = new WorkspaceExecutor(w, context);
			wExecutor.execute();

			Thread.sleep(5000);
		}
	}

}
