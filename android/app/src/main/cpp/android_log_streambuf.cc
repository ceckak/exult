/*
 *  Copyright (C) 2021-2022  The Exult Team
 *
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; either version 2 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program; if not, write to the Free Software
 *  Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA 02111-1307, USA.
 */

#include "android_log_streambuf.h"

#include <android/log.h>

#include <iostream>

AndroidLog_streambuf::AndroidLog_streambuf(int priority, const char* tag)
		: m_priority{priority}, m_tag{tag} {}

std::streambuf::int_type AndroidLog_streambuf::overflow(int_type ch) {
	if ('\n' == ch || traits_type::eof() == ch) {
		__android_log_write(m_priority, m_tag.c_str(), m_lineBuf.c_str());
		m_lineBuf = "";
	} else {
		m_lineBuf += ch;
	}
	return ch;
}
