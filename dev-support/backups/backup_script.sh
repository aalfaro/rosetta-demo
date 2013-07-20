#!/bin/sh
#----------------------------------------------------------------------------------------------
#
#Code to generate back up files from CQ5
#
#----------------------------------------------------------------------------------------------

#----------------------------------------------------------------------------------------------
#Variables
#----------------------------------------------------------------------------------------------

HOST=""
PKNAME=""
LOCALDIR=""
FILTERS=""

NOW=$(date +"%m-%d-%Y_%T")



USER=admin
PASS=admin
TEMP=/tmp/crx_backup

#----------------------------------------------------------------------------------------------
#Functions
#----------------------------------------------------------------------------------------------

help()
{
	echo "Usage -h [HOST_IP] -n [PACKAGE_NAME] -o [LOCAL_DIRECTORY] -f [FILTER_PATH <file>]"
	echo ""
  echo "You'll need vlt installed on you CQ5 instance"
}

create_package()
{ 	
	 

	command="vlt --credentials $USER:$PASS co --filter $FILTERS http://$HOST/crx/ $TEMP"
	echo "$command"
	$command

	cd $TEMP

  

	zip -r $LOCALDIR/"$PKNAME".zip ./*

 
	rm -r $TEMP

  cd $LOCALDIR
}

 upload_package(){

    
  curl -u admin:admin "http://$HOST/crx/packmgr/service.jsp?cmd=rm&name=$PKNAME"

  curl -X POST -u $USER:$PASS -F package=@$PKNAME.zip http://$HOST/crx/packmgr/service/.json/?cmd=upload

  mv "$PKNAME".zip "$PKNAME"_"$NOW".zip
   

   
 }


#----------------------------------------------------------------------------------------------
#Process
#----------------------------------------------------------------------------------------------

clear

if [ $# -eq 0 ] ; then
    help
    exit 0
fi

while getopts ":h:n:o:f:" optname
  do
      case "$optname" in
        "h")
            HOST=$OPTARG
            
            ;;
        "n")
            PKNAME=$OPTARG
            
            ;;
        "o")
            LOCALDIR=$OPTARG
            
            ;;

         "f")
            FILTERS=$OPTARG
            
            ;;
        * )
            help
            exit 0
            ;;
     esac
  done

if [ "$HOST" = "" -o "$PKNAME" = "" -o "$LOCALDIR" = "" -o "$FILTERS" = "" ] ; then
    help
    exit 0
fi

create_package

upload_package

exit 0