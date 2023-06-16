from flask_restful import Resource
from flask import request
import mysql.connector
from mysql.connector import Error

from mysql_connection import get_connection

# API 동작하는 코드를 만들기 위해서는
# class(클래스)를 만들어야 한다. 

# class 란???  비슷한 데이터끼리 모아놓은 것 (테이블 생각)
# 클래스는 변수와 함수로 구성된 묶음
# 테이블과 다른점 : 함수가 있다는 점!!

# API 를 만들기 위해서는, 
# flask_restful 라이브러리의 Resource 클래스를!!!
# 상속해서 만들어야 한다. 파이썬에서 상속은 괄호!

class RecipeListResource(Resource) :
    
    def post(self) :

        # {
        #     "name": "김치찌게",
        #     "description": "맛있게 끓이는 방법",
        #     "num_of_servings": 4,
        #     "cook_time": 30,
        #     "directions": "고기볶고 김치넣고 물뭇고 두부넣고",
        #     "is_publish": 1
        # }
        
        # 1. 클라이언트가 보낸 데이터를 받아온다.
        data = request.get_json()

        print(data)

        # 2. DB 에 저장한다.        
        try :
            # 2-1. 데이터베이스를 연결한다. 
            connection = get_connection()

            # 2-2. 쿼리문 만든다 
            ###### 중요!!! 컬럼과 매칭되는 데이터만 %s 로 바꿔준다.
            query = '''insert into recipe
                    (name, description, num_of_servings, cook_time,
                        directions , is_publish)
                    values
                    (%s, %s, %s, %s, %s, %s);'''
            # 2-3. 쿼리에 매칭되는 변수 처리!  중요! 튜플로 처리해준다!
            record = ( data['name'], data['description'],
                      data['num_of_servings'],
                       data['cook_time'],
                        data['directions'],
                         data['is_publish'] )
            # 2-4. 커서를 가져온다.
            cursor = connection.cursor()

            # 2-5. 쿼리문을, 커서로 실행한다.
            cursor.execute(query, record)

            # 2-6. DB에 반영 완료하라는, commit 해줘야 한다.
            connection.commit()

            # 2-7. 자원해제
            cursor.close()
            connection.close()

        except Error as e :
            print(e)
            return {'result' : 'fail', 'error' : str(e) }, 500



        # 3. 에러가 났으면, 에러났다고 알려주고,
        #    그렇지 않으면, 잘 저장되었다고 알려준다.

        return {'result' : 'success'}
    
    
    
    def get(self) :
        # 여러분의 코드 작성.
        print("레시피 가져오는 API 동작했음.")


        return {'result' : 'success', 'count' : 3}, 400
        













