#!/bin/bash

mkdir -p ssl/api
cp /etc/letsencrypt/live/api.atticket.o-r.kr/fullchain.pem ./ssl/api
cp /etc/letsencrypt/live/api.atticket.o-r.kr/privkey.pem ./ssl/api
mkdir -p ssl/keycloak
cp /etc/letsencrypt/live/keycloak.atticket.o-r.kr/fullchain.pem ./ssl/keycloak
cp /etc/letsencrypt/live/keycloak.atticket.o-r.kr/privkey.pem ./ssl/keycloak

docker-compose -f docker-compose-dev.yml up -d
