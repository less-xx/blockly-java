package org.teapotech.blockly.workspace;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.teapotech.blockly.block.def.BlockExecutorRegistry;
import org.teapotech.blockly.block.def.CustomBlockConfiguration;
import org.teapotech.blockly.block.executor.file.UserFileResource;
import org.teapotech.blockly.execution.provider.UserFileResourceProvider;
import org.teapotech.blockly.model.Category;
import org.teapotech.blockly.model.Workspace;
import org.teapotech.blockly.util.BlockXmlUtils;
import org.teapotech.blockly.util.JSONUtils;

public class TestBlockExecutorRegistry {

	static BlockExecutorRegistry blockDefRegistry = null;
	static UserFileResource TEST_USER_FILE_RESOURCE = new TestUserFileResource("test-user-file-resource1",
			"Test User File Resource 1", "/tmp/test");
	static List<UserFileResource> userFileResources = List.of(TEST_USER_FILE_RESOURCE);

	@BeforeAll
	static void init() throws Exception {
		blockDefRegistry = new BlockExecutorRegistry();
		blockDefRegistry.setUserFileResourceProvider(new UserFileResourceProvider() {

			@Override
			public UserFileResource findUserFileResourceById(String id) {
				return TEST_USER_FILE_RESOURCE;
			}

			@Override
			public List<UserFileResource> findAll() {
				return userFileResources;
			}
		});
		blockDefRegistry.loadBlockExecutors();
	}

	@Test
	public void testGetToolboxConfiguration() throws Exception {

		Workspace w = blockDefRegistry.getToolboxConfiguration();
		assertNotNull(w.getCategories());
		assertTrue(!w.getCategories().isEmpty());

		Category cat = w.getCategories().get(0);
		assertNotNull(cat.getBlocks());
		assertTrue(!cat.getBlocks().isEmpty());

		String xml = BlockXmlUtils.toXml(w);
		System.out.println(xml);
	}

	@Test
	public void testGetCustomBlockConfigurations() throws Exception {
		List<CustomBlockConfiguration> blockDefs = blockDefRegistry.getCustomBlockConfigurations();
		assertTrue(blockDefs.size() > 0);

		String json = JSONUtils.getJSON(blockDefs);
		System.out.println(json);
	}

	static class TestUserFileResource implements UserFileResource {

		private final String name;
		private final String id;
		private final String path;

		public TestUserFileResource(String id, String name, String path) {
			this.id = id;
			this.name = name;
			this.path = path;
		}

		@Override
		public String getName() {
			return this.name;
		}

		@Override
		public String getId() {
			return this.id;
		}

		@Override
		public boolean isDirectory() {
			return this.path.endsWith(File.separator);
		}

		@Override
		public String getPath() {
			return this.path;
		}

	}
}
