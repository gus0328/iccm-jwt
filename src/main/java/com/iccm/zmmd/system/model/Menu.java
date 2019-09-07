package com.iccm.zmmd.system.model;

import com.iccm.zmmd.common.annotation.Excel;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 菜单对象 menu
 *
 * @author ruoyi
 * @date 2019-09-07
 */
public class Menu extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long menuId;

    /** 菜单名称 */
    @Excel(name = "菜单名称")
    private String menuTitle;

    /** 菜单name */
    @Excel(name = "菜单name")
    private String menuName;

    /** 菜单路径 */
    @Excel(name = "菜单路径")
    private String menuUrl;

    /** 类型 */
    @Excel(name = "类型")
    private String menuType;

    /** 排序编号 */
    @Excel(name = "排序编号")
    private Integer orderNum;

    /** 父菜单id */
    @Excel(name = "父菜单id")
    private Long parentId;

    public void setMenuId(Long menuId)
    {
        this.menuId = menuId;
    }

    public Long getMenuId()
    {
        return menuId;
    }
    public void setMenuTitle(String menuTitle)
    {
        this.menuTitle = menuTitle;
    }

    public String getMenuTitle()
    {
        return menuTitle;
    }
    public void setMenuName(String menuName)
    {
        this.menuName = menuName;
    }

    public String getMenuName()
    {
        return menuName;
    }
    public void setMenuUrl(String menuUrl)
    {
        this.menuUrl = menuUrl;
    }

    public String getMenuUrl()
    {
        return menuUrl;
    }
    public void setMenuType(String menuType)
    {
        this.menuType = menuType;
    }

    public String getMenuType()
    {
        return menuType;
    }
    public void setOrderNum(Integer orderNum)
    {
        this.orderNum = orderNum;
    }

    public Integer getOrderNum()
    {
        return orderNum;
    }
    public void setParentId(Long parentId)
    {
        this.parentId = parentId;
    }

    public Long getParentId()
    {
        return parentId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
                .append("menuId", getMenuId())
                .append("menuTitle", getMenuTitle())
                .append("menuName", getMenuName())
                .append("menuUrl", getMenuUrl())
                .append("menuType", getMenuType())
                .append("orderNum", getOrderNum())
                .append("parentId", getParentId())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("updateBy", getUpdateBy())
                .append("updateTime", getUpdateTime())
                .append("remark", getRemark())
                .toString();
    }
}
