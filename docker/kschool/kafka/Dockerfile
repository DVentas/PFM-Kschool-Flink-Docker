FROM kafka_base


RUN echo "auto.create.topics.enable=true" >> /opt/kafka/etc/kafka/server.properties

RUN /opt/kafka/bin/zookeeper-server-start /opt/kafka/etc/kafka/zookeeper.properties &

WORKDIR /opt/kafka

CMD service ssh restart && tail -f $(echo)





