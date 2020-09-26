import pymongo
from bson.json_util import dumps
from dotenv import load_dotenv
import data_models
import data_processing
import pandas as pd
import os
from datetime import datetime


#load connection string
load_dotenv()

class AccountHandler:

	def __init__(self):
		self.client=pymongo.MongoClient(os.getenv("MONGO_URL"))
		self.database=self.client["Easy-Banking"]
		self.accounts=self.database["Accounts"]


	def handle_request(self,request):
		if request.method=="GET":
			identity_number=request.args.get("identity_number")
			parameters={}
			parameters["identity_number"]=identity_number
			return self.get_data(parameters)

		elif request.method=="POST":
			return self.post_data(request.get_json())

		elif request.method=="PATCH":
			return self.patch_data(request.get_json())

#get an account 
	def get_data(self,parameters):
		if parameters!=None:
			try:
				query={"identity_number":parameters["identity_number"]}
				doc=self.accounts.find(query)
				return dumps(doc)
      
			except:
						return "Account get error"

		else:
				return "parameters error"

#create a new account 
	def post_data(self,parameters):
		if parameters!=None:
			try:
					account_number=9283400+self.accounts.count()
					query = { "account":account_number}
					doc=self.accounts.find(query)
					if doc.count()==0:
							account=data_models.Account(account_number,parameters["type"],parameters["balance"],parameters["active"],parameters["identity_number"],[])
							account=account.to_map()
							self.accounts.insert_one(account)
							return str(account)
					
					#must implement proper error handling
					else:
							return "account creation error"

			except:
						return "500 Internal Server Error"

#update an existing account
	def patch_data(self,parameters):
		if parameters!=None:
			try:
					debits=parameters.pop("debits",None)
					query = { "account_number": parameters["account_number"]}
					new_values = { "$set": parameters}
					self.accounts.update_one(query, new_values)

					if debits!=None:
						self.accounts.update({"account_number":parameters["account_number"]},{ "$push": { "debits": { "$each": debits} } })
					
					return str(new_values)
					
			except:
					return "Account update error"

#make a payment
	def make_payment(self,request):
		parameters=request.get_json()
		payer=parameters["payer"]
		payee=parameters["payee"]

		payer_debit=[{"date":datetime.now().timestamp(),"debit_amount":payer["debit_amount"],"balance":payer["balance"],
		"description":payer["description"],"reference":payer["reference"]}]
		payer_parameters={"account_number":int(parameters["payer_account"]),"balance":payer["balance"],"debits":payer_debit}

		payee_debit=[{"date":datetime.now().timestamp(),"debit_amount":payee["debit_amount"],
		"description":payee["description"],"reference":payee["reference"]}]
		payee_parameters={"account_number":int(parameters["payee_account"]),"debits":payee_debit}

		try:
			# payer updates
			debits=payer_parameters.pop("debits",None)
			query = { "account_number": int(payer_parameters["account_number"])}
			new_values1 = { "$set": payer_parameters}
			self.accounts.update_one(query, new_values1)
			if debits!=None:
				self.accounts.update(query,{ "$push": { "debits": { "$each": debits} } })

			#payee updates
			debits=payee_parameters.pop("debits",None)
			query = { "account_number": int(payee_parameters["account_number"])}
			if self.accounts.find(query).count()==1:
				self.accounts.update(query,{{ "$inc": { "balance": payee_parameters["debit_amount"]}}})

				if debits!=None:
						print("second update")
						self.accounts.update(query,{ "$push": { "debits": { "$each": debits} } })
			else:
				print("account does not exist")

			return str(new_values1)
		
		except:
			return "payment error"

		# print(parameters)
		# print(payer)
		# print(payee)
		return "1"