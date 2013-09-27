#!/usr/bin/env python

from distutils.core import setup

setup(name='yax',
      version='1.0',
      description='Python sax parsing using Bison',
      author='Dennis Heimbigner',
      author_email='dmh@unidata.ucar.edu',
      url='https://github.com/Unidata/yax.git',
      packages=['yax','yax/test'],
      package_data={'yax':['python.skel','Makefile'],
                    'yax/test':['dap4.sax.y']},
      data_files=[('/etc/yax',['yax/python.skel'])],
      license='See file COPYRIGHT'
     )
