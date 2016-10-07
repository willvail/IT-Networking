import java.io.*;
import java.net.*;
/*
* @author William Vail(100941960), Christian Moreau(100934980)
*
*/
public class myfileserver {
	public static void main(String[] args) throws IOException {
        while(true){
        	int M = 0, N = 0;
	        if (args.length != 1) {
	            System.err.println("Usage: java Server <port number>");
	            System.exit(1);
	        }

	        int portNumber = Integer.parseInt(args[0]);

	        try (
	            ServerSocket serverSocket = new ServerSocket(portNumber);	//creates a socket
	            Socket clientSocket = serverSocket.accept();				//waits until a client sends a request, the accept method returns a new socket which is bound to the remote access port.
	            PrintWriter out =											//communicates with the client using this code
	                new PrintWriter(clientSocket.getOutputStream(), true);	//1. Get the sockets input/output stream and opens readers and writers on them
	            BufferedReader in = new BufferedReader(
	                new InputStreamReader(clientSocket.getInputStream()));
	        ) {
	            String inputLine, outputLine;
	            System.out.println("Did we make it fam?");
	            // Initiate conversation with client
	            myfileserver protocol = new myfileserver();					//2. Initiates communication with the client by writing to the socket
	            outputLine = protocol.processInput(null);
	            while ((inputLine = in.readLine()) != null) {				//3. Communicates with the client by reading from and writing to the socket
	                N++;
	                File myFile = new File(inputLine); 		          	  //Set filename to client determined filename

	                System.out.println("REQ " + N + ": File " + inputLine + " requested from <IP>");
	                System.out.println("REQ " + N + ": Total successful requests so far = " + M);

	                byte[] mybytearray = new byte[(int) myFile.length()];
	                BufferedInputStream bis = new BufferedInputStream(new FileInputStream(myFile));
	                bis.read(mybytearray, 0, mybytearray.length);
	                OutputStream os = new ObjectOutputStream(clientSocket.getOutputStream());
	                os.write(mybytearray, 0, mybytearray.length);
	                M++;
	                out.println("Server handled " + N + " requests, " + M + " requests were successful");
	                out.println("Download complete");
					      //Close Socket
	                bis.close();
	                System.out.println("REQ " + N + ": File transfer complete");
	                System.out.println("REQ " + N + ": [Not] Successful");
	                if (outputLine.equals("Bye."))
	                	break;
	          }
	        } catch (IOException e) {
	            System.out.println("Exception caught when trying to listen on port "
	                + portNumber + " or listening for a connection");
	            System.out.println(e.getMessage());
	        }
        }
    }
	private String processInput(Object object) {

		return "";
	}
}
