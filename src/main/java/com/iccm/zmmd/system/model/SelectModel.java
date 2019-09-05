package com.iccm.zmmd.system.model;

/**
 * Created by Administrator on 2019/9/5.
 * 下拉bean
 */
public class SelectModel {

    private String title;

    private boolean expand;

    private String value;

    private String depart;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean getExpand() {
        return expand;
    }

    public void setExpand(boolean expand) {
        this.expand = expand;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getDepart() {
        return depart;
    }

    public void setDepart(String depart) {
        this.depart = depart;
    }
}
