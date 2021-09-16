#include "soilLevel.h"

#define waterOUT 6  // 수분공급펌프
#define light 9

int moment = 3000;

void setup() {
  Serial.begin(9600);

  pinMode(light, OUTPUT);
  pinMode(waterOUT, OUTPUT);
}

void loop() {
}
