#!/bin/bash

isSuccessfulResponse() {
	if [[ "$1" =~ "\"success\":true" ]] ; then
		return 1;
	else
		return 0;
	fi
}

if [[ $# -lt 2 ]]; then
	echo "You must supply arguments: $0 [PACKAGE] [INSTANCE:PORT]" >&2
	echo "Note that the INSTANCE:PORT is either an IP address and port or hostname and port" >&2
	exit 1
fi

uploadFilename=`basename "$1"`
packageDeleteResponse=`curl -u admin:admin -X POST http://$2/crx/packmgr/service/.json/etc/packages/$uploadFilename?cmd=delete`
if [[ $? != 0 ]] ; then
	echo "A script error occurred" >&2
	exit 1;
fi

isSuccessfulResponse "$packageDeleteResponse"
if [[ $? == 0 ]] ; then
	echo "WARNING: The package $uploadFilename doesn't exist, will upload the new one anyway..." >&2
fi

packageUploadResponse=`curl -u admin:admin -F package="@$1" http://$2/crx/packmgr/service/.json/?cmd=upload`
if [[ $? != 0 ]] ; then
	echo "A script error occurred" >&2
	exit 1;
fi

isSuccessfulResponse "$packageUploadResponse"
if [[ $? == 0 ]] ; then
	echo "Could not upload package $1:" >&2
	echo $packageUploadResponse >&2
fi

echo "Package $uploadFilename uploaded, installing..."
packageInstallResponse=`curl -u admin:admin -X POST http://$2/crx/packmgr/service/.json/etc/packages/$uploadFilename?cmd=install`
if [[ $? != 0 ]] ; then
	echo "A script error occurred" >&2
	exit 1;
fi

isSuccessfulResponse "$packageInstallResponse"
if [[ $? == 0 ]] ; then
	echo "Could not install package $uploadFilename:" >&2
	echo $packageInstallResponse >&2
fi
