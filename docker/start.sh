#!/usr/bin/env bash
#abort on errors
set -e

mvn clean package -f ../

docker build -t baseubuntu ./base/base/
docker build -t kafka_base ./base/kafka/ 
docker build -t hadoop_base ./base/hadoop/
docker build -t mongo_base ./base/mongoDB/
docker build -t flink_base ./base/flink/
docker build -t mqtt_base ./base/mqtt/
docker build -t nifi_base ./base/nifi/
docker build -t nodejs_base ./base/nodejs/

docker-compose -f ./kschool/docker-compose.yml up -d

