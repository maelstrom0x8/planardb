#include "net.hpp"

#include <cstring>
#include <stdexcept>

namespace net = pldb::net;

net::socket::socket(unsigned int _port) : port{_port} {
    int retval;

    fd = ::socket(AF_INET, SOCK_STREAM, 0);
    if (fd == -1) {
        fprintf(stderr, "Opening socket failed\n");
        throw new std::runtime_error("Failed to open socket");
    }

    memset(&address, 0, sizeof(socket_address));
    address.sin_family = AF_INET;
    address.sin_port = htons(port);
    address.sin_addr.s_addr = INADDR_ANY;
}

net::socket::inet_socket_address net::socket::get_address() const noexcept {
    return address;
}

int net::socket::get_socket_file_descriptor() const noexcept { return fd; }

net::socket net::socket::accept() {
    socklen_t len = sizeof(address);
    net::socket::inet_socket_address client_address;
    int client_fd = ::accept(fd, (socket_address *)&client_address, &len);
    if (client_fd < 0)
        return {client_fd};

    socket cl;
    cl.address = client_address;
    cl.fd = client_fd;

    return cl;
}

int net::socket::close() { return ::close(fd); }

int net::socket::listen() { return ::listen(fd, 3); }

net::socket::~socket() { close(); }

int net::socket::bind() {
    
    auto retval = ::bind(fd, (socket_address *)&address, sizeof(address));
    if (retval == -1) {
        
        throw new std::runtime_error("Failed to bind socket");
    }
    
    return retval;
}
