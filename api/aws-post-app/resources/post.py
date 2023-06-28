from flask_jwt_extended import get_jwt_identity, jwt_required
from flask_restful import Resource
from flask import request
import mysql.connector
from mysql.connector import Error
from config import Config

from mysql_connection import get_connection

# AWS 의 여러 서비스들을 이용할 수 있는 파이썬 라이브러리
# boto3 는 우리컴에는 설치하고, 람다에는 기본으로 설치되어있으니
# requirements.txt 에는 적을 필요 없다.
import boto3

from datetime import datetime


class PostingListResource(Resource) :

    @jwt_required()
    def post(self) :


        # 1. 클라이언트로부터 데이터를 받아온다.
        user_id = get_jwt_identity()

        if 'photo' not in request.files or \
            'content' not in request.form :
            return {'result' : 'fail', 'error':'필수항목 확인'},400
        
        file = request.files['photo']
        content = request.form['content']

        # 2. 사진부터 S3에 저장한다.
        current_time = datetime.now()

        new_filename = current_time.isoformat().replace(':', '_').replace('.','_') + '_' + str(user_id) + '.jpg'

        try :
            s3 = boto3.client('s3',
                              aws_access_key_id = Config.AWS_ACCESS_KEY_ID,
                              aws_secret_access_key = Config.AWS_SECRET_ACCESS_KEY)
            s3.upload_fileobj(file, 
                              Config.S3_BUCKET,
                              new_filename,
                              ExtraArgs = {'ACL':'public-read',
                                           'ContentType':'image/jpeg'})
        
        except Exception as e:
            print(str(e))
            return {'result':'fail', 'error':str(e)}


        # 3. DB에 사진의 url와 content내용을 저장한다.
        try :
            connection = get_connection()
            query = '''insert into post
                    (userId, imgUrl, content)
                    values
                    ( %s, %s, %s);'''
            record = ( user_id, 
                       Config.S3_BASE_URL+new_filename,
                        content)
            cursor = connection.cursor()
            cursor.execute(query, record)
            connection.commit()
            cursor.close()
            connection.close()

        except Error as e :
            print(str(e))
            return {'result':'fail', 'error':str(e)},500


        # 4. 데이터 가공해서 클라이언트에 응답한다. 
        
        return {'result' : 'success'}



class PostingResource(Resource) :

    @jwt_required()
    def put(self, post_id) :

        user_id = get_jwt_identity()

        print(request.files)

        if 'photo' in request.files :

            file = request.files['photo']
            content = request.form['content']

            # 2. 수정된 사진부터 S3에 저장한다.
            current_time = datetime.now()

            new_filename = current_time.isoformat().replace(':', '_').replace('.','_') + '_' + str(user_id) + '.jpg'

            try :
                s3 = boto3.client('s3',
                                aws_access_key_id = Config.AWS_ACCESS_KEY_ID,
                                aws_secret_access_key = Config.AWS_SECRET_ACCESS_KEY)

               

                s3.upload_fileobj(file, 
                                Config.S3_BUCKET,
                                new_filename,
                                ExtraArgs = {'ACL':'public-read',
                                            'ContentType':'image/jpeg'})
                

            except Exception as e:
                print(str(e))
                return {'result':'fail', 'error':str(e)}

            # DB의 테이블을 업데이트 한다.
            # 새로운 파일 URL과 내용으로 업데이트 한다.

            try :
                connection = get_connection()
                query = '''update post
                        set imgUrl = %s , content = %s
                        where id = %s and userId = %s;'''
                record = ( Config.S3_BASE_URL+new_filename ,
                           content, 
                           post_id,
                           user_id)
                cursor = connection.cursor()
                cursor.execute(query, record)
                connection.commit()

                cursor.close()
                connection.close()

            except Error as e:
                print(str(e))
                return {'result':'fail', 'error':str(e)},500

            # 데이터 가공한후 클라이언트에 응답.
            return {'result':'success'}


        else :

            content = request.form['content']

            try :
                connection = get_connection()
                query = '''update post
                        set content = %s 
                        where id = %s and userId = %s;'''
                record = (content, post_id, user_id)

                cursor = connection.cursor()
                cursor.execute(query, record)
                connection.commit()

                cursor.close()
                connection.close()

            except Error as e:
                print(str(e))
                return {'result':'fail', 'error':str(e)}, 500

            return {'result' : 'success'}

        









