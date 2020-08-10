package org.teapotech.blockly.block.def;

import static java.lang.annotation.ElementType.TYPE;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(TYPE)
public @interface ForBlockDefinition {

	public Class<? extends BlockDefinition> value();
}
