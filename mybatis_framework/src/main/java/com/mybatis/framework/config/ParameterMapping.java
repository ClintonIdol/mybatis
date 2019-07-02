/**
 * Author:   LiXiaoPeng
 * Date:     2019/6/30 18:07
 * Description:
 */
package com.mybatis.framework.config;

public class ParameterMapping {

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ParameterMapping(String name) {
        this.name = name;
    }
}
