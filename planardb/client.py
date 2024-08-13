#!/usr/bin/env python3

import io
import socket, signal
import sys

import cmd
import os
import re


class PLClient(cmd.Cmd):
    """A command-line parser for interactive use.

    This class provides a command-line interface for interactive use.
    Users can enter commands, and this parser interprets and processes
    those commands.

    Attributes:
        prompt (str): The command prompt to display.
    """
    prompt = 'pldb=# '

    def __init__(self, completekey="tab", stdin=None, stdout=None):
        super().__init__(completekey, stdin, stdout)
        self.sock = None
        self.server_address = ('127.0.0.1', 2969)
        self.timeout = 2.0
    
    def connect(self):
        """Establishes a connection to the server."""
        if self.sock is None:
            self.sock = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
            self.sock.settimeout(self.timeout)
        
        if not self.sock:
            self.print_result('Failed to create socket')
            return False

        try:
            self.sock.connect(self.server_address)
            return True
        except socket.error as e:
            self.print_result(f'Failed to connect to server: {e}')
            self.close_connection()
            return False

    def close_connection(self):
        """Closes the socket connection."""
        if self.sock:
            self.sock.close()
            self.sock = None

    def send_request(self, data: str) -> str:
        """Sends a request to the server and returns the response."""
        if not self.connect():
            return ''

        try:
            self.sock.sendall(data.encode())
            response = self.sock.recv(1024).decode()
            return response
        except socket.timeout:
            self.print_result('Socket timeout: No response from server')
        except socket.error as e:
            self.print_result(f'Socket error: {e}')
        finally:
            self.close_connection()
        
        return ''

    def do_quit(self, args):
        """Quit command to exit the program"""
        # self.clear_terminal()
        return True

    def do_EOF(self, line):
        """Ctrl-D to exit the program"""
        # self.clear_terminal()
        return True

    def do_pget(self, *args):
        _args = args[0].split(' ')
        if len(_args) < 1:
            self.print_result('Missing arguments')
            return
        data = 'pget ' +  ' '.join(_args)
        res = self.send_request(data)
        print(" " + res)
        return
    
    def print_result(result: str):
        fmt = ' ' + result
        print(fmt)

    def do_pset(self, *args):
        _args = args[0].split(' ')
        if(len(_args) < 2):
            self.print_result('Missing arguments')
            return
        data = 'pset ' + ' '.join(_args)
        self.send_request(data)
        return

    def cmdloop(self, intro=None):
        super().cmdloop(intro)

    def emptyline(self) -> bool:
        return False

    def preloop(self) -> None:
        if not os.isatty(0):
            self.prompt = ''
            self.onecmd('')

    def precmd(self, line: str):
        if(len(line) < 1):
            return super().precmd(line)
        try:
            _fn = 'do_' + line.split(' ')[0]
            if getattr(self, _fn) is not None:
                return super().precmd(line)
        except (AttributeError):
            self.print_result('Invalid command')
            return ''
        return super().precmd(line)
    
    def signal_handler(self, signal, frame):
        self.clear_terminal()
        print()
        self.close_connection()
        sys.exit(0)

    def clear_terminal(self):
        """Clear the terminal screen"""
        os.system('cls' if os.name == 'nt' else 'clear')

if __name__ == '__main__':
    client = PLClient()
    signal.signal(signal.SIGINT, client.signal_handler)
    client.cmdloop()

