#include "sdlrwopsostream.h"

SdlRwopsOstream::SdlRwopsOstream() : std::ostream(&m_streambuf)
{
}

SdlRwopsOstream::SdlRwopsOstream(const char* s, std::ios_base::openmode mode) : std::ostream(&m_streambuf) {
    if (m_streambuf.open(s, mode | std::ios_base::out) == nullptr)
        setstate(std::ios_base::failbit);
}

SdlRwopsStreambuf* SdlRwopsOstream::rdbuf() const
{
    return const_cast<SdlRwopsStreambuf*>(&m_streambuf);
}

bool SdlRwopsOstream::is_open() const
{
    return m_streambuf.is_open();
}

void SdlRwopsOstream::open(const char* s, std::ios_base::openmode mode)
{
    if (m_streambuf.open(s, mode | std::ios_base::out))
        clear();
    else
        setstate(std::ios_base::failbit);
}

void SdlRwopsOstream::close()
{
    if (m_streambuf.close() == nullptr)
        setstate(std::ios_base::failbit);
}
