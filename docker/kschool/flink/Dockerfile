FROM flink_base

WORKDIR /opt/flink/

RUN ./bin/start-local.sh

CMD service ssh restart && ./bin/start-local.sh && tail -f $(echo)




