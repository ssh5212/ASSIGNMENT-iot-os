import requests
import csv
import xmltodict as xmltodict

url = 'http://apis.data.go.kr/1360000/AsosHourlyInfoService/getWthrDataList'
params ={'serviceKey' : 'VVaN0snYf14wjXJ4ZSDPGH0cOuHxjHPDFnnU4DVrPNEEG5fe0o4OLOvK/TCPn6xZ0DyNCIpi7wZanD6BoL3KBw==', 'pageNo' : '1', 'numOfRows' : '24', 'dataType' : 'XML', 'dataCd' : 'ASOS', 'dateCd' : 'HR', 'startDt' : '20211209', 'startHh' : '00', 'endDt' : '20211210', 'endHh' : '00', 'stnIds' : '108' }
response = requests.get(url, params=params).text
data = xmltodict.parse(response)

data.keys()

for i in range(0, 23):
	tm = data['response']['body']['items']['item'][i]['tm']
	ta = data['response']['body']['items']['item'][i]['ta']
	rn = data['response']['body']['items']['item'][i]['rn']
	if rn is None:
		rn = 0
	ws = data['response']['body']['items']['item'][i]['ws']
	wd = data['response']['body']['items']['item'][i]['wd']
	hm = data['response']['body']['items']['item'][i]['hm']
	ps = data['response']['body']['items']['item'][i]['ps']

	now = [tm, ta, rn, ws, wd, hm, ps]
	f = open('mergin.csv','a', newline='')
	wr = csv.writer(f)
	wr.writerow(now)
	f.close()
	print(i, "번째 완료")

response = requests.get(url, params=params)