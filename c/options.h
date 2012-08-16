#ifndef OPTIONS_H
#define OPTIONS_H

extern int options(const char* optlist, int argc, char** argv);
extern int optionset(int c);
extern int ioption(int c);
extern const char* soption(int c);
extern const char* nonoption(int i);

#endif /*OPTIONS_H*/
