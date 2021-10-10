#include<Servo.h>

// 아두이노에 연결되는 핀
#define door1 4     // 케이스 개폐1
#define door2 5     // 케이스 개폐2
#define water1 6    // 셀1 토양수분
#define water2 7    // 셀2 토양수분
#define light 8     // 조명제어
#define aero 10     // 대기수분
#define heat1 11    // 셀1 열선
#define heat1 12    // 셀2 열선

// 전역변수
int moment = 1000;  // 작동 간 대기시간
int servo1Pin = door1;  // 케이스 개폐 서보모터 객체
int servo2Pin = door2;  // 케이스 개폐 서보모터 객체
int angle = 0;

// 객체선언
Servo servo1;
Servo servo2;

void setup() {
  Serial.begin(9600);
  servo1.attach(servo1Pin);
  servo2.attach(servo2Pin);

  pinMode(water, OUTPUT);
  pinMode(light, OUTPUT);
  pinMode(aero, OUTPUT);
  pinMode(heat, OUTPUT);
}

void loop() {
  char data;

  if (Serial.available() > 0) { // 데이터가 수신되는지 감지
    data = Serial.read();
    // Serial.println(data); //데이터를 잘 받았는지 확인
  }

  if (data == 'a') {
    for (angle = 0; angle < 90; angle++) {
      servo1.write(angle);
      servo2.write(180 - angle);
      delay(15);
    }
  }
  else if (data == 'b') {
    for (angle = 90; angle > 0; angle--) {
      servo1.write(angle);
      servo2.write(180 - angle);
      delay(15);
    }
  }
  
  if(data=='c'){
    digitalWrite(water,HIGH);
    delay(15);
  }
  else if(data=='d'){
    digitalWrite(water,LOW);
    delay(15);
  }
}
