#!/bin/bash
# $@ - all command line params
# $1 - first param
# $# - number of command line params

mvn package

cp -v target/prettycsv-1.0.jar ~/Public/prettycsv-1.0.jar

javapackager -deploy \
    -title "PrettyCSV" \
    -name "PrettyCSV" \
    -appclass com.axorion.prettycsv.PrettyCSV \
    -native image \
    -Bicon=PrettyCSV.icns \
    -outdir dist \
    -outfile PrettyCSV.app \
    -srcfiles target/prettycsv-1.0.jar
