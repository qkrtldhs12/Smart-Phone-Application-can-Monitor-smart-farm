#define toggle 4    // 라즈베리파이 신호
#define light 5   // 식물조명제어
#define lux A0      // 조도센서

int lumi;
int sign;

void setup() {
  Serial.begin(9600);
  pinMode(toggle,INPUT);
  pinMode(light, OUTPUT);
}

void loop() {
  lumi=analogRead(A0);
  lumi=map(lumi,0,1023,100,0);
  Serial.println(lumi);

  sign=digitalRead(toggle);

  if(sign==true){
    analogWrite(light,lumi);
    delay(100);
  }
  else{
    analogWrite(light,lumi);
    delay(100);
  }
  
  delay(300);
}
