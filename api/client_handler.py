import pymongo
from bson.json_util import dumps
from dotenv import load_dotenv
import data_models
import os
import synthetic_data

#load connection string
load_dotenv()

class ClientHandler:
	def handle_request(self,request):
		self.client=pymongo.MongoClient(os.getenv("MONGO_URL"))
		self.database=self.client["Easy-Banking"]
		self.clients=self.database["Clients"]
		self.accounts=self.database["Accounts"]
		
		if request.method=="GET":
			identity_number=request.args.get("identity_number")
			password=request.args.get("password")
			parameters={}
			parameters["identity_number"]=identity_number
			parameters["password"]=password
			
			return self.get_data(parameters)

		elif request.method=="POST":
			return self.post_data(request.get_json())
		
		elif request.method=="PATCH":
			return self.patch_data(request.get_json())
	
	#get a client using their id
	def get_data(self,parameters):
		if parameters!=None:
			try:
				query={"identity_number":parameters["identity_number"],"password":parameters["password"]}
				doc=self.clients.find(query)
				return dumps(doc)

			except:
				return "Internal server error"
		else:
			return "Nothing in parameters"

	#post create a new client 
	def post_data(self,parameters):
		if parameters!=None:
			try:
					query = { "identity_number":parameters["identity_number"]}
					doc=self.clients.find(query)
					if doc.count()==0:
							# create an account first
							account_number=9283400+self.accounts.count()
							debits,balance=synthetic_data.synthetic_transactions()
							account=data_models.Account(account_number,parameters["type"],balance,True,parameters["identity_number"],debits)
							account=account.to_map()
							self.accounts.insert_one(account)
							
							# create the client with account above
							client=data_models.Client(parameters["identity_number"],parameters["name"],parameters["surname"],parameters["email"],parameters["password"],account_number)
							client=client.to_map()
							self.clients.insert_one(client)
							
							return str(client)
					
					# must implement proper error handling
					else:
							return "Client creation error"

			except:
						return "500 Internal Server Error"
	
	#update an existing client
	def patch_data(self,parameters):
		if parameters!=None:
			try:
					query = { "identity_number": parameters["identity_number"]}
					new_values = { "$set": parameters}
					self.clients.update_one(query, new_values)
					return str(new_values)
					
			except:
					return "Client update error"