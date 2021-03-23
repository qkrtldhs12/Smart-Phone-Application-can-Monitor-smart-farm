import serial

port='/dev/ttyACM0'
bRate=9600  #boudrate
cmd='temp'

seri=serial.Serial(port,baudrate=bRate,timeout=None)
print(seri.name)

seri.write(cmd.encode())

a=1

while a:
    if seri.in_witing!=0:
        content=seri.readline()
        print(content[:-2].decode())
        a=0