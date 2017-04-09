#!/bin/bash
mongod &

mongoimport --db tweet --collection tags --file tags.json

java -jar itau.jar && tailf logItau.txt
