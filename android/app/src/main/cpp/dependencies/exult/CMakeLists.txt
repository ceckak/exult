set(DEPENDENCY exult)
include(../build_dependency.cmake)

# Exult depends on SDL
set_target_properties(${DEPENDENCY} PROPERTIES INTERFACE_LINK_LIBRARIES SDL2)
