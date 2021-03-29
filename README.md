#202103231050
김경호 온라인

#202103291134
현재 시점 아두이노 센서결과 출력방식 및 코드
출력예시(작은따옴표는 한줄단위를 나타냄)
'셀번호,토양수분,조도,온도,습도 '
'cell_1,48,53,0,0 '
'cell_1,38,62,0,0 '
'cell_1,38,62,0,0 '
코드예시
  Serial.print("cell_1");
  Serial.print(",");
  Serial.print(level);
  Serial.print(",");
  Serial.print(lumi);
  Serial.print(",");
  Serial.print((int)temperature);
  Serial.print(",");
  Serial.print((int)humidity);
  Serial.println(" ");