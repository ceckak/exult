# Common install directory for dependencies.
set(DEPENDENCIES_INSTALL_DIR "${CMAKE_BINARY_DIR}/dependencies/install")

# Map LLVM triple to the NDK's toolchain prefix triple.
if(${ANDROID_LLVM_TRIPLE} STREQUAL armv7-none-linux-androideabi${ANDROID_NATIVE_API_LEVEL})
    set(TRIPLE armv7a-linux-androideabi${ANDROID_NATIVE_API_LEVEL})
elseif(${ANDROID_LLVM_TRIPLE} STREQUAL aarch64-none-linux-android${ANDROID_NATIVE_API_LEVEL})
    set(TRIPLE aarch64-linux-android${ANDROID_NATIVE_API_LEVEL})
elseif(${ANDROID_LLVM_TRIPLE} STREQUAL i686-none-linux-android${ANDROID_NATIVE_API_LEVEL})
    set(TRIPLE i686-linux-android${ANDROID_NATIVE_API_LEVEL})
elseif(${ANDROID_LLVM_TRIPLE} STREQUAL x86_64-none-linux-android${ANDROID_NATIVE_API_LEVEL})
    set(TRIPLE x86_64-linux-android${ANDROID_NATIVE_API_LEVEL})
else()
    set(TRIPLE ${ANDROID_LLVM_TRIPLE})
endif()

# Build up linker flags to find libraries at build and runtime.
foreach(IMPLICIT_LINK_DIRECTORY ${CMAKE_CXX_IMPLICIT_LINK_DIRECTORIES})
  set(LDFLAGS "${LDFLAGS} -L${IMPLICIT_LINK_DIRECTORY} -Wl,-rpath,${IMPLICIT_LINK_DIRECTORY}")
endforeach()

# Construct a shell script that sets autotools-friendly environment variables for the NDK build.
set(ENVFILE "${CMAKE_CURRENT_BINARY_DIR}/env.sh")
file(WRITE ${ENVFILE} "#!/bin/sh\n")
file(APPEND ${ENVFILE} "export CC=${ANDROID_TOOLCHAIN_ROOT}/bin/${TRIPLE}-clang\n")
file(APPEND ${ENVFILE} "export CXX=${ANDROID_TOOLCHAIN_ROOT}/bin/${TRIPLE}-clang++\n")
file(APPEND ${ENVFILE} "export PKG_CONFIG_PATH=${DEPENDENCIES_INSTALL_DIR}/lib/pkgconfig:$PKG_CONFIG_PATH\n")
#file(APPEND ${ENVFILE} "export PATH=${CMAKE_BINARY_DIR}/../../../../../../usecode/ucxt:$PATH\n")
file(APPEND ${ENVFILE} "export LDFLAGS=\"${ANDROID_LINKER_FLAGS} ${LDFLAGS}\"\n")
file(APPEND ${ENVFILE} "export CFLAGS=\"${CMAKE_C_FLAGS}\"\n")
file(APPEND ${ENVFILE} "export CXXFLAGS=\"${CMAKE_CXX_FLAGS}\"\n")

# Get the CPU count to use for parallel builds
include(ProcessorCount)
ProcessorCount(NCPU)

# Pull in dependencies
include(ExternalProject)
add_subdirectory("ogg")
add_subdirectory("vorbis")
add_subdirectory("sdl")
add_subdirectory("exult")
