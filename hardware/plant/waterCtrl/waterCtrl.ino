#define green 6     // 녹색LED
#define red 7       // 적색LED
#define waterOUT 8  // 수분공급펌프
#define waterIN A0  // 수분감지센서

unsigned short level; // 수분감지센서 측정값
unsigned short wet = 15; // 식물마다 적절한 토양수분도

void setup() {
  Serial.begin(9600);

  pinMode(green, OUTPUT);
  pinMode(red, OUTPUT);
  pinMode(waterOUT, OUTPUT);
}

void loop() {
  level = analogRead(waterIN);
  level = map(level, 0, 1023, 0, 100); // 수분도 퍼센트 변환
  
  //현재는 시리얼모니터에 출력하지만,
  //추후 현재시간과 함께 데이터베이스에 저장
  Serial.println(level);

  if (level < wet) {
    // 물을 줘야하는 상황
    digitalWrite(green, LOW);
    digitalWrite(red, HIGH);
    digitalWrite(waterOUT, HIGH);
    delay(10);
  }
  else {
    // 물을 주지않아야하는 상황
    digitalWrite(green, HIGH);
    digitalWrite(red, LOW);
    digitalWrite(waterOUT, LOW);
    delay(10);
  }
  delay(10);
}
