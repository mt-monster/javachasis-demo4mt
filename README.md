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
