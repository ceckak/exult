#ifndef SDLRWOPSSTREAMBUF_H_
#define SDLRWOPSSTREAMBUF_H_

#include <streambuf>

// Partly derived from libc++'s basic_filebuf
// https://github.com/llvm-mirror/libcxx/blob/master/include/fstream
//   Part of the LLVM Project, under the Apache License v2.0 with LLVM Exceptions.
//   See https://llvm.org/LICENSE.txt for license information.
//   SPDX-License-Identifier: Apache-2.0 WITH LLVM-exception

class SdlRwopsStreambuf : public std::streambuf {
public:
	SdlRwopsStreambuf();
	virtual ~SdlRwopsStreambuf();
	bool               is_open() const;
	SdlRwopsStreambuf* open(const char* s, std::ios_base::openmode mode);
	SdlRwopsStreambuf* close();

protected:
	int_type underflow() override;
	int_type pbackfail(int_type c = traits_type::eof()) override;
	int_type overflow(int_type c = traits_type::eof()) override;
	pos_type
			seekoff(off_type off, std::ios_base::seekdir dir,
					std::ios_base::openmode which
					= std::ios_base::in | std::ios_base::out) override;
	pos_type
			seekpos(pos_type                sp,
					std::ios_base::openmode which
					= std::ios_base::in | std::ios_base::out) override;
	int     sync() override;

private:
	char                    m_buffer[8];
	struct SDL_RWops*       m_context;
	std::ios_base::openmode m_openMode;
	std::ios_base::openmode m_currentMode;
};

#endif    // SDLRWOPSSTREAMBUF_H_
