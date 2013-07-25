GIT=git
DST=$GIT/yax

FILES=`cat Inventory |tr -d '\r' |tr '\n' '  '`

rm -fr ${GIT}
mkdir -p ${DST}

for f in ${FILES} ; do
DIR=`dirname $f`
mkdir -p ${DST}/${DIR}
cp $f $DST/$f
done

