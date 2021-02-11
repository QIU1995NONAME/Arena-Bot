#!/usr/bin/env bash
echo "MODIFY 'DATADIR_MARIADB' FIRST AND REMOVE THIS LINE!" && exit 1
########################################################################################################################
readonly DATADIR_MARIADB="/tmp/mariadb/data"
########################################################################################################################

docker run \
  -e MYSQL_RANDOM_ROOT_PASSWORD="yes" \
  -v ${DATADIR_MARIADB}:/var/lib/mysql \
  -p 13306:3306 \
  -d mariadb:10 \
  --character-set-server=utf8mb4 \
  --collation-server=utf8mb4_unicode_ci
