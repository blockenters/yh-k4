from flask import Flask
from flask_restful import Api

from resources.photo import PhotoResource, PhotoReviewResource

app = Flask(__name__)

api = Api(app)

api.add_resource( PhotoResource ,  '/photo')
api.add_resource( PhotoReviewResource  , '/review')

if __name__ == '__main__':
    app.run()




