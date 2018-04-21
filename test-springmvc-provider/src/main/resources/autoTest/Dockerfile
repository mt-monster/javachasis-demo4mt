FROM docker-hub.tools.huawei.com/paas-cse/cse-jre:8u111
RUN mkdir -p /home/apps/server
COPY demo-springmvc-server/demo-springmvc-server-${version}.jar /home/apps/server
COPY lib/ /home/apps/lib
ENTRYPOINT ["java", "-jar", "/home/apps/server/demo-springmvc-server-${version}.jar"]