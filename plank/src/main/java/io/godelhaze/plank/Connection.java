package io.godelhaze.plank;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.*;
import java.nio.channels.SocketChannel;
import java.util.Set;

public class Connection implements Closeable {

  private static final String DEFAULT_HOST = "127.0.0.1";
  private static final int DEFAULT_PORT = 2969;

  private  static  final Logger LOG = LoggerFactory.getLogger(Connection.class);


  private Socket socket;
  private InetSocketAddress address;

  public void connect(SocketAddress endpoint) throws IOException {
    socket.connect(endpoint);
  }

  public boolean getTcpNoDelay() throws SocketException {
    return socket.getTcpNoDelay();
  }

  public boolean isConnected() {
    return socket.isConnected();
  }

  public void setReceiveBufferSize(int size) throws SocketException {
    socket.setReceiveBufferSize(size);
  }

  public <T> T getOption(SocketOption<T> name) throws IOException {
    return socket.getOption(name);
  }

  public void bind(SocketAddress bindpoint) throws IOException {
    socket.bind(bindpoint);
  }

  public void setTcpNoDelay(boolean on) throws SocketException {
    socket.setTcpNoDelay(on);
  }

  public int getSendBufferSize() throws SocketException {
    return socket.getSendBufferSize();
  }

  public void shutdownOutput() throws IOException {
    socket.shutdownOutput();
  }

  public int getSoTimeout() throws SocketException {
    return socket.getSoTimeout();
  }

  public <T> Socket setOption(SocketOption<T> name, T value) throws IOException {
    return socket.setOption(name, value);
  }

  public void shutdownInput() throws IOException {
    socket.shutdownInput();
  }

  public void setSendBufferSize(int size) throws SocketException {
    socket.setSendBufferSize(size);
  }

  public void connect(SocketAddress endpoint, int timeout) throws IOException {
    socket.connect(endpoint, timeout);
  }

  public SocketAddress getLocalSocketAddress() {
    return socket.getLocalSocketAddress();
  }

  public void setSoTimeout(int timeout) throws SocketException {
    socket.setSoTimeout(timeout);
  }

  public SocketChannel getChannel() {
    return socket.getChannel();
  }

  public boolean getReuseAddress() throws SocketException {
    return socket.getReuseAddress();
  }

  public void setPerformancePreferences(int connectionTime, int latency, int bandwidth) {
    socket.setPerformancePreferences(connectionTime, latency, bandwidth);
  }

  public SocketAddress getRemoteSocketAddress() {
    return socket.getRemoteSocketAddress();
  }

  public int getTrafficClass() throws SocketException {
    return socket.getTrafficClass();
  }

  public boolean getOOBInline() throws SocketException {
    return socket.getOOBInline();
  }

  public void setReuseAddress(boolean on) throws SocketException {
    socket.setReuseAddress(on);
  }

  public int getPort() {
    return socket.getPort();
  }

  public void setTrafficClass(int tc) throws SocketException {
    socket.setTrafficClass(tc);
  }

  public boolean isOutputShutdown() {
    return socket.isOutputShutdown();
  }

  public int getLocalPort() {
    return socket.getLocalPort();
  }

  public void setOOBInline(boolean on) throws SocketException {
    socket.setOOBInline(on);
  }

  public boolean isInputShutdown() {
    return socket.isInputShutdown();
  }

  public int getSoLinger() throws SocketException {
    return socket.getSoLinger();
  }

  public void sendUrgentData(int data) throws IOException {
    socket.sendUrgentData(data);
  }

  public InetAddress getLocalAddress() {
    return socket.getLocalAddress();
  }

  public boolean getKeepAlive() throws SocketException {
    return socket.getKeepAlive();
  }

  public void setSoLinger(boolean on, int linger) throws SocketException {
    socket.setSoLinger(on, linger);
  }

  public int getReceiveBufferSize() throws SocketException {
    return socket.getReceiveBufferSize();
  }

  public boolean isClosed() {
    return socket.isClosed();
  }

  public InetAddress getInetAddress() {
    return socket.getInetAddress();
  }

  public void setKeepAlive(boolean on) throws SocketException {
    socket.setKeepAlive(on);
  }

  public boolean isBound() {
    return socket.isBound();
  }

  public Set<SocketOption<?>> supportedOptions() {
    return socket.supportedOptions();
  }

  public Connection() {
    this(DEFAULT_HOST, DEFAULT_PORT);
  }

  public Connection(String host, int port) {
    socket = new Socket();
    address = new InetSocketAddress(host, port);
  }

  public Connection(String host) {
    LOG.info("Creating connection");
    socket = new Socket();
    address = new InetSocketAddress(DEFAULT_HOST, DEFAULT_PORT);
  }

  public Socket getSocket() {
    return socket;
  }

  public void setSocket(Socket socket) {
    this.socket = socket;
  }

  public InetSocketAddress getAddress() {
    return address;
  }

  public void setAddress(InetSocketAddress address) {
    this.address = address;
  }

  public OutputStream getOutputStream() throws IOException {
    return socket.getOutputStream();
  }

  public InputStream getInputStream() throws IOException {
    return socket.getInputStream();
  }

  @Override
  public void close() throws IOException {
    if (!socket.isClosed())
      socket.close();
  }
}
