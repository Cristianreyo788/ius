#!/usr/bin/env bash
app="standingoffer-service"
docker stop "$app"
docker rm -f "$app"
docker build -t $app .
