import argparse
import socket
import time
import queue
import threading
import multiprocessing
import serial


class ConnectionManager():

    def __init__(self):
        self.connections = []
        self.listeners = []
        self.lock = threading.Lock()

    def append(self, sock):
        with self.lock:
            self.connections.append(sock)
            self._notify_connections()

    def remove(self, sock):
        with self.lock:
            self.connections.remove(sock)
            self._notify_connections()

    def write(self, bs):
        def _func(sock, bs):
            try:
                sock.sendall(bs)
            except Exception as e:
                print("%r" % e)

        with self.lock:
            processes = []

            for s in self.connections:
                p = multiprocessing.Process(target=_func, args=(s, bs), daemon=True)
                p.start()
                processes.append(p)

            for p in processes:
                p.join()

    def add_listener(self, callback):
        with self.lock:
            self.listeners.append(callback)

    def _notify_connections(self):
        size = len(self.connections)
        for fp in self.listeners:
            fp(size)


class SerialManager(threading.Thread):

    def __init__(self):
        super().__init__(daemon=True)
        self.port = serial.Serial()
        self.lock = threading.Lock()
        self.listeners = []
        self.serial_except_listeners = []
        self.opened = False
        self.closed = False

    def __enter__(self):
        return self

    def __exit__(self, type, value, traceback):
        self.close()
        return False

    def open(self, params):
        if self.closed:
            raise Exception("Already closed")

        if self.opened:
            raise Exception("Already opened")

        self.opened = True
        self.port.port = params.device
        self.port.baudrate = params.baudrate
        self.port.bytesize = params.bytesize
        self.port.parity = params.parity
        self.port.stopbits = params.stopbits
        self.port.xonxoff = params.xonxoff
        self.port.rtscts = params.rtscts
        self.port.dsrdtr = params.dsrdtr
        self.port.open()
        self.start()

    def close(self):
        if not self.closed:
            self.closed = True
            self.port.close()

    def run(self):
        q = queue.Queue()

        def fp_port_reading():
            try:
                while not self.closed:
                    q.put(self.port.read())

            except serial.serialutil.SerialException as e:
                self.notify_serial_exception(e)

        threading.Thread(target=fp_port_reading, daemon=True).start()

        while not self.closed:
            try:
                blst = [q.get()]

                try:
                    while not q.empty():
                        blst.append(q.get_nowait())
                except queue.Empty:
                    pass

                bs = b"".join(blst)

                with self.lock:
                    for fp in self.listeners:
                        fp(bs)

            except Exception as e:
                print("%r" % e)

    def write(self, bs):
        if not self.closed:
            try:
                with self.lock:
                    self.port.write(bs)
            except serial.serialutil.SerialException as e:
                self.notify_serial_exception(e)


    def add_listener(self, callback):
        with self.lock:
            self.listeners.append(callback)

    def add_serial_exception_listener(self, callback):
        with self.lock:
            self.serial_except_listeners.append(callback)

    def notify_serial_exception(self, e):
        with self.lock:
            for cb in self.serial_except_listeners:
                cb(e)

