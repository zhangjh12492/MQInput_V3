package com.wfj;

//import com.wfj.netty.Server;

/**
 * Hello world!
 *
 */
public class AppInput {
	
/*
	private static Logger log = LoggerFactory.getLogger("ServerControlHandler");

    private static Server server;
    
    public static void main(String[] args) throws IOException {
        if (args == null || args.length == 0) {
            args = new String[]{"start", "8080", "8081"};
        }
        if (args != null && args.length != 0 && (args[0].equals("stop"))) {
            int ctrlPort = Integer.parseInt(args[1]);
            Socket s = new Socket("localhost", ctrlPort);
            PrintWriter w = new PrintWriter(s.getOutputStream());
            w.println("stop");
            w.flush();
            w.close();
            s.close();
        } else if (args == null || args.length == 0 || args[0].equals("start")) {
            int serverPort = Integer.parseInt(args[1]);
            int conrtrolPort = Integer.parseInt(args[2]);
            server = new Server(serverPort,conrtrolPort);
            try {
                server.run();
            } catch (Exception e) {
                log.error(e.getMessage(), e);
                System.out.println(e.getMessage());
                server.shutdown(-1);
            }
        }
    }

*/

}
