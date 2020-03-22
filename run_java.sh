#!/usr/bin/env bash
fileName=$1

echo "executing java"

cd ~/Desktop || exit 1
java -jar "$fileName" || exit 1

exit