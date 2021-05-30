#!/usr/bin/env bash

podman run \
  -e MYSQL_ROOT_PASSWORD="root-dev" \
  -e MYSQL_DATABASE="arena-data-dev" \
  -e MYSQL_USER="arena-bot" \
  -e MYSQL_PASSWORD="arena-secret" \
  -p 13306:3306 \
  -d mariadb:10 \
  --character-set-server=utf8mb4 \
  --collation-server=utf8mb4_unicode_ci
