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
        

        # 2-2. 오토 태깅을 위해, AWS 오브젝트 디텍션 서비스 이용.
        #      Rekognition 서비스.

        label_list = self.detect_labels(new_filename, Config.S3_BUCKET)

        print(label_list)

        
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
            # 포스트 테이블에, 포스팅 내용을 넣으면서, 
            # 이 아이디값을 가져와야지, 태그를 만들 수 있다
            post_id = cursor.lastrowid

            print('post_id : ', post_id)

            # 태그 이름을 하나씩 가져와서, 테이블에 있는지 확인한다.
            # 테이블에 없으면, insert 해준다. 
            query2 = '''select *
                        from tag_name
                        where name = %s;'''
            query3 = '''insert into tag_name
                        (name)
                        values
                        (%s);'''
            query4 = '''insert into tag
                        (postId, tagNameId)
                        values
                        (%s, %s);'''
            
            for label in label_list : 
                record2 = (label , )
                print(label)
                cursor = connection.cursor(dictionary=True)
                cursor.execute(query2, record2)
                result_list = cursor.fetchall()

                if len(result_list) == 0 :
                    record3 = (label , )
                    cursor.execute(query3, record3)
                    tag_name_id = cursor.lastrowid

                    print('count == 0', 'tag_name_id', tag_name_id, ', label : ', label)
                else :
                    tag_name_id = result_list[0]['id']

                    print('count == 1', 'tag_name_id', tag_name_id, ', label : ', label)
                
                # 태그 테이블에 데이터 insert 
                record4 = (post_id, tag_name_id)
                cursor.execute(query4, record4)

            connection.commit()
            cursor.close()
            connection.close()

        except Error as e :
            print(str(e))
            return {'result':'fail', 'error':str(e)},500


        # 4. 데이터 가공해서 클라이언트에 응답한다. 
        
        return {'result' : 'success'}

    def detect_labels(self, photo, bucket):

        client=boto3.client('rekognition', 
                            'us-east-1',
                            aws_access_key_id = Config.AWS_ACCESS_KEY_ID,
                            aws_secret_access_key = Config.AWS_SECRET_ACCESS_KEY)

        response = client.detect_labels(Image={'S3Object':{'Bucket':bucket,'Name':photo}},
            MaxLabels=5)

        print('Detected labels for ' + photo) 
        print()   

        label_list = []

        for label in response['Labels']:

            label_list.append(label['Name'])

            print ("Label: " + label['Name'])
            # print ("Confidence: " + str(label['Confidence']))
            # print ("Instances:")
            # for instance in label['Instances']:
            #     print ("  Bounding box")
            #     print ("    Top: " + str(instance['BoundingBox']['Top']))
            #     print ("    Left: " + str(instance['BoundingBox']['Left']))
            #     print ("    Width: " +  str(instance['BoundingBox']['Width']))
            #     print ("    Height: " +  str(instance['BoundingBox']['Height']))
            #     print ("  Confidence: " + str(instance['Confidence']))
            #     print()

            # print ("Parents:")
            # for parent in label['Parents']:
            #     print ("   " + parent['Name'])
            # print ("----------")
            print ()
        return label_list


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

    
    @jwt_required()
    def delete(self, post_id) :

        user_id = get_jwt_identity()

        try :
            connection = get_connection()
            query = '''delete from post
                        where id = %s and userId = %s;'''
            record = (post_id, user_id)
            cursor = connection.cursor()
            cursor.execute(query, record)
            connection.commit()
            cursor.close()
            connection.close()

        except Error as e:
            print(str(e))
            return {'result':'fail', 'error':str(e)},500

        return {'result' : 'success'}









