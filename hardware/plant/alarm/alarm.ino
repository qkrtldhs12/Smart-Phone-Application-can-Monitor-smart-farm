#define beep 6      // 부저연결핀(이진으로 작동하는 수동부저 추천)
#define led 7       // LED연결핀
#define signal 8    // 수신신호연결핀(기본적인 유선신호)

void turnOn(int,int);
void turnOff(int,int);

void setup(){
    pinMode(beep,OUTPUT);
    pinMode(led,OUTPUT);
    pinMode(signal,INPUT);
}

void loop(){
    if(digitalRead(signal)==HIGH){
        turnOn(beep,led);
    }
    else{
        turnOff(beep,led);
    }
}

void turnOn(int a,int b){
    digitalWrite(a,HIGH);
    digitalWrite(b,LOW);
    delay(500);
    digitalWrite(a,LOW);
    digitalWrite(b,HIGH);
    delay(500);
}

void turnOff(int a,int b){
    digitalWrite(a,LOW);
    digitalWrite(b,LOW);
    delay(500);
}