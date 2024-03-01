Contador= 0
Path='Projeto/Logs/Maregrafo/'

def main():
    while True:
        global Path
        os.makedirs(Path, exist_ok=True)
        Date=datetime.datetime.now()
        File_name=Path+Date.strftime('ECXX5s'+'%Y%m%d') + '.dat'
        DataToSend=Data(Date.strftime("%Y-%m-%d %H:%M:%S"))
        with open(File_name,'a') as File:
            File.write(DataToSend)
            File.close()
        with open(Path+"Send.txt",'a') as File:
            File.write(DataToSend)
            File.close()
        time.sleep(5)


def Data(Date):
    Profundidade= round(random.uniform(0,2),3)
    global Contador
    Contador +=1
    Info = f"{Date}  {Profundidade}  0  'Teste' {Contador}\n"
    return Info
