package com.ycbjie.pdlib.sku;

import java.util.List;

/**
 * Created by yc on 2018/6/25.
 */

public class SkuBean {


    /**
     * spec_id : 8
     * spec_name : 颜色
     * spec_values : [{"spec_value_id":7,"name":"红色","spec_id":8},{"spec_value_id":8,"name":"黑色","spec_id":8},{"spec_value_id":15,"name":"橙色","spec_id":8},{"spec_value_id":97,"name":"红黑色","spec_id":8},{"spec_value_id":98,"name":"红橙色","spec_id":8},{"spec_value_id":100,"name":"黑橙色","spec_id":8},{"spec_value_id":101,"name":"橙红色","spec_id":8},{"spec_value_id":102,"name":"浅蓝色","spec_id":8}]
     */

    private int spec_id;
    private String spec_name;
    private List<SpecValuesBean> spec_values;

    public int getSpec_id() {
        return spec_id;
    }

    public void setSpec_id(int spec_id) {
        this.spec_id = spec_id;
    }

    public String getSpec_name() {
        return spec_name;
    }

    public void setSpec_name(String spec_name) {
        this.spec_name = spec_name;
    }

    public List<SpecValuesBean> getSpec_values() {
        return spec_values;
    }

    public void setSpec_values(List<SpecValuesBean> spec_values) {
        this.spec_values = spec_values;
    }

    public static class SpecValuesBean {
        /**
         * spec_value_id : 7
         * name : 红色
         * spec_id : 8
         */

        private int spec_value_id;
        private String name;
        private int spec_id;
        private STATUS status= STATUS.NO_SELECTED;

        public int getSpec_value_id() {
            return spec_value_id;
        }

        public void setSpec_value_id(int spec_value_id) {
            this.spec_value_id = spec_value_id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getSpec_id() {
            return spec_id;
        }

        public void setSpec_id(int spec_id) {
            this.spec_id = spec_id;
        }

        public enum  STATUS{
            NO_SELECTED,SELECTED,NONE
        }

        public STATUS getStatus() {
            return status;
        }

        public void setStatus(STATUS status) {
            this.status = status;
        }
    }
}
