FROM baseubuntu

RUN wget http://apache.rediris.es/nifi/0.6.1/nifi-0.6.1-bin.tar.gz -O /opt/nifi-0.6.1-bin.tar.gz
RUN cd /opt/ && tar xvzf nifi-0.6.1-bin.tar.gz
RUN ln -s /opt/nifi-0.6.1 /opt/nifi

CMD service ssh restart && tail -f $(echo)