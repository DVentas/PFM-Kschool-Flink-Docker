FROM baseubuntu

RUN wget http://apache.rediris.es/flink/flink-1.0.0/flink-1.0.0-bin-hadoop27-scala_2.11.tgz -O /opt/flink-1.0.0-bin-hadoop27-scala_2.11.tgz
RUN cd /opt/ && tar xvf flink-1.0.0-bin-hadoop27-scala_2.11.tgz
RUN ln -s /opt/flink-1.0.0 /opt/flink

RUN export JAVA_HOME=/usr/lib/jvm/java-8-oracle/

CMD service ssh restart && tail -f $(echo)





