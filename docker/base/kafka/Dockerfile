FROM baseubuntu

RUN wget http://packages.confluent.io/archive/2.0/confluent-2.0.1-2.11.7.tar.gz -O /opt/confluent-2.0.1-2.11.7.tar.gz
RUN cd /opt/ && tar xzvf confluent-2.0.1-2.11.7.tar.gz
RUN ln -s /opt/confluent-2.0.1 /opt/kafka
