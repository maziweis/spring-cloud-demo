package com.myrule;

import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.AbstractLoadBalancerRule;
import com.netflix.loadbalancer.ILoadBalancer;
import com.netflix.loadbalancer.Server;

import java.util.List;

public class MyRandomRule extends AbstractLoadBalancerRule {
    private int total=0;
    private int currentIndex=0;
    @Override
    public void initWithNiwsConfig(IClientConfig iClientConfig) {
        // TODO Auto-generated method stub
    }

    public Server choose(ILoadBalancer lb,Object key) {
        if(lb==null)
            return null;
        Server server=null;
        while (server==null){
            if(Thread.interrupted()){
                return null;
            }
            //获取所有有效的服务的实例列表
            List<Server> upList=lb.getReachableServers();
            //获取所有的服务实例的列表
            List<Server> allList=lb.getAllServers();
            //如果没有任何实例则返回null
            int serverCount=allList.size();
            if(serverCount==0){
                return null;
            }
            //与随机策略相似，但每个服务实例只有在调用3次之后，才会调用其他服务实例
            if(total<3){
                server=upList.get(currentIndex);
                total++;
            }else {
                total=0;
                currentIndex++;
                if(currentIndex>=upList.size()){
                    currentIndex=0;
                }
            }
            if(server==null){
                Thread.yield();
                continue;
            }
            if(server.isAlive()){
                return server;
            }
            server=null;
            Thread.yield();
        }
        return server;
    }
    @Override
    public Server choose(Object key) {
        return choose(getLoadBalancer(), key);
    }
}
