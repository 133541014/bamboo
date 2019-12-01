package pers.fish.bamboo.common.model;

import java.io.Serializable;

/**
 * –¥µ„ ≤√¥∞…
 *
 * @author fish
 * @date 2019/11/30 18:15
 */
public class RPCRequest implements Serializable{

    private static final long serialVersionUID = -6976028674370836441L;

    private String className;

    private String methodName;

    private Object[] parameters;

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public Object[] getParameters() {
        return parameters;
    }

    public void setParameters(Object[] parameters) {
        this.parameters = parameters;
    }
}
