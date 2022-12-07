package org.teapotech.blockly.resource;

import java.util.Date;

public interface FileResource {

	String getName();

	String getId();

	boolean isDirectory();

    long getSize();

    Date lastModifiedTime();

}
