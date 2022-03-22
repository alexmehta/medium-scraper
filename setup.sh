#!/bin/bash
echo "pulling git repo"
git fetch origin main
git reset --hard FETCH_HEAD
git clean -df
echo "packaging jar"
mvn package -T4
java -jar target/server-0.0.1-SNAPSHOT.jar
