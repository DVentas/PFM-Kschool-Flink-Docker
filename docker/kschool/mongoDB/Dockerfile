FROM mongo_base

RUN echo "LC_ALL=C" >> /etc/environment

COPY backupMongo /var/backupMongo

CMD service ssh restart && tail -f $(echo)

