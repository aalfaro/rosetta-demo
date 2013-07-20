#! /bin/bash
user=admin
password=admin
host=""
root=""
market=""
brand=""

temp_dir=/tmp/crx_export_sh
crx_base_path=/jcr_root/etc/designs/
crx_command_base_path=/etc/designs/
source_name=.content.xml
#usage
function help {
    echo "Usage: crx_design_export.sh -h host:port -r rootfolder [-m market] [-b brand]"
}

#parameter check
if [ $# -eq 0 ] ; then
    help
    exit 0
fi
while getopts ":h:r:m:b:" optname
  do
      case "$optname" in
        "h")
            host=$OPTARG
            ;;
        "r")
            root=$OPTARG
            ;;
        "m")
            market=$OPTARG
            ;;
        "b")
            brand=$OPTARG
            ;;
        * )
            help
            exit 0
            ;;
     esac
  done

if [ "$host" = "" -o "$root" = "" ] ; then
    help
    exit 0
fi

#calculating vlt, source and target parameters from input params
command="vlt --credentials $user:$password export http://$host/crx/  $crx_command_base_path"
target="${root}${crx_base_path}"
source_path="${temp_dir}${crx_base_path}"
if [ "$market" != "" ] ; then
    command="$command$market/"
    target="$target$market/"
    source_path="$source_path$market/"
    if [ "$brand" != "" ] ; then
	command="$command$brand/"
        target="$target$brand/"
        source_path="$source_path$brand/"
    fi
fi
source_path="$source_path$source_name"

command="$command $temp_dir"

#vlt export
$command

#create target dir hierarchy
mkdir -p $target

#copy exported file(s) into the target dir
yes | cp $source_path $target

#remove temporary folder
rm -R $temp_dir

exit 0
