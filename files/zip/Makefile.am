AM_CPPFLAGS = $(SDL_CFLAGS) $(INCDIRS) -I@top_srcdir@/headers $(WINDOWING_SYSTEM) \
		$(DEBUG_FLAGS) $(CPPFLAGS)

noinst_LTLIBRARIES = libminizip.la

libminizip_la_SOURCES =	\
	zip.cc \
	zip.h \
	unzip.cc \
	unzip.h

CLEANFILES = *~
