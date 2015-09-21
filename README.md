# JChatServer
Server portion of JChat. I learned a lot of fundamental things in Java from this project.

## Things I learned to use after this project:
1. Sending messages using TCP
2. How Regex works (String.split())
3. Threading and how to use Threads
4. Dynamic Arrays (ArrayList<> in particular)
5. How to use JFormDesigner and JFrame's in general.

## How it works (Pretty simple)
1. Waits for clients to connect
2. Creates a new Thread for client
3. Adds Thread to ArrayList of Threads (Actually wrapped in a ClientHandler class)
4. Everytime a person sends a message/disconnects, everyone gets notified of the message to update their text area

