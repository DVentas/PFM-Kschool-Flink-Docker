#!/usr/bin/env bash
/opt/kafka/bin/zookeeper-server-start /opt/kafka/etc/kafka/zookeeper.properties &

sleep 3s

/opt/kafka/bin/kafka-server-start /opt/kafka/etc/kafka/server.properties &

sleep 5s

/opt/kafka/bin/schema-registry-start /opt/kafka/etc/schema-registry/schema-registry.properties &

service ssh restart

tail -f $(echo)
