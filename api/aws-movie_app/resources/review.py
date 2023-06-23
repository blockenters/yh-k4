from flask_jwt_extended import get_jwt_identity, jwt_required
from flask_restful import Resource
from flask import request
import mysql.connector
from mysql.connector import Error

from mysql_connection import get_connection

class ReviewListResource(Resource) : 

    @jwt_required()
    def post(self, movie_id) :

        # 1. 클라이언트로부터 데이터 받아온다.
        user_id = get_jwt_identity()

        data = request.get_json()

        try :
            connection = get_connection()

            query = '''insert into rating
                    (userId, movieId, rating)
                    values
                    ( %s, %s, %s);'''
            record = (user_id, movie_id, data['rating'])

            cursor = connection.cursor()
            cursor.execute(query, record)
            connection.commit()
            
            cursor.close()
            connection.close()

        except Error as e:
            print(e)
            return {'result':'fail', 'error':str(e)},500

        return {'result':'success'}



