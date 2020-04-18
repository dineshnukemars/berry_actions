#!/usr/bin/env bash
appName=$1

cd ~/Desktop || exit 1

rm -rf "$appName"

if [ -e "$appName" ]
then
    echo "$appName dir is not deleted"
else
    echo "$appName dir is deleted"
fi

tar -xf "$appName".tar
echo "extracted tar"

cd "$appName"/bin || exit

echo "executing App - $appName"
sudo chmod +x ./"$appName"
./$appName