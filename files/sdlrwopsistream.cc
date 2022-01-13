#include "sdlrwopsistream.h"

SdlRwopsIstream::SdlRwopsIstream() : std::istream(&m_streambuf)
{
}

SdlRwopsIstream::SdlRwopsIstream(const char* s, std::ios_base::openmode mode) : std::istream(&m_streambuf)
{
    if (m_streambuf.open(s, mode | std::ios_base::in) == nullptr)
        setstate(std::ios_base::failbit);
}

SdlRwopsStreambuf* SdlRwopsIstream::rdbuf() const
{
    return const_cast<SdlRwopsStreambuf*>(&m_streambuf);
}

bool SdlRwopsIstream::is_open() const
{
    return m_streambuf.is_open();
}

void SdlRwopsIstream::open(const char* s, std::ios_base::openmode mode)
{
    if (m_streambuf.open(s, mode | std::ios_base::in))
        clear();
    else
        setstate(std::ios_base::failbit);
}

void
SdlRwopsIstream::close()
{
    if (!m_streambuf.close())
        setstate(std::ios_base::failbit);
}
