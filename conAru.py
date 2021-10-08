from serial import Serial
import time
import firebase_admin
from firebase_admin import credentials
from firebase_admin import firestore
from datetime import datetime

'''
# 센싱아두이노 시리얼포트 연결-라즈베리파이
ser1=Serial('/dev/ttyACM0',9600)
ser2=Serial('/dev/ttyACM1',9600)
'''
# 센싱아두이노 시리얼포트 연결-윈도우10
ser1=Serial('COM7',9600)    #sen
ser2=Serial('COM10',9600)   #con

def conS(s,cel,con):
    '''
    토양수분센서 데이터를 받아 수분공급하는 코드
    코드표기방식
    토양수분공급 on/off : c/d
    ''' 
    
    level=25    # 식물마다 정해진 적정수분량
                # 원래는 스마트팜API에서 받아온 변수로 설정해야함.
    if s<level:
        code='c'
        code=code.encode('utf-8')
        con.write(code)
    else:
        code='d'
        code=code.encode('utf-8')
        con.write(code)
    
    return

while (1):
    if ser1.readable():
        res1=ser1.readline()
        res1=str(res1)
        ref1=list(res1.split(','))
        ref1[0]=ref1[0][-1]
        ref1[3]=ref1[3][:-6]

        conS(int(ref1[1]),'1',ser2)
        print(ref1)

    time.sleep(5)