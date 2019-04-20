#!/bin/bash
# $@ - all command line params
# $1 - first param
# $# - number of command line params

if [ $# != 2 ]; then
    # Type your script name below
    echo "deploy.sh <version> <username>";
    echo "Example: ./deploy.sh 1.1 username"
else
    VER=$1
    USERNAME=$2

    # cd into directory so md5 doesn't include the path
    cd dist/bundles

    #Check if file doesn't exists, remove the "!" to make it check for the file
    if [ ! -f PrettyCSV-${VER}.exe ]; then
    	echo "PrettyCSV-${VER}.exe doesn't exists"
    else
        echo "Calculated hash for PrettyCSV-${VER}.exe"
        shasum -a 512 PrettyCSV-${VER}.exe > PrettyCSV-${VER}.exe.sha512.txt
        md5 PrettyCSV-${VER}.exe > PrettyCSV-${VER}.exe.md5.txt

        echo Deploying Windows version to 8BitCoder.com
        scp PrettyCSV-${VER}.exe ${USERNAME}@8BitCoder.com:www/downloads
        scp PrettyCSV-${VER}.exe.md5.txt ${USERNAME}@8BitCoder.com:www/downloads
        scp PrettyCSV-${VER}.exe.sha512.txt ${USERNAME}@8BitCoder.com:www/downloads
    fi

    #Check if file doesn't exists, remove the "!" to make it check for the file
    if [ ! -f PrettyCSV-${VER}.zip ]; then
    	echo "PrettyCSV-${VER}.zip doesn't exists"
    else
        echo "Calculated hash for PrettyCSV-${VER}.zip"
        shasum -a 512 PrettyCSV-${VER}.zip > PrettyCSV-${VER}.zip.sha512.txt
        md5 PrettyCSV-${VER}.zip > PrettyCSV-${VER}.zip.md5.txt

        echo Deploying Mac version to 8BitCoder.com
        scp PrettyCSV-${VER}.zip ${USERNAME}@8BitCoder.com:www/downloads
        scp PrettyCSV-${VER}.zip.md5.txt ${USERNAME}@8BitCoder.com:www/downloads
        scp PrettyCSV-${VER}.zip.sha512.txt ${USERNAME}@8BitCoder.com:www/downloads
    fi

    cd ../..
fi
