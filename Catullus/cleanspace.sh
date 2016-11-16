#/bin/sh
PERL=`which perl`

for arg in "$@"
do 
    renamed=$(echo $arg | sed -e "s/.txt/-2.txt/")
    CMD=$PERL -pe  's/[ ]+/ /g' $arg  | perl -pe  's/^ //' - | tee renamed
    echo $CMD
    #
done
