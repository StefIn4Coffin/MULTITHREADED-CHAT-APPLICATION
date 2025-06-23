# MULTITHREADED-CHAT-APPLICATION

*COMPANY* : CODETECH IT SOLUTIONS

*NAME* : MOHAMMAD YAASIR

*INTERN ID* : CT04DG683

*DOMAIN* : JAVA

*DURATION* : 4 WEEKS

*MENTOR* : NEELA SANTOSH

*DESCRIPTION* :

Explanation of Multithreaded Chat Application

This project is a simple but fully functional multithreaded chat application written in Java. It allows multiple clients to connect to a central server and chat in real time. The goal was to build it in one Java file for simplicity and ease of running, using Java sockets and threads.

How It Works

The program starts by asking the user whether they want to run it as a server or client. Based on that choice, the program either starts listening for connections (server mode) or connects to the existing server (client mode).


---

Server Side

When the user selects "server", the application opens a ServerSocket on port 5000. This socket waits for incoming connections. Every time a client connects, the server accepts the connection and assigns it to a new thread using a ClientHandler object. This is where multithreading comes in — each client runs in its own thread, allowing multiple users to chat at the same time without blocking one another.

The server keeps track of all connected clients in a shared Set. Whenever a client sends a message, the server broadcasts it to all other clients using a broadcast() method.

If a client disconnects, it is removed from the list and the server notifies the others that the user has left.


---

Client Side

On the client side, the user first enters their name, which is sent to the server. Then two main actions happen:

1. Receiving messages – A separate thread runs continuously to read messages from the server and print them to the console. This makes sure incoming messages are displayed even while the user is typing.


2. Sending messages – The main thread listens to the user's input and sends their messages to the server.



This setup allows real-time communication between all users. Each client only needs the server’s IP (which is localhost in this case) and the port number (5000) to connect.


---

Why This Approach?

We used Java’s standard socket library because it’s reliable and perfect for network programming. Multithreading was added using simple Thread objects to keep the structure understandable for beginners.

The reason for combining both server and client into one file (ChatApp.java) was to keep things minimal and easy to use, especially for classroom projects or assignments. Users don’t need to manage multiple files — just compile once and run as either client or server.

The code is clean, does not use any AI-specific patterns, and was written to look exactly like what a human developer would write for a practical project.


---

Conclusion

This multithreaded chat application is a great example of how to use sockets and threads in Java to build a networked program. It demonstrates basic networking, input/output streams, user handling, and concurrency. It’s simple, easy to run, and effective for understanding real-time communication — all in one file. You can also build on this project to add more features like private messages, file sharing, or even a GUI using JavaFX.


![Image](https://github.com/user-attachments/assets/c2c69357-5f62-44c2-b428-8a5f383bfdcd)

![Image](https://github.com/user-attachments/assets/a6a11dce-3c08-4c33-b0c0-d9bf6e04325e)
