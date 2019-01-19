# javachasis-demo4mt
javachasis-demo4mt


  tcc:
    transaction:
      repository: com.huawei.paas.cse.tcc.repository.RedisTransactionRepository
      redis:
        host: 192.168.1.30
        port: 6379
        password: Huawei@123


  handler:
    chain:
      Provider:
        default: perf-stats,qps-flowcontrol-provider,bizkeeper-provider,tcc-provider,twoPC-provider
      Consumer:
        default: perf-stats,qps-flowcontrol-consumer,loadbalance,bizkeeper-consumer,tcc-consumer,twoPC-consumer
        
        https://huaweicse.github.io/cse-java-chassis-doc/using-cse-in-spring-boot/using-cse-in-spring-boot.html






[2018-07-20 01:48:35,963/GMT][registry-vert.x-eventloop-thread-0][ERROR]PUT /v4/default/registry/microservices/4301228a8a6511e88ea50255ac100088/instances/d73bf8058b1e11e8bbe30255ac10009b/heartbeat fail, endpoint is cse-service-center.manage.svc.cluster.local:30100, message: The timeout period of 3000ms has been exceeded while executing PUT /v4/default/registry/microservices/4301228a8a6511e88ea50255ac100088/instances/d73bf8058b1e11e8bbe30255ac10009b/heartbeat for host cse-service-center.manage.svc.cluster.local org.apache.servicecomb.serviceregistry.client.http.RestUtils.lambda$null$36(RestUtils.java:93)
[2018-07-20 01:48:35,964/GMT][registry-vert.x-eventloop-thread-0][WARN]invoke service [/v4/default/registry/microservices/4301228a8a6511e88ea50255ac100088/instances/d73bf8058b1e11e8bbe30255ac10009b/heartbeat] failed, retry. org.apache.servicecomb.serviceregistry.client.http.ServiceRegistryClientImpl.retry(ServiceRegistryClientImpl.java:86)
[2018-07-20 01:48:35,970/GMT][registry-vert.x-eventloop-thread-0][INFO]Change service center address from cse-service-center.manage.svc.cluster.local:30100 to 172.16.0.173:30100 org.apache.servicecomb.serviceregistry.client.IpPortManager.getNextAvailableAddress(IpPortManager.java:99)
[2018-07-20 01:48:36,008/GMT][Service Center Task][WARN]sc task taken more than 3048ms to execute org.apache.servicecomb.serviceregistry.task.ServiceCenterTaskMonitor.endCycle(ServiceCenterTaskMonitor.java:51)
[2018-07-20 01:48:36,963/GMT][pool-3-thread-3][INFO]Pojo model:invoke success:instance_1ikw:delaytime(s):3000 com.huawei.paas.cse.demo.pojo.server.HelloImpl.SayHello(HelloImpl.java:57)
[2018-07-20 01:48:40,969/GMT][pool-3-thread-2][INFO]Pojo model:invoke success:instance_1ikw:delaytime(s):3000 com.huawei.paas.cse.demo.pojo.server.HelloImpl.SayHello(HelloImpl.java:57)
[2018-07-20 01:48:44,972/GMT][pool-3-thread-4][INFO]Pojo model:invoke success:instance_1ikw:delaytime(s):3000 com.huawei.paas.cse.demo.pojo.server.HelloImpl.SayHello(HelloImpl.java:57)
[2018-07-20 01:48:48,976/GMT][pool-3-thread-6][INFO]Pojo model:invoke success:instance_1ikw:delaytime(s):3000 com.huawei.paas.cse.demo.pojo.server.HelloImpl.SayHello(HelloImpl.java:57)
[2018-07-20 01:48:52,984/GMT][pool-3-thread-5][INFO]Pojo model:invoke success:instance_1ikw:delaytime(s):3000 com.huawei.paas.cse.demo.pojo.server.HelloImpl.SayHello(HelloImpl.java:57)
[2018-07-20 01:48:56,986/GMT][pool-3-thread-7][INFO]Pojo model:invoke success:instance_1ikw:delaytime(s):3000 com.huawei.paas.cse.demo.pojo.server.HelloImpl.SayHello(HelloImpl.java:57)
[2018-07-20 01:49:01,003/GMT][pool-3-thread-8][INFO]Pojo model:invoke success:instance_1ikw:delaytime(s):3000 com.huawei.paas.cse.demo.pojo.server.HelloImpl.SayHello(HelloImpl.java:57)
[2018-07-20 01:49:02,961/GMT][registry-vert.x-eventloop-thread-0][ERROR]GET /v4/default/registry/instances?rev=133844.2&appId=default&serviceName=SERVICECENTER&version=0.0.0%2B fail, endpoint is cse-service-center.manage.svc.cluster.local:30100, message: The timeout period of 30000ms has been exceeded while executing GET /v4/default/registry/instances?rev=133844.2&appId=default&serviceName=SERVICECENTER&version=0.0.0%2B for host cse-service-center.manage.svc.cluster.local org.apache.servicecomb.serviceregistry.client.http.RestUtils.lambda$null$36(RestUtils.java:93)
[2018-07-20 01:49:02,962/GMT][registry-vert.x-eventloop-thread-0][WARN]invoke service [/v4/default/registry/instances] failed, retry. org.apache.servicecomb.serviceregistry.client.http.ServiceRegistryClientImpl.retry(ServiceRegistryClientImpl.java:86)
[2018-07-20 01:49:02,963/GMT][registry-vert.x-eventloop-thread-0][INFO]Change service center address from cse-service-center.manage.svc.cluster.local:30100 to 172.16.0.173:30100 org.apache.servicecomb.serviceregistry.client.IpPortManager.getNextAvailableAddress(IpPortManager.java:99)
[2018-07-20 01:49:03,749/GMT][registry-vert.x-eventloop-thread-2][ERROR]watcher connect to service center server failed, microservice 4301228a8a6511e88ea50255ac100088, connection is closed accidentally org.apache.servicecomb.serviceregistry.client.http.ServiceRegistryClientImpl.watchErrorHandler(ServiceRegistryClientImpl.java:639)
[2018-07-20 01:49:03,751/GMT][registry-vert.x-eventloop-thread-2][INFO]read exception event, message is :connection is closed accidentally org.apache.servicecomb.serviceregistry.task.ServiceCenterTask.onExceptionEvent(ServiceCenterTask.java:71)
[2018-07-20 01:49:03,751/GMT][registry-vert.x-eventloop-thread-2][ERROR]ws close error. org.apache.servicecomb.serviceregistry.client.http.WebsocketUtils.lambda$null$28(WebsocketUtils.java:65)
java.lang.IllegalStateException: WebSocket is closed
        at io.vertx.core.http.impl.WebSocketImplBase.checkClosed(WebSocketImplBase.java:253)
        at io.vertx.core.http.impl.WebSocketImplBase.close(WebSocketImplBase.java:99)
        at org.apache.servicecomb.serviceregistry.client.http.WebsocketUtils.lambda$null$28(WebsocketUtils.java:63)
        at io.vertx.core.impl.ContextImpl.lambda$wrapTask$2(ContextImpl.java:344)
        at io.netty.util.concurrent.AbstractEventExecutor.safeExecute(AbstractEventExecutor.java:163)
        at io.netty.util.concurrent.SingleThreadEventExecutor.runAllTasks(SingleThreadEventExecutor.java:403)
        at io.netty.channel.nio.NioEventLoop.run(NioEventLoop.java:463)
        at io.netty.util.concurrent.SingleThreadEventExecutor$5.run(SingleThreadEventExecutor.java:858)
        at java.lang.Thread.run(Thread.java:745)
