package com.huawei.demo4mt.filterPkg;

import org.apache.servicecomb.serviceregistry.discovery.DiscoveryContext;
import org.apache.servicecomb.serviceregistry.discovery.DiscoveryFilter;
import org.apache.servicecomb.serviceregistry.discovery.DiscoveryTreeNode;

/**
 * 一句话功能简述
 * 功能详细描述
 * @author m00416667
 * @version [版本号, ]
 * @see  [相关类/方法]
 * @since [产品/模块版本]
 * Package Name:com.huawei.demo4mt.filterPkg
 */
public class DiscoverFilterTest implements DiscoveryFilter {
    @Override public int getOrder() {
        return 0;
    }

    @Override
    public DiscoveryTreeNode discovery(DiscoveryContext discoveryContext, DiscoveryTreeNode discoveryTreeNode) {

        return discoveryTreeNode;
    }
}
