package com.iccm.zmmd.common;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.iccm.zmmd.common.page.PageDomain;
import com.iccm.zmmd.common.page.TableDataInfo;
import com.iccm.zmmd.common.page.TableSupport;
import com.iccm.zmmd.common.utils.StringUtils;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.wf.jwtp.provider.Token;
import org.wf.jwtp.util.SubjectUtil;

import java.util.List;

/**
 * Controller基类
 * Created by wangfan on 2018-02-22 上午 11:29.
 */
public class BaseController {

    /**
     * 获取当前登录的userId
     */
    public Integer getLoginUserId(HttpServletRequest request) {
        Token token = getLoginToken(request);
        return token == null ? null : Integer.parseInt(token.getUserId());
    }

    public Token getLoginToken(HttpServletRequest request) {
        return SubjectUtil.getToken(request);
    }

    /**
     * 设置请求分页数据
     */
    protected void startPage()
    {
        PageDomain pageDomain = TableSupport.buildPageRequest();
        Integer pageNum = pageDomain.getPageNum();
        Integer pageSize = pageDomain.getPageSize();
        if (StringUtils.isNotNull(pageNum) && StringUtils.isNotNull(pageSize))
        {
            String orderBy = SqlUtil.escapeOrderBySql(pageDomain.getOrderBy());
            PageHelper.startPage(pageNum, pageSize, orderBy);
        }
    }

    /**
     * 响应请求分页数据
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    protected TableDataInfo getDataTable(List<?> list)
    {
        TableDataInfo rspData = new TableDataInfo();
        rspData.setCode(0);
        rspData.setRows(list);
        rspData.setTotal(new PageInfo(list).getTotal());
        return rspData;
    }


}
