package org.teapotech.blockly.workspace;

public class TestBlockExecutorRegistry {

//    static BlockRegistry blockDefRegistry = null;
//    static UserFileResource TEST_USER_FILE_RESOURCE = new TestUserFileResource("test-user-file-resource1",
//            "Test User File Resource 1", "/tmp/test");
//    static List<UserFileResource> userFileResources = List.of(TEST_USER_FILE_RESOURCE);
//
//    @BeforeAll
//    static void init() throws Exception {
//        blockDefRegistry = new BlockRegistry(JsonHelper.builder().build());
//        blockDefRegistry.setUserFileResourceProvider(new UserFileResourceProvider() {
//
//            @Override
//            public UserFileResource findUserFileResourceById(String id) {
//                return TEST_USER_FILE_RESOURCE;
//            }
//
//            @Override
//            public List<UserFileResource> findAll() {
//                return userFileResources;
//            }
//        });
//        blockDefRegistry.loadBlockExecutors();
//    }
//
//    @Test
//    public void testGetToolboxConfiguration() throws Exception {
//
//        Workspace w = blockDefRegistry.getToolboxConfiguration();
//        assertNotNull(w.getCategories());
//        assertTrue(!w.getCategories().isEmpty());
//
//        Category cat = w.getCategories().get(0);
//        assertNotNull(cat.getBlocks());
//        assertTrue(!cat.getBlocks().isEmpty());
//
//        String xml = BlockXmlUtils.toXml(w);
//        System.out.println(xml);
//    }
//
//    @Test
//    public void testGetCustomBlockConfigurations() throws Exception {
//        List<CustomBlockConfiguration> blockDefs = blockDefRegistry.getCustomBlockConfigurations();
//        assertTrue(blockDefs.size() > 0);
//
//        String json = JSONUtils.getJSON(blockDefs);
//        System.out.println(json);
//    }
//
//    static class TestUserFileResource implements UserFileResource {
//
//        private final String name;
//        private final String id;
//        private final String path;
//
//        public TestUserFileResource(String id, String name, String path) {
//            this.id = id;
//            this.name = name;
//            this.path = path;
//        }
//
//        @Override
//        public String getName() {
//            return this.name;
//        }
//
//        @Override
//        public String getId() {
//            return this.id;
//        }
//
//        @Override
//        public boolean isDirectory() {
//            return this.path.endsWith(File.separator);
//        }
//
//        @Override
//        public String getPath() {
//            return this.path;
//        }
//
//    }
}
