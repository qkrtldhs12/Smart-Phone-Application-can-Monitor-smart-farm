from serial import Serial
import time
import firebase_admin
from firebase_admin import credentials
from firebase_admin import firestore

'''
# 센싱아두이노 시리얼포트 연결-라즈베리파이
ser1=Serial('/dev/ttyACM0',9600)
ser2=Serial('/dev/ttyACM1',9600)
'''
# 센싱아두이노 시리얼포트 연결-윈도우10
ser1=Serial('COM7',9600)
ser2=Serial('COM10',9600)

cred = credentials.Certificate("spam-85498-firebase-adminsdk-wsavj-a18faa0fe2.json")
# 파이어베이스 연결용 비공개 키
firebase_admin.initialize_app(cred)
db = firestore.client() # Firestore 객체 생성
Device_id = "12345" # 각 기기별 고유번호, User_info 밑에 저장될 데이터
doc_ref = db.collection('Device_data').document(Device_id)
# Firestore에서 Device_data 컬렉션 내부에 Device_id를 이름으로 현재 데이터값 갱신

while ser1.readable() or ser2.readable():
    if ser1.readable():
        res1=ser1.readline()
        res1=str(res1)
        ref1=list(res1.split(','))
        ref1[0]=ref1[0][-1]
        ref1[3]=ref1[3][:-6]
        print(ref1)

    if ser2.readable():
        res2=ser2.readline()
        res2=str(res2)
        ref2=list(res2.split(','))
        ref2[0]=ref2[0][-1]
        ref2[3]=ref2[3][:-6]
        print(ref2)

    doc_ref.set({
    'cell' : {
        'cell_'+ref1[0] : {
            'Soil' : ref1[1],
            'Temp' : ref1[2],
            'Humi' : ref1[3]
        },
        'cell_'+ref2[0] : {
            'Soil' : ref2[1],
            'Temp' : ref2[2],
            'Humi' : ref2[3]
        }
    }
})
    # TODO: 센서 측정값, 제어시 사용할 변수값 JSON 형태로 DB 스키마 참고해서 추가할 것
    
    # TODO: 접속 형태만 만들었으니 알아서 자동갱신 할 수 있게 모듈화 해서 쓸 것
    try:
        doc = doc_ref.get()
        print("Document data :".format(doc.to_dict()))
    except:
        print("No such doc")

    time.sleep(5)