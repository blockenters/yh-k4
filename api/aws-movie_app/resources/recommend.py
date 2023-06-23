from flask_jwt_extended import get_jwt_identity, jwt_required
from flask_restful import Resource
from flask import request
import mysql.connector
from mysql.connector import Error

from mysql_connection import get_connection

import pandas as pd

class MovieRecommendResource(Resource):

    @jwt_required()
    def get(self):

        user_id = get_jwt_identity()

        corr_movie = pd.read_csv('data/corr_movie.csv', index_col=0)

        print(corr_movie)

        # 유저가 준 별점정보를 DB에서 가져온다.
        try :
            connection = get_connection()

            query = '''select m.title, r.rating 
                    from rating r
                    join movie m
                        on r.movieId = m.id
                    where r.userId = %s;'''
            record = (user_id, )

            cursor = connection.cursor(dictionary=True)
            cursor.execute(query, record)

            result_list = cursor.fetchall()

            # 영화 id 값 처리를 위한 쿼리 
            query = '''select id, title
                    from movie;'''
            cursor.execute(query)
            movie_list = cursor.fetchall()

            cursor.close()
            connection.close()

        except Error as e:
            print(e)
            return {'result':'fail', 'error' : str(e)}, 500

        # 상관계수가 있는 데이터프레임에서, 
        # 유저가 본 영화만 가져와서 가중치 계산하고,
        # 정렬하여서 클라이언트에게 보내준다.

        user_df = pd.DataFrame(result_list)

        similar_movies_list = pd.DataFrame()

        for i in range( user_df.shape[0] ) :
            movie_title = user_df['title'][i]
            recom_movies = corr_movie[movie_title].dropna().sort_values(ascending=False).to_frame()
            recom_movies.columns = ['correlation']
            recom_movies['weight'] = recom_movies['correlation'] * user_df['rating'][i]
            similar_movies_list = pd.concat(  [similar_movies_list, recom_movies] )

        # weight 로 정렬
        similar_movies_list.sort_values('weight', ascending = False, inplace=True)

        # 내가 이미 본영화는 제거
        for name in user_df['title'] :
            if name in similar_movies_list.index :
                similar_movies_list.drop(name, axis = 0, inplace=True)
        
        # 중복 추천된 영화도 정리
        recom_df = similar_movies_list.groupby('title')['weight'].max().sort_values(ascending=False).head(10).to_frame().reset_index()

        print(recom_df)

        
        # 영화의 아이디값도 붙여준다. 
        movie_df = pd.DataFrame(movie_list)
        
        recom_df = pd.merge( recom_df , movie_df, on = 'title')


        print( recom_df.to_dict(orient='records') )

        result_list = recom_df.to_dict(orient='records')
        
        return {'result' : 'success', 'count': len(result_list),
                'items':result_list }


