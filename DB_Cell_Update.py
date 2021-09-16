import firebase_admin
from firebase_admin import credentials
from firebase_admin import firestore

cred = credentials.Certificate("spam-85498-firebase-adminsdk-wsavj-a18faa0fe2.json")
# 파이어베이스 연결용 비공개 키
firebase_admin.initialize_app(cred)
db = firestore.client() # Firestore 객체 생성
Device_id = "12345" # 각 기기별 고유번호, User_info 밑에 저장될 데이터

doc_ref = db.collection('Device_data').document(Device_id)
# Firestore에서 Device_data 컬렉션 내부에 Device_id를 이름으로 현재 데이터값 갱신
doc_ref.set({
    'cell' : {
        'cell_1' : {
            'Soil' : 4,
            'Lumi' : 4,
            'Temp' : 4,
            'Humi' : 4
        },
        'cell_2' : {
            'Soil' : 4,
            'Lumi' : 3,
            'Temp' : 2,
            'Humi' : 1
        }
    }
    # TODO: 센서 측정값, 제어시 사용할 변수값 JSON 형태로 DB 스키마 참고해서 추가할 것
})

# TODO: 접속 형태만 만들었으니 알아서 자동갱신 할 수 있게 모듈화 해서 쓸 것
try:
    doc = doc_ref.get()
    print("Document data :".format(doc.to_dict()))
except:
    print("No such doc")