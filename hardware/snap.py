import threading
from time import sleep
import time
import firebase_admin
from firebase_admin import credentials
from firebase_admin import firestore
from datetime import datetime
from serial import Serial
import pyfirmata

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

# 파이어베이스 연결용 비공개 키
cred = credentials.Certificate("./spam-85498-firebase-adminsdk-wsavj-7375d295d7.json")
firebase_admin.initialize_app(cred)
db = firestore.client() # Firestore 객체 생성

user_email = "caesar2746@gmail.com"
model_id = "default"   # 모델명(고유값)

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
All_Cell = []   # 디바이스 내의 모든 Cell 정보를 저장하는 리스트
for doc in doc_ref:
    cell = Cell(doc)
    All_Cell.append(cell)

while(1):
    # 제어신호를 받아 컨트롤하는 부분
    # 디바이스 제어
    doc_ref = db.collection("User_info").document(user_email).collection("User_Device").document(model_id).collection("Control").document("Received")
    doc = doc_ref.get()
    if doc.exists:
        door = doc.get("door")
        heat = doc.get("heat")
        humidifier = doc.get("humidifier")
        light = doc.get("light")
        vent = doc.get("vent")

        # 제어신호 처리하는부분
        print(door, heat, humidifier, light, vent)

        # 기존의 제어요청데이터 삭제
        doc_ref.update({
            "connected" : firestore.DELETE_FIELD,
            "door" : firestore.DELETE_FIELD,
            "heat" : firestore.DELETE_FIELD,
            "humidifier" : firestore.DELETE_FIELD,
            "light" : firestore.DELETE_FIELD,
            "model_id" : firestore.DELETE_FIELD,
            "name" : firestore.DELETE_FIELD,
            "vent" : firestore.DELETE_FIELD,
            "viewtype" : firestore.DELETE_FIELD
        })
    else:
        print("Null")

    time.sleep(10)