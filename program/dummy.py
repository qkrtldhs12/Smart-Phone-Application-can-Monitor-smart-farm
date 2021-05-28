# 난 필요한 모듈을 위에 모아두지
import random
import csv
from datetime import datetime, timedelta

# 난 객체 선언부도 모두 모아두지
f1=open('dataset1_0.csv','w',newline='')
f2=open('dataset2_0.csv','w',newline='')
f3=open('dataset3_0.csv','w',newline='')
wr1=csv.writer(f1)
wr2=csv.writer(f2)
wr3=csv.writer(f3)
wr1.writerow(['index','date','temp','humi','lumi','soil'])
wr2.writerow(['index','date','temp','humi','lumi','soil'])
wr3.writerow(['index','date','temp','humi','lumi','soil'])

curDate=datetime(2021,4,1,0,0)
dotDate1=curDate+timedelta(weeks=6)
dotDate2=curDate+timedelta(weeks=10)
dotDate3=curDate+timedelta(weeks=22)
finDate=curDate+timedelta(weeks=26)
num=1

gap=((finDate-curDate).days)*24*60
gap1=((dotDate1-curDate).days)*24*60
gap2=((dotDate2-dotDate1).days)*24*60
gap3=((dotDate3-dotDate2).days)*24*60
gap4=((finDate-dotDate3).days)*24*60

# dataset case1
# 발아시점에 대해서만 상태가 변화
for num in list(range(1,gap+1)):
    if num<gap1:
        # 파종 시 환경변수
        temp=round(random.randrange(20,30)+random.random(),2)
        humi=round(random.randrange(60,70)+random.random(),2)
        lumi=round(random.randrange(60,70)+random.random(),2)
        soil=round(random.randrange(7,10)+random.random(),2)
        time=curDate
        wr1.writerow([num,time,temp,humi,lumi,soil])
        curDate=curDate+timedelta(minutes=1)
    else:
        # 생장 시 환경변수
        temp=round(random.randrange(16,22)+random.random(),2)
        humi=round(random.randrange(60,70)+random.random(),2)
        lumi=round(random.randrange(60,70)+random.random(),2)
        soil=round(random.randrange(5,8)+random.random(),2)
        time=curDate
        wr1.writerow([num,time,temp,humi,lumi,soil])
        curDate=curDate+timedelta(minutes=1)

# dataset case2
# 발아시점과 열매맺는시점에 대해 상태가 변화
for num in list(range(1,gap+1)):
    if num<gap1:
        # 파종 시 환경변수
        temp=round(random.randrange(20,30)+random.random(),2)
        humi=round(random.randrange(60,70)+random.random(),2)
        lumi=round(random.randrange(60,70)+random.random(),2)
        soil=round(random.randrange(7,10)+random.random(),2)
        time=curDate
        wr2.writerow([num,time,temp,humi,lumi,soil])
        curDate=curDate+timedelta(minutes=1)
    elif num>=gap1 and num<gap2:
        # 생장 시 환경변수
        temp=round(random.randrange(16,22)+random.random(),2)
        humi=round(random.randrange(60,70)+random.random(),2)
        lumi=round(random.randrange(60,70)+random.random(),2)
        soil=round(random.randrange(7,10)+random.random(),2)
        time=curDate
        wr2.writerow([num,time,temp,humi,lumi,soil])
        curDate=curDate+timedelta(minutes=1)
    else:
        # 생장 시 환경변수
        temp=round(random.randrange(16,20)+random.random(),2)
        humi=round(random.randrange(60,70)+random.random(),2)
        lumi=round(random.randrange(60,70)+random.random(),2)
        soil=round(random.randrange(5,8)+random.random(),2)
        time=curDate
        wr1.writerow([num,time,temp,humi,lumi,soil])
        curDate=curDate+timedelta(minutes=1)

# dataset case3
# 발아시점과 열매맺는시점, 그 이후에 대해 상태가 변화
for num in list(range(1,gap+1)):
    if num<gap1:
        # 파종 시 환경변수
        temp=round(random.randrange(20,30)+random.random(),2)
        humi=round(random.randrange(60,70)+random.random(),2)
        lumi=round(random.randrange(60,70)+random.random(),2)
        soil=round(random.randrange(7,10)+random.random(),2)
        time=curDate
        wr2.writerow([num,time,temp,humi,lumi,soil])
        curDate=curDate+timedelta(minutes=1)
    elif num>=gap1 and num<gap2:
        # 생장 시 환경변수
        temp=round(random.randrange(16,22)+random.random(),2)
        humi=round(random.randrange(60,70)+random.random(),2)
        lumi=round(random.randrange(60,70)+random.random(),2)
        soil=round(random.randrange(7,10)+random.random(),2)
        time=curDate
        wr2.writerow([num,time,temp,humi,lumi,soil])
        curDate=curDate+timedelta(minutes=1)
    elif num>=gap2 and num<gap3:
        # 생장 시 환경변수
        temp=round(random.randrange(18,25)+random.random(),2)
        humi=round(random.randrange(60,70)+random.random(),2)
        lumi=round(random.randrange(60,70)+random.random(),2)
        soil=round(random.randrange(5,8)+random.random(),2)
        time=curDate
        wr2.writerow([num,time,temp,humi,lumi,soil])
        curDate=curDate+timedelta(minutes=1)
    else:
        # 생장 시 환경변수
        temp=round(random.randrange(16,20)+random.random(),2)
        humi=round(random.randrange(60,70)+random.random(),2)
        lumi=round(random.randrange(60,70)+random.random(),2)
        soil=round(random.randrange(2,4)+random.random(),2)
        time=curDate
        wr1.writerow([num,time,temp,humi,lumi,soil])
        curDate=curDate+timedelta(minutes=1)

f1.close()
f2.close()
f3.closr()