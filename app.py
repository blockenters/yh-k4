import streamlit as st
import pandas as pd
from sklearn.preprocessing import LabelEncoder, OneHotEncoder
from sklearn.compose import ColumnTransformer
from sklearn.preprocessing import MinMaxScaler

def main() :
    st.title('K-Means 클러스터링 앱')

    # 1. csv 파일을 업로드 할수 있다.
    csv_file = st.file_uploader('CSV 파일 업로드', type=['csv'])

    if csv_file is not None :
        # 업로드한 csv파일을 데이터프레임으로 읽고
        df = pd.read_csv(csv_file)

        st.dataframe( df )

        st.subheader('Nan 데이터 확인')

        st.dataframe( df.isna().sum() )

        st.subheader('결측값 처리한 결과')

        df = df.dropna()
        df.reset_index(inplace = True, drop= True)

        st.dataframe( df )

        st.subheader('클러스터링에 사용할 컬럼 선택')

        selected_columns = st.multiselect('X로 사용할 컬럼을 선택하세요.', df.columns)

        if len(selected_columns) != 0 :

            X = df[selected_columns]
            st.dataframe( X )

            # 숫자로된 새로운 데이터프레임 만든다.
            X_new = pd.DataFrame()

            for name in X.columns :
            # print(name)

                # 데이터가 문자열이면, 데이터의 종류가 몇개인지 확인한다.
                if X[ name ].dtype == object :

                    if X[name].nunique() >= 3 :
                    # 원핫 인코딩한다.
                        ct = ColumnTransformer( [('encoder', OneHotEncoder() ,[0] )] , 
                                        remainder= 'passthrough' )
                        
                        col_names = sorted( X[name].unique() )

                        X_new[col_names] = ct.fit_transform(  X[name].to_frame()  )

                    else :
                        # 레이블 인코딩 한다.
                        label_encoder = LabelEncoder()
                        X_new[name] = label_encoder.fit_transform( X[name] )
                
                # 숫자 데이터일때의 처리는 여기서
                else :
                    X_new[name] = X[name]

            st.subheader('문자열은 숫자로 바꿔줍니다.')
            st.dataframe(X_new)

            st.subheader('피처 스케일링 합니다.')
            scaler = MinMaxScaler()
            X_new = scaler.fit_transform(X_new)
            st.dataframe(X_new)

if __name__ == '__main__' :
    main()


