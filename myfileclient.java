import java.io.*;
import java.net.*;
import java.util.Scanner;
/*
* @author William Vail(100941960), Christian Moreau(100934980)
*
*/

public class myfileclient {
    public static void main(String[] args) throws IOException {
        if (args.length != 2) {
            System.err.println(
                "Usage: java myFileClient <host name> <port number>");
            System.exit(1);
        }
        String hostName = args[0];
        int portNumber = Integer.parseInt(args[1]);

        try (
            Socket socket = new Socket(hostName, portNumber);
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(
                new InputStreamReader(socket.getInputStream()));
        ) {
            //Scanner user_input = new Scanner( System.in );
            System.out.println("Enter the file you want to transfer");

            BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
            String fromServer = null, fromUser = null;		//input/output variables
            //ask the user for the file name
            String FILE = "protocol.txt";
            String FILE_TO_RECEIVE = "/Users/stephenpeterkins/Projects/WillsA/Assignment1/protocol.txt";
            //String FILE_TO_RECIEVE = user_input.next();
            int FILE_SIZE = 3000;				//server needs to tell the user the size of file
            // receive file
            byte [] mybytearray  = new byte [FILE_SIZE];
            //String FILE = FILE_PATH + FILE_TO_RECEIVE;
            System.out.println(FILE_TO_RECEIVE);
            FileOutputStream fos = new FileOutputStream(FILE);
            BufferedOutputStream bos = new BufferedOutputStream(fos);
            InputStream is = socket.getInputStream();

            out.println(FILE);
            int bytesRead = is.read(mybytearray, 0, mybytearray.length);
            int current = bytesRead;
            System.out.println("This is current:" + current);
            while ((fromServer = in.readLine()) != null){       //if things exist, close sockets
                System.out.println("Server: " + fromServer);
                bytesRead = is.read(mybytearray, current, (mybytearray.length - current));
                if(bytesRead >= 0) current += bytesRead;

                if (fromServer.equals("Bye."))
                    break;

                fromUser = stdIn.readLine();
                if (fromUser != null) {
                    System.out.println("Client: " + fromUser);
                    out.println(fromUser);
                }
            }
                if (fos != null) fos.close();
                if (bos != null) bos.close();
                if (socket != null) socket.close();
        }

        catch (UnknownHostException e) {
            System.err.println("Don't know about host " + hostName);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to " +
                hostName);
            e.printStackTrace();
            System.exit(1);
        }
    }
}
