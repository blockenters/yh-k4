def save_uploaded_file(directory, file) :
    # 1. 저장할 디렉토리가 있는지 확인하고,
    #    없으면 디렉토리를 먼저 만든다.
    if not os.path.exists(directory) : 
        os.makedirs(directory)
    # 2. 디렉토리가 있으니, 파일 저장
    with open(os.path.join(directory, file.name), 'wb') as f :
        f.write(file.getbuffer())
    return st.success('파일 저장 완료')
  
  
