#pragma once


#include "net.hpp"

namespace pldb {

class planardb {

  public:
    planardb();
    int run(int argc, char *argv[]);

  private:
    net::server server;
};
} // namespace planardb
