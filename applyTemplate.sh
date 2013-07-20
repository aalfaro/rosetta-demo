
echo
echo "Applying CQ Project template..."
echo "this process will rename all placeholder 'MyClient' and 'MyProject' values"
echo "with the values you enter below. Please enter the 'pretty name' for the values below"
echo

#DEBUG=true
SCRIPT=`echo $0 | sed 's/^\.\///'`
YEAR=`date +%Y`

echo -n "Please enter the name of your Client: "
read MYCLIENT

echo -n "Please enter the name of your Project: "
read MYPROJECT

# make some "code-friendly" versions of these values
MYCLIENTClean=`echo $MYCLIENT | sed 's/ /_/g' | awk '{print tolower($0)}'`
MYPROJECTClean=`echo $MYPROJECT | sed 's/ /_/g' | awk '{print tolower($0)}'`

if [ "$MYCLIENT" == "" ] || [ "$MYPROJECT" == "" ]; then
  echo;echo "You must enter a value for Client and Project"
  echo "Terminiating..."
  exit 1;
fi;

# echo things out and confirm
echo "Applying template with following values:"
echo "Year: $YEAR"
echo "Client: $MYCLIENT ($MYCLIENTClean)"
echo "Project: $MYPROJECT ($MYPROJECTClean)"
echo; echo -n "Does this look right? (y/n) : "
read confirm

if [[ $confirm != y* ]]; then
  echo;echo "Terminating...";
  exit 1;
fi


echo "Performing transformation..."

# rename each directory with "myclient" in it

for i in $(find . -type d | grep "\/*myclient[^\/]*$" | sort -r); do
  NEW=`echo $i | sed "s/\(.*\)myclient/\1$MYCLIENTClean/"`
  if [ "$DEBUG" == "true" ]; then
    echo running: mv $i $NEW
  fi
  mv $i $NEW
done

# rename each directory with "myproject" in it

for i in $(find . -type d | grep "\/*myproject[^\/]*$" | sort -r); do
  NEW=`echo $i | sed "s/\(.*\)myproject/\1$MYPROJECTClean/"`
  if [ "$DEBUG" == "true" ]; then
    echo running: mv $i $NEW
  fi
  mv $i $NEW
done

# recursively update files
grep -lr --exclude=$SCRIPT -e 'CURRENTYEAR' * | xargs sed -i .sedbak "s/CURRENTYEAR/$YEAR/g"; find . -name "*.sedbak" | xargs rm
grep -lr --exclude=$SCRIPT -e 'MyClient' * | xargs sed -i .sedbak "s/MyClient/$MYCLIENT/g"; find . -name "*.sedbak" | xargs rm
grep -lr --exclude=$SCRIPT -e 'myclient' * | xargs sed -i .sedbak "s/myclient/$MYCLIENTClean/g"; find . -name "*.sedbak" | xargs rm
grep -lr --exclude=$SCRIPT -e 'MyProject' * | xargs sed -i .sedbak "s/MyProject/$MYPROJECT/g"; find . -name "*.sedbak" | xargs rm
grep -lr --exclude=$SCRIPT -e 'myproject' * | xargs sed -i .sedbak "s/myproject/$MYPROJECTClean/g"; find . -name "*.sedbak" | xargs rm

echo "done!"

mv .git .git-cqtemplate
echo "---------------------------------------------"
echo "In the interest of not having these transformations checked back into the template project,"
echo "the original .git directory has been moved to .git-cqtemplate (and should be deleted before"
echo "checking these transformed files into your new client-specific git repo"
echo "---------------------------------------------"
echo;echo

