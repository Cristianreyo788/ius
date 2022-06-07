#!/usr/bin/env bash
app="standingoffer-service"
docker stop "$app"
docker rm -f "$app"
docker run -v /tmp:/tmp -p 8070:8080 -p 8071:9990 -p 8072:8787 --name $app $app &> $app.log &
