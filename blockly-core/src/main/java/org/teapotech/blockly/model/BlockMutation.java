/**
 * 
 */
package org.teapotech.blockly.model;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * @author jiangl
 *
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BlockMutation {

    private Integer items;

    private Integer elseif;

    private Integer _else;

    public Integer getItems() {
        return items;
    }

    public void setItems(Integer items) {
        this.items = items;
    }

    public Integer getElseif() {
        return elseif;
    }

    public void setElseif(Integer elseif) {
        this.elseif = elseif;
    }

    public Integer getElse() {
        return _else;
    }

    public void setElse(Integer _else) {
        this._else = _else;
    }

}
