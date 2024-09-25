#include "planardb.hpp"
#include "net.hpp"
#include <vector>
#include "query.hpp"
#define PORT 2969

pldb::planardb::planardb() : server{PORT} {}

int pldb::planardb::run(int argc, char *argv[]) {
    server.start();

    return 0;
}

int main(int argc, char *argv[]) {

    pldb::planardb planardb;
    return planardb.run(argc, argv);
}
