package com.huawei.demo4mt.filterPkg;

import java.util.List;

import org.apache.servicecomb.core.Invocation;
import org.apache.servicecomb.loadbalance.ServerListFilterExt;
import org.apache.servicecomb.loadbalance.ServiceCombServer;

/**
 * 一句话功能简述
 * 功能详细描述
 * @author m00416667
 * @version [版本号, ]
 * @see  [相关类/方法]
 * @since [产品/模块版本]
 * Package Name:com.huawei.demo4mt.filterPkg
 */
public class ListFilterTest implements ServerListFilterExt {
    @Override
    public List<ServiceCombServer> getFilteredListOfServers(List<ServiceCombServer> servers, Invocation invocation) {
//        System.out.println("---------------------------->>>>>>>>>>>");
        return servers;
    }
}
