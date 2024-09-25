#include "query.hpp"
#include "utils.hpp"
#include <iostream>

void pldb::ensure_directory_exists(const fs::path &dir_path) {
    if (!fs::exists(dir_path)) {
        if (fs::create_directory(dir_path)) {
            fprintf(stdout, "Initializing database directory");
        } else {
            fprintf(stderr, "Failed to create directory\n");
            throw std::runtime_error("Failed to create directory");
        }
    } else {
        std::cerr<< 
            "Database directory already exists. skipping initialization\n";
    }
}

void pldb::ensure_file_exists(const fs::path &file_path) {
    if (fs::exists(file_path)) {
        return;
    } else {
        std::ofstream file(file_path);
        if (!file) {
            fprintf(stderr, "Failed to create file\n");
            // std::cerr <<  "Failed to create file: ", file_path.string() << std::endl;
            throw std::runtime_error("Failed to create file");
        }
    }
}

std::vector<std::string> pldb::split(const std::string &str, char delimiter) {
    std::vector<std::string> result;
    std::stringstream ss(str);
    std::string token;

    while (std::getline(ss, token, delimiter)) {
        result.push_back(token);
    }

    return result;
}

std::string pldb::join(VectorStringIterator first, VectorStringIterator end,
                       std::string delim) {
    std::string s;
    while (first != end) {
        s.append(*first);
        if (std::next(first) != end)
            s.append(delim);
        first = std::next(first);
    }
    return s;
}

pldb::QueryHandler::QueryHandler() {
    ensure_directory_exists(database_directory);
    ensure_file_exists(dbfile);
    file = std::fstream{dbfile, std::ios::app | std::ios::in | std::ios::out};
}

pldb::QueryHandler::~QueryHandler() {
    if (file.is_open())
        file.close();
}

void pldb::QueryHandler::write(const std::string &record) {
    std::cout << "appending record: " << record << std::endl;
    file << record << "\n";
}

std::string pldb::QueryHandler::read(const std::string &key) {
    std::cout << "reading key: " << key << std::endl;
    file.clear();
    file.seekg(0, std::ios::beg);
    std::string line, target;
    while (std::getline(file, line)) {
        if (line.compare(0, key.length(), key) == 0)
            target = line;
    }
    return target;
}

pldb::QueryResult pldb::QueryHandler::process(std::string &query) {
    std::vector<std::string> tokens = split(query, ' ');

    if (tokens.size() < 2)
        throw std::runtime_error("Invalid arguments");

    if (tokens[0] == "pset") {
        if (tokens.size() < 3) {
            std::runtime_error("Incomplete arguments");
        }

        std::string record = join(std::next(tokens.begin()++), tokens.end());
        write(record);
        return QueryResult::empty();

    } else if (tokens[0] == "pget") {
        std::string result = read(tokens[1]);
        return QueryResult::of(result);
    } else {
        throw std::runtime_error("Invalid command");
    }
    return QueryResult::empty();
}
