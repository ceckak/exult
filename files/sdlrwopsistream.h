#ifndef SDLRWOPSISTREAM_H_
#define SDLRWOPSISTREAM_H_

#include "sdlrwopsstreambuf.h"
#include <istream>

class SdlRwopsIstream
    : public std::istream
{
public:
    typedef typename traits_type::int_type int_type;
    typedef typename traits_type::pos_type pos_type;
    typedef typename traits_type::off_type off_type;

    SdlRwopsIstream();
    explicit SdlRwopsIstream(const char* s, std::ios_base::openmode mode = std::ios_base::in);

    SdlRwopsStreambuf* rdbuf() const;
    bool is_open() const;
    void open(const char* s, std::ios_base::openmode mode = std::ios_base::in);
    void close();

private:
    SdlRwopsStreambuf m_streambuf;
};

#endif //SDLRWOPSISTREAM_H_
