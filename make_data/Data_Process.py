import collections
import pandas as pd

data = pd.read_csv('D:\Study\hello\IoT_OS\make_data\mergin.csv', encoding='cp949') # 파일 읽기
data = data.drop(['지점', '지점명'], axis=1) # 사용하지 않는 row 삭제
data['강수량(mm)'] = data['강수량(mm)'].fillna(0) # 강수량 누락 값을 0으로 채움
data = data.fillna(method='bfill') # 바로 앞 데이터로 채움
data = data.fillna(method='ffill') # 바로 뒤 데이터로 채움

data.to_csv('data.csv', header=True, index=False) # save