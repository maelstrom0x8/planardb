#pragma once

#include "CLI11.hpp"
#include "net.hpp"

namespace pldb {

class planardb {

  public:
    planardb();
    int run(int argc, char *argv[]);

  private:
    CLI::App cli_parser;
    net::server server;
};
} // namespace planardb
