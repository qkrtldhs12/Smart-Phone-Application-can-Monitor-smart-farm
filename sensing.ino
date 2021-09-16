#include "DHT.h"
#include "soilLevel.h"

#define DHTPIN 2    // 온습도센서
#define DHTTYPE DHT11
#define waterIN A0  // 수분감지센서

DHT dht(DHTPIN, DHTTYPE);

int moment = 3000;
int level; // 수분감지센서 측정값
float humidity; // 습도변수
float temperature;  // 온도변수

void setup() {
  Serial.begin(9600);
  dht.begin();
  /*
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
  */
}

void loop() {
  level = analogRead(waterIN);
  level = map(level, 0, 1023, 100, 0); // 수분도 퍼센트 변환
  humidity = dht.readHumidity();
  temperature = dht.readTemperature();

  // 수분제어 프로세스
  if (level > redLevel) {
    // 어플리케이션에 물잔량에 관해 알림이 필요한 상황
    digitalWrite(waterOUT, LOW);
  }
  else if (level <= redLevel && level > greenLevel) {
    // 수중펌프를 작동시켜 수분공급
    digitalWrite(waterOUT, HIGH);
  }
  else if (level <= greenLevel && level > blueLevel) {
    // 토양수분도가 적절하여 그대로 두어도 되는 상황
    digitalWrite(waterOUT, LOW);
  }
  else if (level <= blueLevel) {
    // 토양에 수분이 과하게 많은 경우
    // 어플리케이션에 이상알림 필요
    digitalWrite(waterOUT, LOW);
  }
  else {
    // 이상점 발생시 셧다운
    digitalWrite(waterOUT, LOW);
  }
  delay(moment);

  // 시리얼 모니터를 통해 출력되는 값들
  Serial.print("1"); // 셀 숫자는 각 셀마다 바꿔줘야함
  Serial.print(",");
  Serial.print(level);
  Serial.print(",");
  Serial.print((int)temperature);
  Serial.print(",");
  Serial.print((int)humidity);
  Serial.println(" ");
}
