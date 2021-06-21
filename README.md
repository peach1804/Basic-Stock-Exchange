# Basic-Stock-Exchange

Project requirements:
- Must contain dynamic data structures (e.g. doubly linked list or a binary tree)
- Must contain hashing techniques
- Must contain sorting algorithm
- Must contain searching technique
- Must contain 3rd party library
- Must have a GUI

This application will simulate a basic Server/Client stock exchange using sockets. The server will store a list of stocks in a csv file, which will be loaded into a binary tree when the server program starts. When the server is running, stocks can be added or deleted from the list. The list will be saved to the csv file when the program closes.

The server will also store user data in csv files. There will be a file containing login details for existing clients and separate files for each client’s portfolio. When the server starts running, the user details will be loaded to a list of User objects.

The client will need to create an account or login before they are able to trade stocks. When a client creates an account the server will create a new User object with a username, hashed password and a salt. When the client logs in the server will search the list of users and check if the password is correct.

The Inventory class will contain a Stock property and an integer property representing the quantity of that stock held. Each client will have a list called _portfolio_ which contains Inventory objects.

When a client buys or sells a stock a Binary Search will be used to check if they already hold that stock in their portfolios. After the trade their inventory of that stock will be updated. Each client’s portfolio will be sorted using Merge Sort each time a new Inventory object is added.

***Important*** This project requires the third party library openCSV!