class TcpIpSerialConverter(threading.Thread):

    def __init__(self, params):
        super().__init__(daemon=True)
        self.connections = ConnectionManager()
        self.port = SerialManager()
        self.params = params
        self.opened = False
        self.closed = False

        self.port.add_listener(self.connections.write)

        def fp_connections(size):
            print("Connections: %d" % size)

        self.connections.add_listener(fp_connections)

    def __enter__(self):
        return self

    def __exit__(self, type, value, traceback):
        self.close()
        return False

    def open(self):
        if self.closed:
            raise Exception("Already closed")

        if self.opened:
            raise Exception("Already opened")

        self.opened = True
        self.start()

    def close(self):
        if not self.closed:
            self.closed = True

    def run(self):

        print(self.params)

        try:
            with self.port as p:

                ev = threading.Event()

                def func_lstnr(e):
                    print("%r" % e)
                    ev.set()

                p.add_serial_exception_listener(func_lstnr)

                p.open(self.params)

                for s in self.params.bind:
                    TcpIpSerialConverter._startThread(self._open_bind, (s, self.params.rebind))

                for s in self.params.connect:
                    TcpIpSerialConverter._startThread(self._open_connect, (s, self.params.reconnect))

                ev.wait()

        except Exception as e:
            print("%r" % e)

        finally:
            self.close()


    def _open_bind(self, bind, rebind_seconds):
        address, address_family = TcpIpSerialConverter._parse_address(bind)

        while not self.closed:
            str_server = "not binded"

            try:
                with socket.socket(address_family, socket.SOCK_STREAM) as server:
                    server.setsockopt(socket.SOL_SOCKET, socket.SO_REUSEADDR, 1)
                    server.bind(address)

                    str_server = str(server)
                    print("server bind: %r" % str_server)

                    server.listen(10)

                    while not self.closed:
                        sock, client_addr = server.accept()
                        TcpIpSerialConverter._startThread(self._reading, (sock,))

            except Exception as e:
                print("%r" % e)

            finally:
                print("server closed: %r" % str_server)

            if not self.closed:
                time.sleep(rebind_seconds)


    def _open_connect(self, connect, reconnect_seconds):
        address, address_family = TcpIpSerialConverter._parse_address(connect)

        while not self.closed:
            sock = None

            try:
                sock = socket.socket(address_family, socket.SOCK_STREAM)
                sock.connect(address)
                self._reading(sock)

            except Exception as e:
                print("%r" % e)
                if sock is not None:
                    sock.close()

            if not self.closed:
                time.sleep(reconnect_seconds)


    def _reading(self, sock):

        str_sock = str(sock)

        try:
            print("connect: %r" % str_sock)

            with sock as s:
                self.connections.append(s)

                while not self.closed:
                    recv = s.recv(4096)
                    if not recv:
                        break

                    self.port.write(recv)

        except Exception as e:
            print("%r" % e)

        finally:
            self.connections.remove(sock)
            print("disconnect: %r" % str_sock)

    @classmethod
    def _startThread(cls, func, args=()):
        th = threading.Thread(target=func, args=args, daemon=True)
        th.start()
        return th

    @classmethod
    def _parse_address(cls, v):
        vv = v.split(":", 1)
        ip = vv[0].strip()
        port = int(vv[1].strip())
        return (ip, port), socket.AF_INET


def _get_params():
    parser = argparse.ArgumentParser()

    parser.add_argument("--device", default="/dev/ttyAMA0")
    parser.add_argument("--baudrate", type=int, default=9600, help="default 9600 bps")
    parser.add_argument("--bytesize", type=int, choices=[5, 6, 7, 8], default=8)
    parser.add_argument("--parity", default=serial.PARITY_NONE
                        , choices=[
                                   serial.PARITY_NONE
                                   , serial.PARITY_EVEN
                                   , serial.PARITY_ODD
                                   , serial.PARITY_MARK
                                   , serial.PARITY_SPACE]
                        , help="[None, Even, Odd, Mark, Space]")
    parser.add_argument("--stopbits", type=int, choices=[1, 2], default=1)

    parser.add_argument("--xonxoff", action="store_const", const=True, default=None, help="set if True")
    parser.add_argument("--rtscts", action="store_const", const=True, default=None, help="set if True")
    parser.add_argument("--dsrdtr", action="store_const", const=True, default=None, help="set if True")

    parser.add_argument("--bind", action="append", default=[], help="format: aaa.bbb.ccc.ddd:eeeee")
    parser.add_argument("--connect", action="append", default=[], help="format: aaa.bbb.ccc.ddd:eeeee")
    parser.add_argument("--rebind", type=float, default=5.0, help="rebind seconds")
    parser.add_argument("--reconnect", type=float, default=5.0, help="reconnect seconds")

    return parser.parse_args()


if __name__ == '__main__':
    with TcpIpSerialConverter(_get_params()) as v:
        v.run()

