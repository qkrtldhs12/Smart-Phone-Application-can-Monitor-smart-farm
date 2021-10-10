#include "DHT.h"
#include "soilLevel.h"

#define DHTPIN 2    // 온습도센서
#define DHTTYPE DHT11
#define waterIN A0  // 수분감지센서

DHT dht(DHTPIN, DHTTYPE);

int moment = 1000;
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
  Serial.print("Temp");
  Serial.print(",");
  Serial.print("Humi");
  Serial.println(" ");
  */
}

void loop() {
  humidity = dht.readHumidity();
  temperature = dht.readTemperature();

  // 시리얼 모니터를 통해 출력되는 값들
  Serial.print("2"); // 셀 숫자는 각 셀마다 바꿔줘야함
  Serial.print(",");
  Serial.print(waterIN);
  Serial.print(",");
  Serial.print((int)temperature);
  Serial.print(",");
  Serial.print((int)humidity);
  Serial.println(" ");

  delay(moment);
}
