import json
import pymongo
import hashlib

#modeling client
class Client:
	def __init__(self,identity_number,name,surname,email,password,account_number):
		self.identity_number=identity_number
		self.name=name
		self.surname=surname
		self.email=email
		self.password=password
		self.accounts=[account_number]

	def __str__(self):
		json=str({"identity_number":self.identity_number,"name":self.name,"surname":self.surname,"email":self.email,"accounts":self.accounts})
		return json
	
	def to_map(self):
		json={"identity_number":self.identity_number,"name":self.name,"surname":self.surname,"email":self.email,
		"password":self.password,"accounts":self.accounts}
		return json

#modeling an account
class Account:
	def __init__(self,account_number,type,balance,active,identity_number,debits):
		self.account_number=account_number
		self.type=type
		self.balance=balance
		self.active=active
		self.identity_number=identity_number
		self.debits=debits

	def __str__(self):
		json=str({"account_number":self.account_number,"type":self.type,"balance":self.balance,"active":self.active,"identiy_number":self.identity_number,"debits":self.debits})
		return json
	
	def to_map(self):
		json={"account_number":self.account_number,"type":self.type,"balance":self.balance,"active":self.active,"identity_number":self.identity_number,"debits":self.debits}
		return json

class Debit:
	def __init__(self,date,debit_amount,balance,description):
		self.date=date
		self.debit_amount=debit_amount
		self.balance=balance
		self.description=description
	
	def __str__(self):
		json={"date":self.date,"debit_amount":self.debit_amount,"balance":self.balance,"description":self.description}
		return str(json)

	def to_map(self):
		json={"date":self.date,"debit_amount":self.debit_amount,"balance":self.balance,"description":self.description}
		return json


