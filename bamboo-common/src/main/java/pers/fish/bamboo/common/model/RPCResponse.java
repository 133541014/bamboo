package pers.fish.bamboo.common.model;

import java.io.Serializable;

/**
 * –¥µ„ ≤√¥∞…
 *
 * @author fish
 * @date 2019/11/30 18:17
 */
public class RPCResponse implements Serializable{

    private static final long serialVersionUID = 1893908515271407633L;

    private Object result;

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }
}
