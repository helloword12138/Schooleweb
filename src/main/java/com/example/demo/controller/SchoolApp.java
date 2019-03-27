package com.example.demo.controller;


import com.example.demo.handle.EnterHandler;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@RestController
public class SchoolApp {


    @Autowired
    EnterHandler enterHandler;


    @RequestMapping("/session/Log")
    public byte[] login(HttpServletRequest req) {
        String user = req.getParameter("u");
        String pass = req.getParameter("p");
        String username = "1615925260";
        String password = "xuxin";
        String admin = "admin";
        // String adpass="123";
        //  System.out.println(username+" "+password);

        if (user != null && user.equals(admin)) {
            if (pass != null && pass.equals(password)) {
                req.getSession().setAttribute("admin", user);

                return "y".getBytes();
            }
            return null;
        } else {
            if (user != null && user.equals(username)) {

                if (pass != null && pass.equals(password)) {
                    req.getSession().setAttribute("user", user);
                    return "y".getBytes();
                }
            } else {
                return "n".getBytes();
            }
        }
        return null;
    }




    @RequestMapping(value = "/servlet/EnterNumChartServlet")
    @ResponseBody
    public Object xxtest(HttpServletRequest req) {

        int length=0;
        String str;
        str=req.getParameter("num");
        length=Integer.valueOf(str);

        String[] xv = enterHandler.xxx(length);

        JSONArray ja = JSONArray.fromObject(xv);
        //   System.out.println(ja);
        JSONObject jo=new JSONObject();
        jo.put("Flow", ja);
        //  System.out.println(jo);

        return jo;
    }

    @RequestMapping(value = "/index2.html", method = RequestMethod.GET)
    public ModelAndView echarts2(){
        return new ModelAndView("index2");
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ModelAndView login1(){
        return new ModelAndView("login");
    }

    @RequestMapping(value = "/index.html", method = RequestMethod.GET)
    public ModelAndView login2(){
        return new ModelAndView("index");
    }


}
