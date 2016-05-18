FROM nodejs_base

WORKDIR /misc/mongodb_map

RUN npm install

CMD service ssh restart && npm start && tail -f $(echo)




