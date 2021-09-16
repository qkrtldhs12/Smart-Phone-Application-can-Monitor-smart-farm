#include "DHT.h"
#include "soilLevel.h"

#define DHTPIN 2    // 온습도센서
#define DHTTYPE DHT11
#define waterOUT 6  // 수분공급펌프
#define red 11       // 적색LED
#define green 10    // 녹색LED
#define blue 9     // 청색LED
#define waterIN A0  // 수분감지센서
#define lux A1      // 조도센서

DHT dht(DHTPIN, DHTTYPE);

int moment = 3000;
int lumi;  // 조도센서 측정값
int level; // 수분감지센서 측정값
float humidity; // 습도변수
float temperature;  // 온도변수

void setup() {
  Serial.begin(9600);
  dht.begin();

  pinMode(light, OUTPUT);
  pinMode(red, OUTPUT);
  pinMode(green, OUTPUT);
  pinMode(blue, OUTPUT);
  pinMode(waterOUT, OUTPUT);

  Serial.print("Cell_NO");
  Serial.print(",");
  Serial.print("Soil");
  Serial.print(",");
  Serial.print("Lumi");
  Serial.print(",");
  Serial.print("Temp");
  Serial.print(",");
  Serial.print("Humi");
  Serial.println(" ");
}

void loop() {
  level = analogRead(waterIN);
  level = map(level, 0, 1023, 100, 0); // 수분도 퍼센트 변환
  lumi = analogRead(lux);
  lumi = map(lumi, 0, 1023, 100, 0);   // 조도센서는 어두우면 값이 커진다.
  humidity = dht.readHumidity();
  temperature = dht.readTemperature();

  // 조명제어 프로세스
  if (lumi < 70) { // 밝기가 50% 이하일때, 조명 100% 사용
    analogWrite(light, 255);
    delay(moment);
  }
  else if (lumi >= 70 && lumi < 90) { // 밝기가 50%~70% 일때, 조명 60% 사용
    analogWrite(light, 150);
    delay(moment);
  }
  else {  // 밝기가 70% 이상일때, 조명을 소등
    analogWrite(light, 0);
  }

  // 수분제어 프로세스
  if (level > redLevel) {
    // 물이 없어 물을 주지 못하는 상황(적색경보)
    // 적색 LED 출력 및 부저ON
    // 어플리케이션에 물잔량에 관해 알림이 필요한 상황
    analogWrite(red, 150);
    analogWrite(green, 0);
    analogWrite(blue, 0);
    digitalWrite(waterOUT, LOW);
    delay(moment);
  }
  else if (level <= redLevel && level > greenLevel) {
    // 토양수분도가 낮아 수분공급이 필요한 상황(황색경보)
    // 수중펌프를 작동시켜 수분공급
    analogWrite(red, 150);
    analogWrite(green, 150);
    analogWrite(blue, 0);
    digitalWrite(waterOUT, HIGH);
    delay(moment);
  }
  else if (level <= greenLevel && level > blueLevel) {
    // 토양수분도가 적절하여 그대로 두어도 되는 상황(녹색경보)
    // 녹색 LED or 청록색 LED 출력
    analogWrite(red, 0);
    analogWrite(green, 150);
    analogWrite(blue, 0);
    digitalWrite(waterOUT, LOW);
    delay(moment);
  }
  else if (level <= blueLevel) {
    // 토양에 수분이 과하게 많은 경우(청색경보)
    // 청색 LED 출력 및 부저ON
    // 어플리케이션에 이상알림 필요
    analogWrite(red, 0);
    analogWrite(green, 0);
    analogWrite(blue, 150);
    digitalWrite(waterOUT, LOW);
    delay(moment);
  }
  else {
    // 이상점 발생시 셧다운
    analogWrite(red, 0);
    analogWrite(green, 0);
    analogWrite(blue, 0);
    digitalWrite(waterOUT, LOW);
    delay(moment);
  }

  // 시리얼 모니터를 통해 출력되는 값들
  Serial.print("cell_1"); // 셀 숫자는 각 셀마다 바꿔줘야함
  Serial.print(",");
  Serial.print(level);
  Serial.print(",");
  Serial.print(lumi);
  Serial.print(",");
  Serial.print((int)temperature);
  Serial.print(",");
  Serial.print((int)humidity);
  Serial.println(" ");
}
