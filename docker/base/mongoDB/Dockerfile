FROM baseubuntu

# Install community edition of MongoDB
# from https://docs.mongodb.org/v3.2/tutorial/install-mongodb-on-ubuntu/
RUN export LC_ALL=C

RUN mkdir -p /data/db

RUN apt-key adv --keyserver hkp://keyserver.ubuntu.com:80 --recv EA312927
RUN echo "deb http://repo.mongodb.org/apt/ubuntu trusty/mongodb-org/3.2 multiverse" | tee /etc/apt/sources.list.d/mongodb-org-3.2.list

RUN apt-get update
RUN apt-get install -y --force-yes mongodb-org

# Pin current version to prevent later upgrades
RUN echo "mongodb-org hold" | dpkg --set-selections
RUN echo "mongodb-org-server hold" | dpkg --set-selections
RUN echo "mongodb-org-shell hold" | dpkg --set-selections
RUN echo "mongodb-org-mongos hold" | dpkg --set-selections
RUN echo "mongodb-org-tools hold" | dpkg --set-selections

# Bind to all interfaces not just localhost
RUN sed -i '/bindIp:\ 127\.0\.0\.1/s/^/#/' /etc/mongod.conf
# Restart with new config and ready to go

#open port
EXPOSE 27017

CMD service ssh restart && mongod -f /etc/mongod.conf

