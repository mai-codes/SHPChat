
# Project 3 - Network Protocol Design - Searchable Chat Protocol (SHP)

This project represents the implementation of the Searchable Chat Protocol for CS544 Computer Networks. It is a Java client-server chat protocol built using sockets. This is a concurrent program, which is able to handle multiple clients by using the Runnable interface. Each client is executed on its own thread.


## Run Locally

Clone the project

```bash
  git clone https://github.com/mai-codes/SHPChat
```

Change directory into the project folder

```bash
  cd SHPChat
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
https://vimeo.com/833553514?share=copy



## Usage/Examples

To use the program, the user first needs to be authenticated. For simplicity sake and due to time limitations, the usernames and passwords are hardcoded inside a Hashmap in the Client. The different users and passwords that can be used in this program are:

| Username | Password  
| :-------- | :------- | 
| `Joe` | `pass1` |
| `Sally` | `pass2` |
| `Mike` | `pass3` |
| `Erica` | `pass4` |

Please note these usernames and passwords are case sensitive and anything other than these will not work.

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

[![dfa.png](https://i.postimg.cc/Fs84Pdk7/dfa.png)](https://postimg.cc/VdqpvNR8)

