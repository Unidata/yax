.PHONEY: test tests check clean install all

all::
	cd c; ${MAKE} all
	cd java; ant all

test tests check::
	cd c; ${MAKE} tests
	cd java; ant tests

clean::
	cd c; ${MAKE} clean	
	cd java; ant clean
	rm -fr ./git

git::
	${SH} ./git.sh

install::
	cp -fr ./git/yax f:/git/export

