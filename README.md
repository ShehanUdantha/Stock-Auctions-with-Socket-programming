# Stock-Auctions-with-Socket-programming
A stock auction is an auction through which different traders can purchase particular securities (items) by bidding on them.

The command to compile main method of this programme is 
              "javac Main.java".
To run the Main enter following command in terminal with the bidding time period.
              "java Main 60"
Then server will start up.


*********************************************************************************************************************************************************************************


Client
======
If you want to log as a Client, you must log to the terminal through the localhost port to run the client file.
               "telnet localhost 2021".
After that clients must enter there name and hit enter. 
Then Server ask for the symbol of stock item you want to bid.
clients must enter the symbol and press enter. 
Then the current price is displayed. 
Now clients must enter there price and hit enter. 
Then the bidding is started.
You can see your updating details in log.txt file.
After that bidding is over this client can continue.
Multiples clients can bid.
After over the bidding time client can not bidding.
At any time by entering 'quit' and hitting enter you can exit the programme.

*********************************************************************************************************************************************************************************

Publisher
=========
If you want to log as a publisher you must log to the terminal through the localhost port to run the publisher file
                 "telnet localhost 2022".
Then you can log to the program using
              Usernam - pub
              Password- pub
Next give the symol which has update the profit.
Next give the security code.
Then you can update the new profit.
If all are correct, You can see the output as "0".
If symobol or security code is incorrect You can see the output as "-1".

*********************************************************************************************************************************************************************************

Subscriber
==========
If you want to log as a Subscriber you must log to the terminal through the localhost port to run the publisher file
                "telnet localhost 2022".
Then you can log to the program using
              Usernam - sub
              Password- sub
After logging you have to choose 1 for profit subscribing or 2 for bid subscribing.
If you choose 1 you can see profit subscribing.
Type the keyword PRFT and give three valid symbols of companies.
              Ex:"PRFT FB AAL APPL"
If all three symobls are valid You can see the output as "0".
If it is not, You can see the output as "-1".
Then you can see the profits of subscribing symbols.
If a publisher update a profit then you can see the new profit.
If you choose 2 you can see bid subscribing.
Type the keyword BID and give three valid symbols of companies.
              Ex:"BID FB AAL APPL"
If all three symobls are valid You can see the output as "0".
If it is not, You can see the output as "-1".
Then you can see the bids of subscribing symbols.
If a client update a bid then you can see the new bid.
