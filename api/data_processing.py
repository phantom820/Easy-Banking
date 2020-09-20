import numpy as np
import pandas as pd
import json

def select_transactions(data,lower_date=None,upper_date=None):
	data=pd.DataFrame(data["debits"].values[0])
	if lower_date==None and upper_date==None:
		data["date"]=pd.to_datetime(data["date"],unit='ms')
		data=data.sort_values(by="date",ascending=False)
		return (data.to_json(orient='records'))