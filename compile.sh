#!/bin/sh

path_src="src/main/java/com"
path_bin="bin"
path_export_jar="Export.jar"
version="8"

# remove bin files
rm -Rf ${path_bin}

# mkdir bin
mkdir ${path_bin}

# compile
javac -d ${path_bin} \
--release ${version} \
$(find ${path_src} -name "*.java")

# jar
jar -c \
-f ${path_export_jar} \
-C ${path_bin} .

