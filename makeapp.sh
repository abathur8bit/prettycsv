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
cp -v target/prettycsv-$VER.jar ~/Public/prettycsv-$VER.jar

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

cp -v target/prettycsv-$VER.jar ~/Public/prettycsv/target
echo Run makeexe.bat on Windows to create Windows version.
# echo If you wanted your icon updated, run makeicons.sh icon.png pcsv from the images directory
fi
