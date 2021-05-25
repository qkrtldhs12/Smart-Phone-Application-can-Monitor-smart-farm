# 난 필요한 모듈을 위에 모아두지
import random
import csv
from datetime import datetime, timedelta

# 난 객체 선언부도 모두 모아두지
f=open('dataset.csv','w',newline='')
wr=csv.writer(f)
wr.writerow(['index','date','temp','humi','lumi','soil'])

curDate=datetime(2021,4,1,0,0)
midDate=curDate+timedelta(weeks=6)
finDate=curDate+timedelta(weeks=26)
num=1

#print(curDate)
#print(midDate)
#print(finDate)

sub=((midDate-curDate).days)*24*60
gap=((finDate-curDate).days)*24*60

for num in list(range(1,gap+1)):
    if num<sub:
        # 파종 시 환경변수
        temp=round(random.randrange(20,30)+random.random(),2)
        humi=round(random.randrange(60,70)+random.random(),2)
        lumi=round(random.randrange(60,70)+random.random(),2)
        soil=round(random.randrange(5,8)+random.random(),2)
        time=curDate
        wr.writerow([num,time,temp,humi,lumi,soil])
        curDate=curDate+timedelta(minutes=1)
    else:
        # 생장 시 환경변수
        temp=round(random.randrange(15,20)+random.random(),2)
        humi=round(random.randrange(60,70)+random.random(),2)
        lumi=round(random.randrange(60,70)+random.random(),2)
        soil=round(random.randrange(5,8)+random.random(),2)
        time=curDate
        wr.writerow([num,time,temp,humi,lumi,soil])
        curDate=curDate+timedelta(minutes=1)

f.close()