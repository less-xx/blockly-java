package org.teapotech.blockly.execution.provider;

import java.util.List;

import org.teapotech.blockly.block.executor.file.UserFileResource;

public interface UserFileResourceProvider {

	UserFileResource findUserFileResourceById(String id);

	List<UserFileResource> findAll();
}
