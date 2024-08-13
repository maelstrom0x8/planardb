#pragma once

#include "query.hpp"
#include <netdb.h>
#include <sys/socket.h>
#include <unistd.h>

namespace pldb {
namespace net {
class socket {

  public:
    using socket_address = struct sockaddr;
    using inet_socket_address = struct sockaddr_in;

    socket() = default;
    socket(int _fd) : fd{_fd} {}
    explicit socket(unsigned int port);
    // socket(const socket &s) = delete;
    // socket operator=(const socket &s) = delete;

    inet_socket_address get_address() const noexcept;
    int get_socket_file_descriptor() const noexcept;

    int bind();
    socket accept();
    int close();
    int listen();

    ~socket();

  private:
    inet_socket_address address;
    int fd;
    unsigned int port;
};

template <typename Fn>
concept QueryResultType = requires(Fn fn, std::string query) {
    { fn(query) } -> std::same_as<QueryResult>; // Check return type
    {
        fn(query).result
    } -> std::convertible_to<std::string>; // Check result member
};

class server {

  public:
    explicit server(unsigned int _port) : socket_{_port} {
        socket_.bind();
        if (socket_.listen() > -1) {
            
        }
    }

    void start() {
        while (true) {
            char buf[1024];
            auto client = socket_.accept();
            auto cfd = client.get_socket_file_descriptor();
            auto bytes_recieved = recv(cfd, buf, 1024, 0);
            if (bytes_recieved > 0) {
                
                std::string query(buf, bytes_recieved);
                QueryResult result = handler.process(query);
                if(!result.result.empty())
                  
                send(cfd, result.result.data(), result.result.size(), 0);
            }
            client.close();
        }
    }

    ~server() { socket_.close(); }

  private:
    pldb::QueryHandler handler;
    socket socket_;
};
} // namespace net
} // namespace pldb
