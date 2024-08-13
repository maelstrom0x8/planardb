cmake_minimum_required(VERSION 3.26)
project(planardb LANGUAGES CXX)

set(CMAKE_CXX_STANDARD 23)
set(CMAKE_CXX_STANDARD_REQUIRED YES)
set(CMAKE_CXX_EXTENSIONS NO)

set(PLANARDB_SOURCES
  src/planardb.cpp
  src/net.cpp
  src/query.cpp
)

add_executable(planardb)
target_sources(planardb PRIVATE ${PLANARDB_SOURCES})

if(CMAKE_BUILD_TYPE STREQUAL "Debug")
  target_compile_options(planardb PRIVATE -fsanitize=address)
  target_link_options(planardb PRIVATE -fsanitize=address)
endif(CMAKE_BUILD_TYPE STREQUAL "Debug")

target_precompile_headers(planardb PUBLIC src/CLI11.hpp)
