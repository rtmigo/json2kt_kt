#!/bin/bash
set -e && cd "${0%/*}"

./gradlew build
echo '--------------------------------------------------------------------------------'
echo

java -jar build/libs/json2kt.shadow.jar "$@"
