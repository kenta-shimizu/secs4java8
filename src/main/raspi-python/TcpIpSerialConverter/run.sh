#!/bin/sh

#TcpIpSerialConverter

eth0ip="192.168.1.100"

bind1="--bind ${eth0ip}:23001"
bind2="--bind 127.0.0.1:23001"
#connect1="--connect 127.0.0.1:23001"

rebind="--rebind 5"
reconnect="--reconnect 5"

sockets="${bind1} ${bind2} ${connect1}"
timeouts="${rebind} ${reconnect}"

p1="--device /dev/ttyAMA0"
p2="--baudrate 9600"
p3="--bytesize 8"
p4="--parity N"
p5="--stopbits 1"
#p6="--xonxoff True"
#p7="--rtscts True"
#p8="--dsrdtr True"

serials="${p1} ${p2} ${p3} ${p4} ${p5} ${p6} ${p7} ${p8}"

parameters="${sockets} ${timeouts} ${serials}"

python3 /home/pi/TcpIpSerialConverter/tcpip-serial-converter.py ${parameters}

exit 0
