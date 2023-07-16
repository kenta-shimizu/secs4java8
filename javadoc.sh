#!/bin/sh

packages="com.shimizukenta.secs com.shimizukenta.secs.local.property 
com.shimizukenta.secs.secs2 com.shimizukenta.secs.sml com.shimizukenta.secs.gem 
com.shimizukenta.secs.hsms com.shimizukenta.secs.hsmsss com.shimizukenta.secs.hsmsgs"
path_src="./src/main/java/"
path_docs="./docs"

# remove files and mkdir
rm -Rf ${path_docs}
mkdir ${path_docs}

javadoc \
-locale en_US \
-d ${path_docs} \
--show-members public \
--source-path ${path_src} \
${packages}
