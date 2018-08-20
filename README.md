# Modulr-AtmService
Modulr ATM Service Test

The jar provides the implementation for below interface 
AccountService :    1) Check balance  
                    2) Withdraw an amount 

AccountServiceImpl that is aware of the following accounts and balances  
-  Account number 01001, Balance 2738.59  
-  Account number 01002, Balance 23.00  
-  Account number 01003, Balance 0.00  
             
ATMServiceImpl and set it up to use the AccountServiceImpl. This have the following behaviour;
  -  Replenish:   
            Sets up the service with currency notes of denominations 5, 10, 20 and 50  
  -  Check balance:   
            Returns a formatted string to display 
  -  Withdraw:   
            Returns notes of the correct denominations  
            Allow withdrawals between 20 and 250 inclusive, in multiples of 5 
            Disburse smallest number of notes  
            Always disburse at least one 5 note, if possible 
             
Assumptions made

Currency : GBP
No Limit for ATM notes capacity
No Thread safety is implemented, intended for single access

