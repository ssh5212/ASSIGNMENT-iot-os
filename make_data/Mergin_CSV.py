import glob
import os

input_path = r'D:\Study\hello\IoT\make_data\past' # csv파일들이 있는 디렉토리 위치
output_file = r'D:\Study\hello\IoT\make_data\mergin.csv' # 저장 파일명

file_list = glob.glob(os.path.join(input_path, '*.csv')) # 모든 scv파일 선택

with open(output_file, 'w') as write_file:
	for i, file_name in enumerate (file_list):
		with open(file_name, 'r', encoding='euc-kr') as original_file: # 파일 읽어오기
			n = 0
			while True:
				line = original_file.readline()
				# if n == 0: # 모든 csv 파일 릴레이션 삭제
				if i != 0 and n == 0: # 첫 번쨰 파일이 아니고, 첫 번쨰 라인인 경우 (릴레이션)
					n += 1
					continue
				if not line:
					print(f'File[{i}] Succeed..!')
					break
				write_file.write(line)
			n += 1

print('File Mergin Succeed..!')