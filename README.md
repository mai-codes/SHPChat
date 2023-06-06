
# Project 3 - Network Protocol Design - Searchable Chat Protocol (SHP)

This project represents the implementation of the Searchable Chat Protocol for CS544 Computer Networks. It is a Java client-server chat protocol built using sockets. This is a concurrent program, which is able to handle multiple clients by using the Runnable interface. Each client is executed on its own thread.


## Run Locally

Clone the project

```bash
  git clone https://github.com/mai-codes/SHPChat
```

Go to the project directory

```bash
  cd my-project
```

Start the server by running the following command:

```bash
  ./server.sh 5055
```

Start the client by running the following command:

```bash
  ./client.sh localhost 5055
```
If there are issues with permissions, change the permissions of the bash scripts as follows:

```bash
  chmod u+x client.sh 
  chmod u+x server.sh
```

## Documentation

The file structure of the project is as follows:
```
.
├── Server.java                # Server class
├── Client.java                # Client class
├── ClientHandler.java         # ClientHandler class
├── ExecutorWrapper.java       # Wrapper class for ExecutorService
├── client.sh                  # Bash script to run client
├── server.sh                  # Bash script to run server
└── README.md
```
The Server.java contains the code for the server class. It supports muliple Client threads and utilizes the ExecutorService library in Java. 

Client.java contains the code for the client.  

ClientHandler.java lays the groundwork for communicating between all clients on the port. It implements the Runnable interface, for supporting different threads.

ExecutorServiceWrapper.java is a wrapper class for the ExecutorService library. This library allows the creation of a thread pool of up to 4 threads. 

## Demo

Link to demo:




## Usage/Examples

Authentication Example:
```bash
Welcome to SHP Chat! Please login by typing your username and password in this format <AUTH username password>:
AUTH Joe pass1
AUTH-RESPONSE: 1
```


## Message PDUs

The control messages implemented in this project so far are the authentication and history messages. These are outlined below:

#### Authentication

```http
  AUTH Joe pass1
  AUTH-RESPONSE 1
```

| Control Message | Response  
| :-------- | :------- | 
| `AUTH <username> <password>` | **Success**: AUTH-RESPONSE 1 |
|  | **Fail**: AUTH-RESPONSE 0  | 


#### Get History

```http
  GET-HISTORY
  HISTORY-RESPONSE 1 <history>
```

| Control Message | Response  
| :-------- | :------- | 
| `GET-HISTORY` | **Success**: `HISTORY-RESPONSE 1 <history>`|
|  | **Fail**: `HISTORY-RESPONSE 0`  | 

#### Data messages

The other type of message is a data message. This is any text sent between the clients.
## DFA

![DFA Screenshot](<img width="757" alt="Screen Shot 2023-05-15 at 1 34 30 AM" src="https://github.com/mai-codes/SHPChat/assets/9252452/74a22c77-4c6e-4479-b8dc-83ca5db9051e">)

