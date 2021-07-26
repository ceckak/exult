#ifndef SDLRWOPSOSTREAM_H_
#define SDLRWOPSOSTREAM_H_

#include "sdlrwopsstreambuf.h"
#include <ostream>

class SdlRwopsOstream : public std::ostream
{
public:
    typedef typename traits_type::int_type int_type;
    typedef typename traits_type::pos_type pos_type;
    typedef typename traits_type::off_type off_type;

    SdlRwopsOstream();
    explicit SdlRwopsOstream(const char* s, std::ios_base::openmode mode = std::ios_base::out);

    SdlRwopsStreambuf* rdbuf() const;
    bool is_open() const;
    void open(const char* s, std::ios_base::openmode mode = std::ios_base::out);
    void close();

private:
    SdlRwopsStreambuf m_streambuf;
};

#endif //SDLRWOPSOSTREAM_H_
