from flask_jwt_extended import get_jwt_identity, jwt_required
from flask_restful import Resource
from flask import request
import mysql.connector
from mysql.connector import Error
from config import Config

from mysql_connection import get_connection

class LikeResource(Resource) :

    @jwt_required()
    def post(self, post_id) :
        
        user_id = get_jwt_identity()

        try :
            connection = get_connection()
            query = '''insert into likes
                    (postId, userId)
                    values
                    ( %s, %s );'''
            record = (post_id, user_id)
            cursor = connection.cursor()
            cursor.execute(query, record)
            connection.commit()

            cursor.close()
            connection.close()

        except Error as e:
            print(e)
            return {'result':'fail','error':str(e)},500

        return {'result':'success'}
    
    @jwt_required()
    def delete(self, post_id) :
        user_id = get_jwt_identity()

        try :
            connection = get_connection()
            query = '''delete from likes
                    where postId = %s and userId = %s;'''
            record = (post_id, user_id)
            cursor = connection.cursor()
            cursor.execute(query, record)
            connection.commit()

            cursor.close()
            connection.close()

        except Error as e:
            print(e)
            return {'result':'fail','error':str(e)},500

        return {'result':'success'}






