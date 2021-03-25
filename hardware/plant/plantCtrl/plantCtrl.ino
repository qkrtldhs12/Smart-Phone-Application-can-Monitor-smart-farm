/*
   조도측정과 조명제어, 수분도 측정과 수분 공급 통합
*/
#define light 5     // 식물조명제어
#define waterOUT 6  // 수분공급펌프
#define buzz 7      // 물 잔량 경고알람
#define red 9       // 적색LED
#define green 10    // 녹색LED
#define blue 11     // 청색LED
#define waterIN A0  // 수분감지센서
#define lux A1      // 조도센서

int moment = 100;
int lumi;  // 조도센서 측정값
int level; // 수분감지센서 측정값
int wet = 10; // 식물마다 적절한 토양수분도
int gap = 5; // 수분도 경계값

void setup() {
  Serial.begin(9600);

  pinMode(light, OUTPUT);
  pinMode(red, OUTPUT);
  pinMode(green, OUTPUT);
  pinMode(blue, OUTPUT);
  pinMode(waterOUT, OUTPUT);
}

void loop() {
  level = analogRead(waterIN);
  level = map(level, 0, 1023, 0, 100); // 수분도 퍼센트 변환
  lumi = analogRead(lux);
  lumi = map(lumi, 0, 1023, 100, 0);   // 조도센서는 어두우면 값이 커진다.

  // 시리얼 모니터를 통해 출력되는 값들
  Serial.print("cell_1"); // 셀 숫자는 각 셀마다 바꿔줘야함
  Serial.print(",");
  Serial.print(level);
  Serial.print(",");
  Serial.print(lumi);
  Serial.println(" ");

  // 조명제어 프로세스
  if (lumi < 50) { // 밝기가 50% 이하일때, 조명 100% 사용
    analogWrite(light, 255);
    delay(moment);
  }
  else if (lumi >= 50 && lumi < 70) { // 밝기가 50%~70% 일때, 조명 60% 사용
    analogWrite(light, 150);
    delay(moment);
  }
  else {  // 밝기가 70% 이상일때, 조명을 소등
    analogWrite(light, 0);
  }

  // 수분제어 프로세스
  if (level < wet - gap) {
    // 물이 없어 물을 주지 못하는 상황(적색경보)
    // 적색 LED 출력 및 부저ON
    // 어플리케이션에 물잔량에 관해 알림이 필요한 상황
    analogWrite(red, 200);
    analogWrite(green, 0);
    analogWrite(blue, 0);
    digitalWrite(waterOUT, LOW);
    digitalWrite(buzz, HIGH);
    delay(moment);
  }
  else if (level >= wet - gap && level < wet) {
    // 토양수분도가 낮아 수분공급이 필요한 상황(황색경보)
    // 수중펌프를 작동시켜 수분공급
    analogWrite(red, 0);
    analogWrite(green, 200);
    analogWrite(blue, 0);
    digitalWrite(waterOUT, HIGH);
    digitalWrite(buzz, LOW);
    delay(moment);
  }
  else if (level >= wet && level < wet + gap) {
    // 토양수분도가 적절하여 그대로 두어도 되는 상황(녹색경보)
    // 녹색 LED or 청록색 LED 출력
    analogWrite(red, 0);
    analogWrite(green, 0);
    analogWrite(blue, 200);
    digitalWrite(waterOUT, LOW);
    digitalWrite(buzz, LOW);
    delay(moment);
  }
  else if (level >= wet + gap) {
    // 토양에 수분이 과하게 많은 경우(청색경보)
    // 청색 LED 출력 및 부저ON
    // 어플리케이션에 이상알림 필요
    analogWrite(red, 200);
    analogWrite(green, 200);
    analogWrite(blue, 200);
    digitalWrite(waterOUT, LOW);
    digitalWrite(buzz, HIGH);
    delay(moment);
  }
  else {
    // 이상점 발생시 셧다운
    analogWrite(red, 0);
    analogWrite(green, 0);
    analogWrite(blue, 0);
    digitalWrite(waterOUT, LOW);
    digitalWrite(buzz, LOW);
    delay(moment);
  }
  delay(10 * moment);
}
