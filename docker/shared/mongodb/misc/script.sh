#!/usr/bin/env bash

mongod -f /etc/mongod.conf &

sleep 2

echo "charge backup !!!!!!!!!!!"
mongorestore /var/backupMongo/


tail -f $(echo)
