EXPORT=f:/git/export

.PHONEY: check clean install all

all::
	cd c; ${MAKE} all
	cd java; ant all
	cd python; ${MAKE} all

check::
	cd c; ${MAKE} check
	cd java; ant check
	cd python; ${MAKE} check

clean::
	cd c; ${MAKE} clean	
	cd java; ant clean
	cd python; ${MAKE} clean
	rm -fr ./git

git::
	${SH} ./git.sh

install::
	rm -fr ${EXPORT}/yax/*
	cp -fr ./git/yax ${EXPORT}


