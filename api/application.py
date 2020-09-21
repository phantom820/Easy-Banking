from flask import Flask,request
from flask_cors import CORS
import client_handler
import account_handler

application = Flask(__name__)
cors = CORS(application)

@application.route("/")
def home():
    return "home"

# handle request about accounts
@application.route("/accounts",methods=['GET','POST','PATCH'])
def accounts():
    handler=account_handler.AccountHandler()
    return  handler.handle_request(request)

@application.rouute("/accounts/pay",methods=["PATCH"])
def accounts_pay():
    handler=account_handler.AccountHandler()
    return  handler.
#handle request for bookings mostly client app
@application.route("/clients",methods=['GET','POST','PATCH'])
def tenants():
    handler=client_handler.ClientHandler()
    return handler.handle_request(request)    

if __name__=="__main__":
    application.run(host='0.0.0.0',debug=True)
