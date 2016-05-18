FROM baseubuntu

#HADOOP
RUN wget http://apache.rediris.es/hadoop/common/hadoop-2.7.2/hadoop-2.7.2.tar.gz -O /opt/hadoop-2.7.2.tar.gz
RUN cd /opt/ && tar xvf hadoop-2.7.2.tar.gz
RUN ln -s /opt/hadoop-2.7.2 /opt/hadoop

RUN export JAVA_HOME=/usr/lib/jvm/java-8-oracle/

RUN mv /opt/hadoop/etc/hadoop/core-site.xml /opt/hadoop/etc/hadoop/core-site.xml.bak
RUN echo "<configuration><property><name>fs.defaultFS</name><value>hdfs://0.0.0.0:9000</value></property></configuration>" > /opt/hadoop/etc/hadoop/core-site.xml


RUN mv /opt/hadoop/etc/hadoop/hdfs-site.xml /opt/hadoop/etc/hadoop/hdfs-site.xml.bak
RUN echo "<configuration><property><name>dfs.replication</name><value>1</value></property></configuration>" > /opt/hadoop/etc/hadoop/hdfs-site.xml 

RUN sed -i '/export JAVA_HOME=\${JAVA_HOME}/c export JAVA_HOME=/usr/lib/jvm/java-8-oracle/' /opt/hadoop/etc/hadoop/hadoop-env.sh

RUN ssh-keygen -t dsa -P '' -f ~/.ssh/id_dsa
RUN cat ~/.ssh/id_dsa.pub >> ~/.ssh/authorized_keys
RUN chmod 0600 ~/.ssh/authorized_keys

RUN echo "    StrictHostKeyChecking no" >> /etc/ssh/ssh_config

RUN /opt/hadoop/bin/hdfs namenode -format

EXPOSE 9000

CMD service ssh restart && tail -f $(echo)





