FROM nifi_base


COPY misc/core-site.xml /opt/nifi/conf

COPY misc/hdfs-site.xml /opt/nifi/conf

COPY misc/nifi-mqtt-nar-0.5-SNAPSHOT.nar /opt/nifi/lib

COPY misc/13ffd12f-0467-4561-b06c-b9771d4bbb8e.template /opt/nifi/conf/templates/

CMD service ssh restart && /opt/nifi/bin/nifi.sh start && tail -f $(echo)