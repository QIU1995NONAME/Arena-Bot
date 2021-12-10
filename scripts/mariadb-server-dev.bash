#!/usr/bin/env bash

readonly ROOT_PASSWD="$(base64 -w 200 /dev/urandom | head -n1 | tr -d '+','/' | cut -c -120)"

podman run \
  -e MYSQL_ROOT_PASSWORD="${ROOT_PASSWD}" \
  -e MYSQL_DATABASE="arena-data-dev" \
  -e MYSQL_USER="arena-bot" \
  -e MYSQL_PASSWORD="arena-bot" \
  -p 13306:3306 \
  -d mariadb:10 \
  --character-set-server=utf8mb4 \
  --collation-server=utf8mb4_unicode_ci
