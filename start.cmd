@echo off
cd D:\Study\hello\IoT_OS\make_data
python Add_Yesterday.py
timeout 30

cd D:\Study\hello\IoT_OS
python train.py
timeout 1000

python test.py
timeout 30

git add *
git commit -m "Data Update"
git push
