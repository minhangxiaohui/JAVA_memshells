package priv.ga0weI.baseonagent;

import com.sun.tools.attach.VirtualMachine;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Attachthing {
    public static void main(String[] args) throws Exception {
        String pid = getpid().trim();
        System.out.println("find a Tomcat vm:"+pid);
        String currentPath = Attachthing.class.getProtectionDomain().getCodeSource().getLocation().getPath();
        currentPath = currentPath.substring(0, currentPath.lastIndexOf("/") + 1);
//        System.out.println("path:"+currentPath);
        String agentFile = currentPath.substring(1,currentPath.length())+"Agent-1.0-SNAPSHOT-jar-with-dependencies.jar".replace("/","\\");
        System.out.println("agent file path :"+agentFile);
        VirtualMachine vm = VirtualMachine.attach(pid);
        vm.loadAgent(agentFile);
        vm.detach();
        System.out.println("agent injected");
    }

    /**
     * look for pid in jvm
     * @return Pid
     * @throws Exception
     */
    private static String getpid() throws Exception{
        Process ps = Runtime.getRuntime().exec("jps");
        InputStream is = ps.getInputStream();
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader bis = new BufferedReader(isr);
        String line;
        StringBuilder sb = new StringBuilder();
        String result = null;
        while((line=bis.readLine())!=null){
            sb.append(line+";");
        }
        String  [] xx= sb.toString().split(";");
        for (String x : xx){
            if (x.contains("Bootstrap")) //find tomcat
            {
                result=x.substring(0,x.length()-9);
            }
        }
        return result;
    }

}
