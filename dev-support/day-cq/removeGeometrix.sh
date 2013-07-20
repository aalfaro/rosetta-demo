#!/bin/bash

if [ "$1" == "" ] || [ "$2" == "" ] || [ "$3" == "" ]; then
	echo "Usage: $0 HOST PORT ADMIN_PASSWORD"
else
	curl -u admin:$3 -F":operation=delete" -F":applyTo=/apps/geometrixx" -F":applyTo=/content/campaigns/geometrixx" -F":applyTo=/content/dam/geometrixx" -F":applyTo=/content/geometrixx" -F":applyTo=/content/geometrixx_mobile" -F":applyTo=/content/usergenerated/content/geometrixx" -F":applyTo=/etc/blueprints/geometrixx" -F":applyTo=/etc/designs/geometrixx" -F":applyTo=/etc/scaffolding/geometrixx" -F":applyTo=/etc/segmentation/geometrixx" -F":applyTo=/var/dam/geometrixx" http://$1:$2
fi;
echo;
