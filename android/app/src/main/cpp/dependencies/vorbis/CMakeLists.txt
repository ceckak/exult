set(DEPENDENCY vorbis)
include(../build_dependency.cmake)

# Vorbis builds/uses a second vorbisfile library
add_library(vorbisfile SHARED IMPORTED GLOBAL)
set_target_properties(
  vorbisfile PROPERTIES
  IMPORTED_LOCATION ${DEPENDENCIES_INSTALL_DIR}/lib/libvorbisfile.so
)
set_target_properties(
  vorbisfile PROPERTIES
  INTERFACE_INCLUDE_DIRECTORIES ${DEPENDENCIES_INSTALL_DIR}/include
)

# Vorbis depends on Ogg and vorbisfile
set_target_properties(${DEPENDENCY} PROPERTIES INTERFACE_LINK_LIBRARIES "ogg;vorbisfile")
