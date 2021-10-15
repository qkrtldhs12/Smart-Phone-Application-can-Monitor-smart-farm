#include<Servo.h>

// 아두이노에 연결되는 핀
#define door1 4     // 케이스 개폐1
#define door2 5     // 케이스 개폐2
#define water1 6    // 셀1 토양수분
#define water2 7    // 셀2 토양수분
#define light 8     // 조명제어
#define aero1 9     // 대기수분
#define aero2 10     // 대기수분
#define heat1 11    // 셀1 열선
#define heat2 12    // 셀2 열선

// 전역변수
int moment = 1000;  // 작동 간 대기시간
int servo1Pin = door1;  // 케이스 개폐 서보모터 객체
int servo2Pin = door2;  // 케이스 개폐 서보모터 객체
int angle = 0;

char w;
String data="";
char s1, t1, h1, s2, t2, h2;
char d, l, a, v, h;

// 객체선언
Servo servo1;
Servo servo2;

void setup() {
  Serial.begin(9600);
  servo1.attach(servo1Pin);
  servo2.attach(servo2Pin);

  pinMode(water1, OUTPUT);
  pinMode(water2, OUTPUT);
  pinMode(light, OUTPUT);
  pinMode(aero1, OUTPUT);
  pinMode(aero2, OUTPUT);
  pinMode(heat1, OUTPUT);
  pinMode(heat2, OUTPUT);
}

void loop() {
  if (Serial.available() > 0) { // 데이터가 수신되는지 감지
    w = Serial.read();
    //Serial.print(w);
    data.concat(w);
    //Serial.print(data); //데이터를 잘 받았는지 확인
    if (data[0] == 'a') {
      s1 = data[1];
      t1 = data[2];
      h1 = data[3];
      s2 = data[4];
      t2 = data[5];
      h2 = data[6];
      Serial.print(s1);
      Serial.print(t1);
      Serial.print(h1);
      Serial.print(s2);
      Serial.print(t2);
      Serial.print(h2);
    }
    else if (data[0] == 'c') {
      d = data[1];
      l = data[2];
      a = data[3];
      v = data[4];
      h = data[5];
    }
  }

  // cell1 수분제어
  if (s1 == '1') {
    digitalWrite(water1, HIGH);
    delay(15);
  }
  else {
    digitalWrite(water1, LOW);
    delay(15);
  }
  // cell2 수분제어
  if (s2 == '1') {
    digitalWrite(water2, HIGH);
    delay(15);
  }
  else {
    digitalWrite(water2, LOW);
    delay(15);
  }

  // cell1 온도제어
  if (t1 == '1') {
    digitalWrite(heat1, HIGH);
    delay(15);
  }
  else {
    digitalWrite(heat1, LOW);
    delay(15);
  }
  // cell2 온도제어
  if (t2 == '1') {
    digitalWrite(heat2, HIGH);
    delay(15);
  }
  else {
    digitalWrite(heat2, LOW);
    delay(15);
  }

  // cell1 가습제어
  if (h1 == '1') {
    digitalWrite(aero1, HIGH);
    delay(15);
  }
  else {
    digitalWrite(aero1, LOW);
    delay(15);
  }
  // cell2 가습제어
  if (h2 == '1') {
    digitalWrite(aero2, HIGH);
    delay(15);
  }
  else {
    digitalWrite(aero2, LOW);
    delay(15);
  }
}
