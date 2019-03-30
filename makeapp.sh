#!/bin/bash
# $@ - all command line params
# $1 - first param
# $# - number of command line params

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
cp -v target/prettycsv-1.0.jar ~/Public/prettycsv-1.0.jar

# Make the Mac application bundle
javapackager -deploy \
    -title "PrettyCSV" \
    -name "PrettyCSV" \
    -appclass com.axorion.prettycsv.PrettyCSV \
    -native image \
    -Bicon=PrettyCSV.icns \
    -outdir dist \
    -outfile PrettyCSV.app \
    -srcfiles target/prettycsv-1.0.jar

echo If you wanted your icon updated, run makeicons.sh icon.png pcsv from the images directory
