FROM baseubuntu

RUN wget https://deb.nodesource.com/setup_5.x -O /opt/setup.sh
RUN chmod +x /opt/setup.sh
RUN /opt/setup.sh

RUN apt-get install -y nodejs

RUN npm install -g express

RUN npm install -g express-generator

CMD service ssh restart && tail -f $(echo)