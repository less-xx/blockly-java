package org.teapotech.blockly.block.executor;

import org.teapotech.blockly.model.Block;

public class BlockExecutionProgress {

	public enum BlockStatus {
		Waiting, Enter, Exit
	}

	private final String threadName;

	public BlockExecutionProgress(String threadName) {
		this.threadName = threadName;
	}

	private BlockStatus blockStatus = BlockStatus.Waiting;
	private String blockId;
	private String blockType;
	private String message;
	private long enterTimestamp;
	private long exitTimestamp;

	public BlockStatus getBlockStatus() {
		return blockStatus;
	}

	void setBlockStatus(BlockStatus blockStatus) {
		this.blockStatus = blockStatus;
	}

	public String getBlockId() {
		return blockId;
	}

	void setBlockId(String blockId) {
		this.blockId = blockId;
	}

	public void setBlock(Block block) {
		this.blockId = block.getId();
		this.blockType = block.getType();
	}

	public String getBlockType() {
		return blockType;
	}

	void setBlockType(String blockType) {
		this.blockType = blockType;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getThreadName() {
		return threadName;
	}

	void setEnterTimestamp(long enterTimestamp) {
		this.enterTimestamp = enterTimestamp;
	}

	public long getEnterTimestamp() {
		return enterTimestamp;
	}

	void setExitTimestamp(long exitTimestamp) {
		this.exitTimestamp = exitTimestamp;
	}

	public long getExitTimestamp() {
		return exitTimestamp;
	}
}
