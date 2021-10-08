import threading
from time import sleep
import time

import firebase_admin
from firebase_admin import credentials
from firebase_admin import firestore
from datetime import datetime

class Device(object):
    def __init__(self, source):
        self.model_id = source.get("model_id")
        self.name = source.get("name")
        self.connected = source.get("connected")
        self.door = source.get("door")
        self.light = source.get("light")
        self.heat = source.get("heat")
        self.humidifier = source.get("humidifier")
        self.vent = source.get("vent")

    def from_dict(self, source):
        #dict로부터 Device로 변환
        self.model_id = source.get("model_id")
        self.name = source.get("name")
        self.connected = source.get("connected")
        self.door = source.get("door")
        self.light = source.get("light")
        self.heat = source.get("heat")
        self.humidifier = source.get("humidifier")
        self.vent = source.get("vent")

    def to_dict(self):
        #Device로부터 dict로 변환
        new_dict = {
            "model_id" : self.model_id,
            "name" : self.name,
            "connected" : self.connected,
            "door" : self.door,
            "light" : self.light,
            "heat" : self.heat,
            "humidifier" : self.humidifier,
            "vent" : self.vent
        }
        return new_dict

class Cell(object):
    def __init__(self, source):
        self.model_id = source.get("model_id")
        self.name = source.get("name")
        self.humi = source.get("humi")
        self.soil = source.get("soil")
        self.temp = source.get("temp")
        self.viewtype = 1

    def from_dict(self, source):
        self.model_id = source.get("model_id")
        self.name = source.get("name")
        self.humi = source.get("humi")
        self.soil = source.get("soil")
        self.temp = source.get("temp")
        self.viewtype = 1

    def to_dict(self):
        new_dict = {
            "model_id": self.model_id,
            "name": self.name,
            "humi": self.humi,
            "soil": self.soil,
            "temp": self.temp,
            "viewtype": int(1)
        }
        return new_dict

    def diff_check(self, source):
        # dict형태로 데이터 받아서 현재 Cell 데이터에 변화가 없다면 True 반환
        if self.humi != source.get("humi"):
            return False
        elif self.soil != source.get("soil"):
            return False
        elif self.temp != source.get("temp"):
            return False
        else:
            return True

cred = credentials.Certificate("d:/git/SPAM_SF/spam-85498-firebase-adminsdk-wsavj-7375d295d7.json")
# 파이어베이스 연결용 비공개 키
firebase_admin.initialize_app(cred)
db = firestore.client() # Firestore 객체 생성
model_id = "default" # 모델명(고유값)
user_email = "caesar2746@gmail.com"

# 해당하는 모델명을 등록한 유저의 이메일 조회
doc_ref = db.collection("User_Email").document("default")
doc = doc_ref.get()
if doc.exists:
    user_email = doc.get("Email")


# 조회한 유저 이메일로 디바이스 정보 조회 -> 디바이스 실행시 한 번 수행한 뒤엔 제어신호 들어올 때만 조회 및 갱신
doc_ref = db.collection("User_info").document(user_email).collection("User_Device").document(model_id)
device = Device(doc_ref.get())

# 디바이스에 연결된 모든 셀 정보 조회
doc_ref = db.collection("User_info").document(user_email).collection("User_Device").document(model_id).collection("Cell_Data").stream()
All_Cell = [] # 디바이스 내의 모든 Cell 정보를 저장하는 리스트
for doc in doc_ref:
    cell = Cell(doc)
    All_Cell.append(cell)

callback_done = threading.Event()

def on_snapshot(doc_snapshot, changes, read_time):
    for doc in doc_snapshot:
        # User_info ~ Received 경로에서 Received에 변화가 있을 경우 아래 로직이 실행됨
        # 앱에서 제어신호를 Received로 보내면 이쪽에서 처리할 것
        pass
    callback_done.set()

doc_ref = db.collection("User_info").document(user_email).collection("User_Device").document(model_id).collection("Control").document("Received")

doc_watch = doc_ref.on_snapshot(on_snapshot)

# DB에 접근하는 횟수 감소를 위해서 일정 주기가 아닌 센서값에 변화가 생길 때만 DB에 갱신
while(1):
    # received_data는 아두이노로부터 받은 데이터 예시
    received_data = {
        "name": "test",
        "model_id": "test",
        "humi": 68,
        "soil": 14,
        "temp": 21,
        "viewtype": 1
    }
    # 현재 가지고 있는 모든 셀 정보(All_Cell)와 수신한 셀 정보(received_data)를 비교
    for cell in All_Cell:
        # All_Cell에서 name을 기준으로 received_data와 일치하는 셀 검색
        if(received_data.get("name") == cell.to_dict().get("name")):
            # 기존의 셀 정보와 수신한 셀 정보가 동일한지 검사 -> False면 불일치, True면 일치
            if(cell.diff_check(received_data) == False):
                # 셀 정보 업데이트
                cell_update = db.collection("User_info").document(user_email).collection("User_Device").document(model_id).collection("Cell_Data").document(received_data.get("name"))
                cell_update.set(received_data)
                All_Cell[cell]=Cell(received_data)
    
    print('1')
    time.sleep(5)
