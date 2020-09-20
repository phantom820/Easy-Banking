import pydbgen
import numpy as np
import pandas as pd
from pydbgen import pydbgen
import json

#create a dataframe with artificial transactions
import pydbgen
import numpy as np
import pandas as pd
from pydbgen import pydbgen
import json

#create a dataframe with artificial transactions
def synthetic_transactions():
    
    month_range=pd.date_range('2020-06-24', periods=3, freq='M')
    monthly_expenditures=np.array(["Electricity","Groceries","Water","Debit order"])
    monthly_income=pd.DataFrame({"date":month_range,"debit_amount":[22243.20]*len(month_range)
                                ,"description":["Settlement"]*len(month_range)})
    monthly_expenses=pd.DataFrame({"date": month_range, "debit_amount": -1*np.round(np.random.uniform(400,1500,len(month_range)),2)
                          ,"description":monthly_expenditures[np.random.randint(0,len(monthly_expenditures),len(month_range))]})
    
    daily_expenditures=np.array(["Restaurant","Data bundles","Clothes","Entertainment","Fuel","Other"])
    day_range=pd.date_range('2020-06-24', periods=90, freq='D')
    daily_expenses=pd.DataFrame({"date": day_range[::np.random.randint(2,5)]})
    daily_expenses["description"]= daily_expenditures[np.random.randint(0,len(daily_expenditures),len(daily_expenses["date"]))]
    daily_expenses["debit_amount"]= -1*np.round(np.random.uniform(20,2000,len(daily_expenses["date"])),2)
    
    transactions=pd.concat([monthly_expenses,daily_expenses,monthly_income])
    transactions["balance"]=50000+np.cumsum(transactions["debit_amount"].values)
    transaction_json = json.loads(transactions.to_json(orient='records'))
    balance=transactions["balance"].values[-1]

    return transaction_json,np.round(balance,2)
	
