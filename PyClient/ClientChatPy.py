import socket

def receive_message(sock):
    """
    Helper function to receive messages from the server.
    """
    msg = sock.recv(1024).decode()
    print(f"Received: {msg}")

def send_message(sock, msg):
    """
    Helper function to send messages to the server.
    """
    sock.sendall(msg.encode())

def main():
    # Define server host and port
    host = 'localhost'
    port = 4444

    # Create a TCP/IP socket
    sock = socket.socket(socket.AF_INET, socket.SOCK_STREAM)

    # Connect the socket to the server
    sock.connect((host, port))
    print(f"Connected to server {host}:{port}")

    # Start receiving messages from the server in a separate thread
    while True:
        receive_message(sock)

        # Prompt the user to enter a message to send to the server
        msg = input("Enter a message to send: ")

        # Send the message to the server
        send_message(sock, msg)

    # Close the socket when done
    sock.close()

if __name__ == '__main__':
    main()