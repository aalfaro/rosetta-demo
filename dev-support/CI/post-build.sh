#! /bin/bash
basedir="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"
cd $basedir
package_path="$(ls ./../../nexus-ui/target/nexus-ui-*-AZ-UI-App.zip)"
package_name="$( basename  $package_path)"
package_name=${package_name%.*}

if [ $# -ne 1 ]
then
   echo "Usage: `basename $0` <hosts-file>"
   echo "       where <hosts-file> is the list of hostname:port to deploy the package to"
   exit 1
fi

if [ ! -f $1 ]
then
	echo "file ${1} does not exist"
	exit 1
fi

for host in $(<$1); do 
    account=$(echo ${host%@*}) 
    hostname=$(echo ${host#*@}) 

	curl -u $account -X POST http://$hostname/crx/packmgr/service/.json/etc/packages/$package_name?cmd=delete
	if [[ $? != 0 ]] ; then
		echo "A script error occurred Deleting $package_name package to $hostname" >&2
		exit 1;
	fi

    echo "Uploading $package_name package to $hostname"
    curl -u $account -F file=@$package_path -F name=$package_name http://$hostname/crx/packmgr/service.jsp
	if [[ $? != 0 ]] ; then
		echo "A script error occurred Uploading $package_name package to $hostname" >&2
		exit 1;
	fi

    echo "Installing $package_name package at $hostname"
    curl -u $account -F name=$package_name http://$hostname/crx/packmgr/service.jsp?cmd=inst
	if [[ $? != 0 ]] ; then
		echo "A script error Installing $package_name package to $hostname" >&2
		exit 1;
	fi

done