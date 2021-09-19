import serial
import time

# 센싱 아두이노 시리얼포트 연결
ser1=serial.Serial('COM7',9600)
# ser2=Serial('/dev/ttyACM1',9600)

while ser1.readable():
    if ser1.readable():
        res1=ser1.readline()
        res1=str(res1)
        ref1=list(res1.split(','))
        ref1[0]=ref1[0][-1]
        ref1[3]=ref1[3][:-6]
        print(ref1)

    time.sleep(5)

"""
doc_ref.set({
    'cell' : {
        'cell_'+res1.decode('utf-8')[0] : {
            'Soil' : 4,
            'Temp' : 4,
            'Humi' : 4
        }
    }
    # TODO: 센서 측정값, 제어시 사용할 변수값 JSON 형태로 DB 스키마 참고해서 추가할 것
})
"""