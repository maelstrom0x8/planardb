#pragma once

#include <filesystem>
#include <fstream>
#include <sstream>
#include <string>
#include <vector>

namespace pldb {

namespace fs = std::filesystem;

using VectorStringIterator = std::vector<std::string>::iterator;

std::string join(VectorStringIterator first, VectorStringIterator end,
                 std::string delim = " ");

std::vector<std::string> split(const std::string &str, char delimiter);

void ensure_file_exists(const fs::path &file_path);
void ensure_directory_exists(const fs::path &dir_path);

struct QueryResult {
    std::string result;

    static QueryResult empty() { return {""}; }
    static QueryResult of(std::string &res) { return {res}; }
};

class QueryHandler {

  public:
    QueryHandler();
    ~QueryHandler();

    QueryResult process(std::string &query);
    std::string read(const std::string &key);
    void write(const std::string &record);

  private:
    std::fstream file;
    fs::path database_directory{"etc/planardb.d"};
    fs::path dbfile{database_directory / "pl.db"};
};

} // namespace pldb
