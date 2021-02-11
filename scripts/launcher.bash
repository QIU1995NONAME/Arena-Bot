#!/bin/bash
readonly PWD0=$(readlink -fn "$(dirname "$0")")
readonly WORKDIR=release

set -e
cd "$PWD0"
mkdir -p "${WORKDIR}"
cd "${WORKDIR}"

java -cp "./lib/*:${PWD0}/lib/*" \
  -Dmirai.slider.captcha.supported \
  net.mamoe.mirai.console.terminal.MiraiConsoleTerminalLoader \
  "$@"
