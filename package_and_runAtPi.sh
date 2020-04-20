#!/bin/bash
start=$(date +%s)

echo "Hello Sky - packaging application"

source ~/.bash_profile

skipBuild=$1

if [ "$skipBuild" != "-skip" ]; then
  ./gradlew assembleDist
  echo "app packaged, copying jar to pi...."
else
  echo "skiped package, copying jar to pi...."
fi

appName="App"
fileName="$appName.tar"
path="./App/build/distributions/$fileName"
host="pi@192.168.0.19"

scp $path $host:/home/pi/Desktop || exit 1
echo "$fileName copied"

end=$(date +%s)
echo "Script Execution Time:$((end - start)) sec"


echo "logging into Raspi"

ssh $host 'bash -s' $appName <run_java.sh

echo "!!!!Script End!!!!!"
