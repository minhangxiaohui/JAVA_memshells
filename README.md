# JAVA_memshells
java 内存马系列 实现（Servlets 、组件、Agent）

# java 内存马实现落地
## 1、基于动态添加Servlets组件(Listener、Servlet、Filter)
addfilter.jsp

addHttpServlet.jsp

addServlet.jsp

addListerner.jsp
## 2、基于框架组件，Spring的Controller

## 3、基于javaagent技术运行时加载，instrumentation的redefinessClass和retransformClass来实现对类的重加载或重构
inst.redefinessClass 
inst.retransformClass
### 使用方法
1、Tomcat的根目录中上传：Agent-1.0-SNAPSHOT-jar-with-dependencies.jar、Attach-1.0-SNAPSHOT-jar-with-dependencies.jar

2、java -jar Attach-1.0-SNAPSHOT-jar-with-dependencies.jar

3、curl http://tomcat:port/anypath?passwod=ga0weI&cmd={xx}
