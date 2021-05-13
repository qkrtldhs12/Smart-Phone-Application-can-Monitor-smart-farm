#define water 6
#define light 7

void setup() {
  pinMode(water, OUTPUT);
  pinMode(light, OUTPUT);
}

void loop() {
  digitalWrite(light,HIGH);
  delay(100);
}
