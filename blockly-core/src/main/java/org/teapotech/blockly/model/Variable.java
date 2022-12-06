package org.teapotech.blockly.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record Variable(String id, String name) implements Serializable {

    public static final NullValue NULL = new NullValue();

    public static record NullValue() implements Serializable {

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj instanceof NullValue) {
                return true;
            }
            return false;
        }
    }

}
