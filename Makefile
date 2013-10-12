EXPORT=f:/git/export

.PHONEY: check clean install all

all::
	cd c; ${MAKE} all
	cd java; ${MAKE} all
	cd python; ${MAKE} all

check::
	cd c; ${MAKE} check
	cd java; ${MAKE} check
	cd python; ${MAKE} check

clean::
	cd c; ${MAKE} clean	
	cd java; ${MAKE} clean
	cd python; ${MAKE} clean
	rm -fr ./export

generate::
	cd c; ${MAKE} generate
	cd java; ${MAKE} generate
	cd python; ${MAKE} generate

export::
	${SH} ./export.sh

install::
	rm -fr ${EXPORT}/yax/*
	cp -fr ./export/yax ${EXPORT}


