package com.huawei.demo4mt.foudationPkg;

import java.util.List;

import com.netflix.loadbalancer.AbstractLoadBalancer;
import com.netflix.loadbalancer.ILoadBalancer;
import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.LoadBalancerStats;
import com.netflix.loadbalancer.Server;

/**
 * 一句话功能简述
 * 功能详细描述
 * @author m00416667
 * @version [版本号, ]
 * @see  [相关类/方法]
 * @since [产品/模块版本]
 * Package Name:com.huawei.demo4mt
 */
public class LoadBanlanceTest extends AbstractLoadBalancer implements IRule {
    private String name;

    private List<Server> servers;

    private IRule iRule;

    @Override public Server choose(Object key) {
        return null;
    }

    @Override public void setLoadBalancer(ILoadBalancer iLoadBalancer) {

    }

    @Override public ILoadBalancer getLoadBalancer() {
        return null;
    }


    @Override public List<Server> getServerList(ServerGroup serverGroup) {
        return null;
    }

    @Override public LoadBalancerStats getLoadBalancerStats() {
        return null;
    }

    @Override public void addServers(List<Server> newServers) {

    }

    @Override public Server chooseServer(Object key) {
        return null;
    }

    @Override public void markServerDown(Server server) {

    }

    @Override public List<Server> getServerList(boolean availableOnly) {
        return null;
    }

    @Override public List<Server> getReachableServers() {
        return null;
    }

    @Override public List<Server> getAllServers() {
        return null;
    }
}
