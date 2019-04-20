#!/bin/bash
# $@ - all command line params
# $1 - first param
# $# - number of command line params

if [ $# != 1 ]; then
    echo "makeapp <version>";
    echo "Example: ./makeapp B.1"
else

VER=$1

# Deal with icon image
#cd images
echo `pwd`
# . makeicons.sh icon.png pcsv
#cd ..
cp images/pcsv-macOS_128.png PrettyCSV.iconset/icon_128x128.png
iconutil --convert icns PrettyCSV.iconset

# Create the JAR file
cp images/pcsv-macOS_128.png src/main/java/img/icon.png
mvn package
#cp -v target/prettycsv-${VER}.jar ~/Public/prettycsv-${VER}.jar

#Check if file doesn't exists, remove the "!" to make it check for the file
if [ ! -f target/prettycsv-$VER.jar ]; then
	echo "target/prettycsv-$VER.jar doesn't exist"
	exit
fi

# Make the Mac application bundle
javapackager -deploy \
    -title "PrettyCSV" \
    -name "PrettyCSV" \
    -appclass com.axorion.prettycsv.PrettyCSV \
    -native image \
    -Bicon=PrettyCSV.icns \
    -outdir dist \
    -outfile PrettyCSV.app \
    -srcfiles target/prettycsv-$VER.jar

cp -v target/prettycsv-${VER}.jar   ~/Public/prettycsv
cp -v makeexe.bat                   ~/Public/prettycsv
cp -v images/icon.ico               ~/Public/prettycsv

# check if exe already exists and remove if so
#Check if file doesn't exists, remove the "!" to make it check for the file
if [ -f  ~/Public/prettycsv/dist/bundles/PrettyCSV-${VER}.exe ]; then
	rm ~/Public/prettycsv/dist/bundles/PrettyCSV-${VER}.exe
fi

#Check if file doesn't exists, remove the "!" to make it check for the file
if [ ! -d dist/bundles/PrettyCSV.app ]; then
	echo "App File doesn't exists"
else
    cd dist/bundles
    zip -r PrettyCSV-${VER}.zip PrettyCSV.app
    cd ../..
fi

echo Run:  makeexe.bat on Windows to create Windows version.
echo Then: cp ~/Public/prettycsv/dist/bundles/PrettyCSV-${VER}.exe dist/bundles/

# echo If you wanted your icon updated, run makeicons.sh icon.png prettycsv from the images directory
fi
