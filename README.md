
![Alt text](https://github.com/phantom820/Easy-Banking/blob/master/screenshots/login.png)
![Alt text](https://github.com/phantom820/Easy-Banking/blob/master/screenshots/registration.png)
![Alt text](https://github.com/phantom820/Easy-Banking/blob/master/screenshots/homescreen.png)
![Alt text](https://github.com/phantom820/Easy-Banking/blob/master/screenshots/payment.png)
![Alt text](https://github.com/phantom820/Easy-Banking/blob/master/screenshots/manage_accounts.png)
![Alt text](https://github.com/phantom820/Easy-Banking/blob/master/screenshots/Transfer.png)
![Alt text](https://github.com/phantom820/Easy-Banking/blob/master/screenshots/transaction2.png)
# Easy-Banking

A dummy mobile banking application that aims to provide users with an easy,reliable and convenient banking experience. Users open their preferred account type 
upon registration and synthetic transactions are generated along with other user account details. The main idea is to digitize the entire banking experience although
the banking has shifted towards digitization some key aspects have remained unchanged such as the opening/closing of accounts, filing of notice for 
notice deposit accounts. The listed aspects still require individuals to physically go to a bank which is not ideal.

### Working features
- Opening accounts
- Payment (intra bank payments)
- Transfers (intra account transfers)
- Viewing transactions 

### Features to be implemented
- Closing accounts
- Filing notice for notice deposit accoounts
- Vault (Security measure to freeze all accounts and cards)
- Cashsend 
- Account migration (i.e change an account from savings to cheque)
- Card management (stop cards, change active card and so on)

## Implementation details
### Frontend
The MVP architecture as described in ![make-you-hand-dirty-with-mvp-model-view-presenter](https://medium.com/cr8resume/make-you-hand-dirty-with-mvp-model-view-presenter-eab5b5c16e42) was closely followed in writing the source code.
- AndroidStudio (java)

### Backend
- Flask with MongoDB (retrofit used in app to make http request/api calls)

## Running
### Pre requisites
- python3
- pip

#### AVD
##### Running server and app
- cd api
- pip install -r requirements.txt
- Run the applicationpy script using python3
- Run app on AVD

#### Physical device
#### Running server and app
- cd api
- pip install -r requirements.txt
- Change host address in application.py script to your ip address and then run the script
- Modify base url by changing the default ip address to your ip address in RetrofitClientInstance.java found in app folder networks directory
- Run app on device 