[2018-07-20 01:49:05,005/GMT][pool-3-thread-1][INFO]Pojo model:invoke success:instance_1ikw:delaytime(s):3000 com.huawei.paas.cse.demo.pojo.server.HelloImpl.SayHello(HelloImpl.java:57)
[2018-07-20 01:49:09,009/GMT][pool-3-thread-3][INFO]Pojo model:invoke success:instance_1ikw:delaytime(s):3000 com.huawei.paas.cse.demo.pojo.server.HelloImpl.SayHello(HelloImpl.java:57)
[2018-07-20 01:49:13,012/GMT][pool-3-thread-2][INFO]Pojo model:invoke success:instance_1ikw:delaytime(s):3000 com.huawei.paas.cse.demo.pojo.server.HelloImpl.SayHello(HelloImpl.java:57)
[2018-07-20 01:49:17,017/GMT][pool-3-thread-4][INFO]Pojo model:invoke success:instance_1ikw:delaytime(s):3000 com.huawei.paas.cse.demo.pojo.server.HelloImpl.SayHello(HelloImpl.java:57)
[2018-07-20 01:49:21,020/GMT][pool-3-thread-6][INFO]Pojo model:invoke success:instance_1ikw:delaytime(s):3000 com.huawei.paas.cse.demo.pojo.server.HelloImpl.SayHello(HelloImpl.java:57)
[2018-07-20 01:49:25,023/GMT][pool-3-thread-5][INFO]Pojo model:invoke success:instance_1ikw:delaytime(s):3000 com.huawei.paas.cse.demo.pojo.server.HelloImpl.SayHello(HelloImpl.java:57)
[2018-07-20 01:49:29,026/GMT][pool-3-thread-7][INFO]Pojo model:invoke success:instance_1ikw:delaytime(s):3000 com.huawei.paas.cse.demo.pojo.server.HelloImpl.SayHello(HelloImpl.java:57)
[2018-07-20 01:49:33,034/GMT][pool-3-thread-8][INFO]Pojo model:invoke success:instance_1ikw:delaytime(s):3000 com.huawei.paas.cse.demo.pojo.server.HelloImpl.SayHello(HelloImpl.java:57)
[2018-07-20 01:49:33,037/GMT][registry-vert.x-eventloop-thread-0][ERROR]PUT /v4/default/registry/microservices/4301228a8a6511e88ea50255ac100088/instances/d73bf8058b1e11e8bbe30255ac10009b/heartbeat fail, endpoint is cse-service-center.manage.svc.cluster.local:30100, message: Connection was closed org.apache.servicecomb.serviceregistry.client.http.RestUtils.lambda$null$36(RestUtils.java:93)
[2018-07-20 01:49:33,037/GMT][registry-vert.x-eventloop-thread-0][WARN]invoke service [/v4/default/registry/microservices/4301228a8a6511e88ea50255ac100088/instances/d73bf8058b1e11e8bbe30255ac10009b/heartbeat] failed, retry. org.apache.servicecomb.serviceregistry.client.http.ServiceRegistryClientImpl.retry(ServiceRegistryClientImpl.java:86)
[2018-07-20 01:49:33,038/GMT][registry-vert.x-eventloop-thread-0][INFO]Change service center address from 172.16.0.173:30100 to 172.16.0.155:30100 org.apache.servicecomb.serviceregistry.client.IpPortManager.getNextAvailableAddress(IpPortManager.java:99)
[2018-07-20 01:49:33,038/GMT][registry-vert.x-eventloop-thread-0][ERROR]GET /v4/default/registry/instances?rev=133844.2&appId=default&serviceName=SERVICECENTER&version=0.0.0%2B fail, endpoint is cse-service-center.manage.svc.cluster.local:30100, message: Connection was closed org.apache.servicecomb.serviceregistry.client.http.RestUtils.lambda$null$36(RestUtils.java:93)
[2018-07-20 01:49:33,038/GMT][registry-vert.x-eventloop-thread-0][WARN]invoke service [/v4/default/registry/instances] failed, retry. org.apache.servicecomb.serviceregistry.client.http.ServiceRegistryClientImpl.retry(ServiceRegistryClientImpl.java:86)
[2018-07-20 01:49:33,048/GMT][registry-vert.x-eventloop-thread-0][INFO]Change service center address from 172.16.0.173:30100 to 172.16.0.155:30100 org.apache.servicecomb.serviceregistry.client.IpPortManager.getNextAvailableAddress(IpPortManager.java:99)
[2018-07-20 01:49:33,068/GMT][registry-vert.x-eventloop-thread-2][INFO]watching microservice 4301228a8a6511e88ea50255ac100088 successfully, the chosen service center address is 172.16.0.173:30100 org.apache.servicecomb.serviceregistry.client.http.ServiceRegistryClientImpl.lambda$watch$21(ServiceRegistryClientImpl.java:535)
[2018-07-20 01:49:36,049/GMT][registry-vert.x-eventloop-thread-0][ERROR]PUT /v4/default/registry/microservices/4301228a8a6511e88ea50255ac100088/instances/d73bf8058b1e11e8bbe30255ac10009b/heartbeat fail, endpoint is 172.16.0.155:30100, message: The timeout period of 3000ms has been exceeded while executing PUT /v4/default/registry/microservices/4301228a8a6511e88ea50255ac100088/instances/d73bf8058b1e11e8bbe30255ac10009b/heartbeat for host 172.16.0.155 org.apache.servicecomb.serviceregistry.client.http.RestUtils.lambda$null$36(RestUtils.java:93)
[2018-07-20 01:49:36,049/GMT][registry-vert.x-eventloop-thread-0][WARN]invoke service [/v4/default/registry/microservices/4301228a8a6511e88ea50255ac100088/instances/d73bf8058b1e11e8bbe30255ac10009b/heartbeat] failed, retry. org.apache.servicecomb.serviceregistry.client.http.ServiceRegistryClientImpl.retry(ServiceRegistryClientImpl.java:86)
[2018-07-20 01:49:36,049/GMT][registry-vert.x-eventloop-thread-0][INFO]Change service center address from 172.16.0.155:30100 to cse-service-center.manage.svc.cluster.local:30100 org.apache.servicecomb.serviceregistry.client.IpPortManager.getNextAvailableAddress(IpPortManager.java:99)
[2018-07-20 01:49:37,038/GMT][pool-3-thread-1][INFO]Pojo model:invoke success:instance_1ikw:delaytime(s):3000 com.huawei.paas.cse.demo.pojo.server.HelloImpl.SayHello(HelloImpl.java:57)
[2018-07-20 01:49:41,041/GMT][pool-3-thread-3][INFO]Pojo model:invoke success:instance_1ikw:delaytime(s):3000 com.huawei.paas.cse.demo.pojo.server.HelloImpl.SayHello(HelloImpl.java:57)
[2018-07-20 01:49:43,065/GMT][registry-vert.x-eventloop-thread-0][ERROR]PUT /v4/default/registry/microservices/4301228a8a6511e88ea50255ac100088/instances/d73bf8058b1e11e8bbe30255ac10009b/heartbeat fail, endpoint is 172.16.0.155:30100, message: Failed to create SSL connection org.apache.servicecomb.serviceregistry.client.http.RestUtils.lambda$null$36(RestUtils.java:93)
[2018-07-20 01:49:43,066/GMT][registry-vert.x-eventloop-thread-0][WARN]invoke service [/v4/default/registry/microservices/4301228a8a6511e88ea50255ac100088/instances/d73bf8058b1e11e8bbe30255ac10009b/heartbeat] failed, retry. org.apache.servicecomb.serviceregistry.client.http.ServiceRegistryClientImpl.retry(ServiceRegistryClientImpl.java:86)
[2018-07-20 01:49:43,067/GMT][registry-vert.x-eventloop-thread-0][INFO]Change service center address from cse-service-center.manage.svc.cluster.local:30100 to 172.16.0.173:30100 org.apache.servicecomb.serviceregistry.client.IpPortManager.getNextAvailableAddress(IpPortManager.java:99)
[2018-07-20 01:49:43,068/GMT][registry-vert.x-eventloop-thread-0][ERROR]GET /v4/default/registry/instances?rev=133844.2&appId=default&serviceName=SERVICECENTER&version=0.0.0%2B fail, endpoint is 172.16.0.155:30100, message: Failed to create SSL connection org.apache.servicecomb.serviceregistry.client.http.RestUtils.lambda$null$36(RestUtils.java:93)
[2018-07-20 01:49:43,068/GMT][registry-vert.x-eventloop-thread-0][WARN]invoke service [/v4/default/registry/instances] failed, retry. org.apache.servicecomb.serviceregistry.client.http.ServiceRegistryClientImpl.retry(ServiceRegistryClientImpl.java:86)
[2018-07-20 01:49:43,069/GMT][registry-vert.x-eventloop-thread-0][INFO]Change service center address from 172.16.0.155:30100 to 172.16.0.173:30100 org.apache.servicecomb.serviceregistry.client.IpPortManager.getNextAvailableAddress(IpPortManager.java:99)
[2018-07-20 01:49:44,119/GMT][registry-vert.x-eventloop-thread-2][INFO]microservice default/SERVICECENTER/1.0.0 instance eb6cb2e58b0511e8bbe30255ac10009b, ping first endpoint [rest://172.16.0.155:30100?sslEnabled=true] success, will not UNREGISTER it org.apache.servicecomb.serviceregistry.task.MicroserviceWatchTask.onMicroserviceInstanceChanged(MicroserviceWatchTask.java:97)
[2018-07-20 01:49:45,048/GMT][pool-3-thread-2][INFO]Pojo model:invoke success:instance_1ikw:delaytime(s):3000 com.huawei.paas.cse.demo.pojo.server.HelloImpl.SayHello(HelloImpl.java:57)
[2018-07-20 01:49:49,050/GMT][pool-3-thread-4][INFO]Pojo model:invoke success:instance_1ikw:delaytime(s):3000 com.huawei.paas.cse.demo.pojo.server.HelloImpl.SayHello(HelloImpl.java:57)
[2018-07-20 01:49:53,055/GMT][pool-3-thread-6][INFO]Pojo model:invoke success:instance_1ikw:delaytime(s):3000 com.huawei.paas.cse.demo.pojo.server.HelloImpl.SayHello(HelloImpl.java:57)
[2018-07-20 01:49:57,057/GMT][pool-3-thread-5][INFO]Pojo model:invoke success:instance_1ikw:delaytime(s):3000 com.huawei.paas.cse.demo.pojo.server.HelloImpl.SayHello(HelloImpl.java:57)
[2018-07-20 01:50:01,065/GMT][pool-3-thread-7][INFO]Pojo model:invoke success:instance_1ikw:delaytime(s):3000 com.huawei.paas.cse.demo.pojo.server.HelloImpl.SayHello(HelloImpl.java:57)
[2018-07-20 01:50:02,973/GMT][Service Center Task][INFO]find instances[1] from service center success. service=default/SERVICECENTER/0.0.0+ org.apache.servicecomb.serviceregistry.registry.AbstractServiceRegistry.findServiceInstances(AbstractServiceRegistry.java:256)
[2018-07-20 01:50:02,974/GMT][Service Center Task][INFO]service id=943f7d6384da11e8abf40255ac1000a0, instance id=edb8cf5a8b0511e8ba090255ac1000ad, endpoints=[rest://172.16.0.173:30100?sslEnabled=true] org.apache.servicecomb.serviceregistry.registry.AbstractServiceRegistry.findServiceInstances(AbstractServiceRegistry.java:262)
[2018-07-20 01:50:02,975/GMT][Service Center Task][INFO]set instances, appId=default, microserviceName=SERVICECENTER, versionRule=latest, instanceId=edb8cf5a8b0511e8ba090255ac1000ad, endpoints=[rest://172.16.0.173:30100?sslEnabled=true]. org.apache.servicecomb.serviceregistry.consumer.MicroserviceVersionRule.lambda$setInstances$5(MicroserviceVersionRule.java:146)
