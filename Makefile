
all::
	cd c; ${MAKE} all tests
	cd java; ant all tests

clean::
	cd c; ${MAKE} clean	
	cd java; ant clean
	rm -fr ./git

git::
	${SH} ./git.sh

install::
	cp -fr ./git/yax f:/git
