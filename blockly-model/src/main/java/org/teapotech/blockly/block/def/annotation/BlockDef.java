package org.teapotech.blockly.block.def.annotation;

import static java.lang.annotation.ElementType.TYPE;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.teapotech.blockly.block.def.BlockDefinition;

@Retention(RetentionPolicy.RUNTIME)
@Target(TYPE)
public @interface BlockDef {

	public Class<? extends BlockDefinition> value() default BlockDefinition.class;

	public String blockType() default "";

	public String category() default "others";

	public String style() default "";

}
