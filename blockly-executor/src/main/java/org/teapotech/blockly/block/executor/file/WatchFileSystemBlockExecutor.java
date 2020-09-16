package org.teapotech.blockly.block.executor.file;

import java.io.File;

import org.apache.commons.io.monitor.FileAlterationListener;
import org.apache.commons.io.monitor.FileAlterationListenerAdaptor;
import org.apache.commons.io.monitor.FileAlterationMonitor;
import org.apache.commons.io.monitor.FileAlterationObserver;
import org.apache.commons.lang3.StringUtils;
import org.teapotech.blockly.block.def.annotation.BlockDef;
import org.teapotech.blockly.block.def.file.WatchFileSystemBlock;
import org.teapotech.blockly.block.event.NamedBlockEvent;
import org.teapotech.blockly.block.executor.AbstractBlockExecutor;
import org.teapotech.blockly.block.executor.BlockExecutionContext;
import org.teapotech.blockly.event.EventDispatcher;
import org.teapotech.blockly.exception.BlockExecutionException;
import org.teapotech.blockly.execution.provider.UserFileResourceProvider;
import org.teapotech.blockly.model.Block;
import org.teapotech.blockly.model.BlockValue;
import org.teapotech.blockly.model.Field;

@BlockDef(value = WatchFileSystemBlock.class)
public class WatchFileSystemBlockExecutor extends AbstractBlockExecutor {

	public WatchFileSystemBlockExecutor(Block block) {
		super(block);
	}

	public WatchFileSystemBlockExecutor(BlockValue blockValue) {
		super(blockValue);
	}

	@Override
	protected Object doExecute(BlockExecutionContext context) throws Exception {
		Field eventField = block.getFieldByName("EVENT", null);
		final String eventName = eventField.getValue();

		Field fileResFeild = block.getFieldByName("FILE_RESOURCE", null);
		if (fileResFeild == null || StringUtils.isBlank(fileResFeild.getValue())) {
			throw new BlockExecutionException("Missing FILE_RESOURCE field value.");
		}
		UserFileResourceProvider userFileResourceProvider = context.getContextObject(UserFileResourceProvider.class);
		UserFileResource userFileRes = userFileResourceProvider.findUserFileResourceById(fileResFeild.getId());
		if (userFileRes == null) {
			throw new BlockExecutionException("Cannot find user file resource by id");
		}

		FileAlterationObserver observer = new FileAlterationObserver(userFileRes.getPath());
		FileAlterationMonitor monitor = new FileAlterationMonitor(5000);
		FileAlterationListener listener = new FileAlterationListenerAdaptor() {
			@Override
			public void onFileCreate(File file) {
				LOG.debug("onFileCreate {}", file.getName());
				try {
					dispatchEvent(eventName, WatchFileSystemBlock.EVENT_CREATED, file, context);
				} catch (Exception e) {
					LOG.error(e.getMessage(), e);
				}
			}

			@Override
			public void onFileDelete(File file) {
				LOG.debug("onFileDelete {}", file.getName());
				try {
					dispatchEvent(eventName, WatchFileSystemBlock.EVENT_DELETED, file, context);
				} catch (Exception e) {
					LOG.error(e.getMessage(), e);
				}
			}

			@Override
			public void onFileChange(File file) {
				LOG.debug("onFileChange {}", file.getName());
				try {
					dispatchEvent(eventName, WatchFileSystemBlock.EVENT_CHANGED, file, context);
				} catch (Exception e) {
					LOG.error(e.getMessage(), e);
				}
			}
		};
		observer.addListener(listener);
		monitor.addObserver(observer);
		monitor.start();
		LOG.info("Start watching {}", observer.getDirectory());
		return null;
	}

	private void dispatchEvent(String eventName, String event, File file, BlockExecutionContext context)
			throws Exception {

		NamedBlockEvent evt = new NamedBlockEvent(context.getWorkspaceId(), block.getType(), block.getId());
		evt.setEventName(eventName);
		evt.setParameter(new WatchFileEvent(event, file));
		context.getContextObject(EventDispatcher.class).dispatchBlockEvent(evt);
	}

	public static class WatchFileEvent {
		final String event;
		final File file;

		WatchFileEvent(String event, File file) {
			this.event = event;
			this.file = file;
		}

		public String getEvent() {
			return event;
		}

		public File getFile() {
			return file;
		}

		@Override
		public String toString() {
			return event + ": " + file.getName();
		}

	}
}
