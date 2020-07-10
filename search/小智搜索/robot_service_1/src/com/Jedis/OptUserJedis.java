package com.Jedis;

import com.Db.dao.UserDao;
import com.MyHttpDecoder.MyHttpPostDecoder;
import com.bulk.ElasticSearchOpt;
import com.handler.MyEmail;
import io.netty.handler.codec.http.FullHttpRequest;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.apache.log4j.Logger;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

import static com.handler.MyEmail.EMAIL_TO;

/*
* 用于处理注册,使用redis的第0个数据库
* */



/**
 * redis库的使用:第0个库是用户验证登录缓存
 * 第1个库是用户在线状态缓存
 * */
public class OptUserJedis {
    private UserDao userDao;
    private ElasticSearchOpt elopt;
    private static final Logger logger = org.apache.log4j.Logger.getLogger(OptUserJedis.class);
    private ElasticSearchOpt elOpt = new ElasticSearchOpt();
    private MyEmail myEmail;
    private String response_json;
    private static String USER_PASS = "pass";
    private static String USER_EMAIL = "email";
    private static String ALTER_EMAIL = "alterEmail";

    public static GenericObjectPoolConfig poolConfig = new GenericObjectPoolConfig();

    public static JedisPool jedisPool = new JedisPool(poolConfig, "127.0.0.1", 6379);

    public OptUserJedis() {
        this.elopt = new ElasticSearchOpt();
        logger.info("开始进行用户操作");
    }

    public void setUserDao(UserDao user) {
        this.userDao = user;
        saveMsg();
    }


    private void saveMsg() { //再redis里缓存
        Jedis jedis = jedisPool.getResource();
        logger.info("正在将用户" + userDao.toString() + "写入redis...");
        jedis.lpush(userDao.getEmail(), userDao.getPass(), "off");
        logger.info("写入成功");
//        jedis.expire(userDao.getEmail(),10);
//        logger.info("设置用户 "+ userDao.getEmail()+" 的失效时间10s");
        jedis.close();
    }

    public boolean checkUser(String email) {
        Jedis jedis = jedisPool.getResource();
        //从邮箱点击超链接来进行检测是否再redis中,若有则 写入elasticsearch,没有返回失效状态
        logger.info("第二次验证,正在检测redis里是否存在email为" + email + "的用户...");
        jedis.lpop(email);
        jedis.lpushx(email, "on");
        ArrayList<String> userlist = (ArrayList<String>) jedis.lrange(email, 0, -1);
        System.out.println(userlist);
        if (userlist.size() == 0) {
            logger.info("该用户的email不存在或以失效");
            return false;
        }
        UserDao userDao = new UserDao();
        userDao.setEmail(email);
        userDao.setPass(userlist.get(1));
        String status = elopt.addUser(userDao);
        if (status.equals("1")) {
            return false;
        }
        logger.info("该用户" + userDao.toString() + "存在,已经写入elasticsearch");
        jedis.close();
        return true;
    }

    /*
     * 登录时先查看缓存,没有的话再去查找数据库
     * */
    public String loginUser(FullHttpRequest request1) {
        Jedis jedis = jedisPool.getResource();
        logger.info("判断为登录请求,url  :" + request1.uri());
        MyHttpPostDecoder myDecoder = new MyHttpPostDecoder(request1);
        HashMap<String, Object> user_msg = myDecoder.httpPostDecode();
        UserDao user = new UserDao(user_msg);
        logger.info("登录请求,正在查找缓存...\t用户信息: " + user.toString());

        /*先查看该用户是否已经登录*/
        /*注释的代码是用来防止一个账户多台设备登录,还有下面*/
//        String flag = jedis.select(1);
//        String status = jedis.get(user.getEmail());
//        System.out.println(flag+"   "+status);
//
//        if(status != null && status.equals("on")){
//            return "{\"status\":\"用户已存在！不允许多台登录\"}";
//        }
//
//        jedis.select(0);

        List userlist = jedis.lrange(user.getEmail(), 0, -1);
        UserDao userDao = null;
        System.out.println(userlist.size());
        if (user.getEmail() == null || user.getPass() == null) {
            logger.info("用户信息部分为空");
            return "{\"status\":\"用户部分信息为空\"}";
        }
        if (userlist.size() != 0) {
            if (userlist.get(0).equals("on")) {
                logger.info("缓存中存在该用户: " + userlist);
                if (user.getPass().equals(userlist.get(1))) {
                    return "{\"status\":\"ok\"}";
                }
            } else {
                logger.info("缓存中有用户,但没有注册");
                return "{\"status\":\"用户还没进行邮箱验证！\"}";
            }
        }
        logger.info("缓存中不存在,开始查找elasticsearch...");

        userDao = elopt.searchAccount(user);
        if (userDao == null) {
            logger.info("用户: " + user.getEmail() + "不存在");
            return "{\"status\":\"该用户不存在\"}";
        } else {
            if (!user.getPass().equals(userDao.getPass())) {
                logger.info("用户存在,但密码不正确");
                return "{\"status\":\"密码不正确，请重新输入密码\"}";
            }
        }
        logger.info("用户存在 " + userDao.toString() + " ,且密码正确\t登录请求完成");
        jedis.lpush(userDao.getEmail(), userDao.getPass(), "on");

        /*
         *设置只能同时有一个用户在线
         * */
//        jedis.select(1);
//        jedis.set(userDao.getEmail(),"on");
//        jedis.select(0);
        jedis.close();
        return "{\"status\":\"ok\"}";
    }

    public boolean deleteUserByEmail(String email) {
        Jedis jedis = jedisPool.getResource();
        UserDao userDao = new UserDao();
        userDao.setEmail(email);
        try {
            if (jedis.exists(email)) {
                //说明缓存存在
                jedis.ltrim(email, 1, 0);
                elopt.deleteUser(userDao);
            } else {
                //缓存里没有,直接删elasticsearch
                elopt.deleteUser(userDao);
            }
        } catch (Exception e) {
            logger.error("删除用户为: " + email + "出现错误", e);
            return false;
        } finally {
            jedis.close();
        }
        logger.info("删除用户: " + email);
        return true;
    }

    /*
     * 先根据用户邮箱发送验证码
     * 将邮箱与验证码存入redis
     * 再次调用的时候验证用户所传入的验证码
     * 在更新ela
     *
     * */

    public String applyToAlterMail(FullHttpRequest request1) {
        logger.info("用户获取修改密码的验证码");
        //开始进行用户验证,进行http解码获取user信息,并发送邮件进行验证
        MyHttpPostDecoder myHttpPostDecoder = new MyHttpPostDecoder(request1);
        HashMap<String, Object> paraMap = myHttpPostDecoder.httpPostDecode();
        logger.info("获得参数:  " + paraMap);
        EMAIL_TO = (String) paraMap.get(ALTER_EMAIL);
        logger.info("将要的注册邮箱： " + ALTER_EMAIL);
        //redis缓存用户注册信息数据,并设置k失效
        UserDao user = new UserDao(paraMap);
        if (elOpt.isAccountExists(user)) {
            response_json = "{\"status\":\"该用户已存在,不能重复注册！\"}";
        } else {
            OptUserJedis optUser = new OptUserJedis();
            optUser.setUserDao(user);
            MyEmail.SIGN = 1;
            myEmail = new MyEmail(EMAIL_TO);
            FutureTask<String> future = new FutureTask(myEmail);
            Thread sendMsgThread = new Thread(future);
            sendMsgThread.start();
            try {
                response_json = future.get();
            } catch (InterruptedException e) {
                logger.error("Method: register", e);
                response_json = "{\"status\":\"fail\"}";
            } catch (ExecutionException e) {
                logger.error("Method: register", e);
                response_json = "{\"status\":\"fail\"}";
            }
        }
        logger.info("response_json:\t" + response_json + "\t第一次验证完成");
        return response_json;
    }


    /*
     * 二次验证修改密码
     * */
    public String upDateUser(FullHttpRequest request1) {
        MyHttpPostDecoder myHttpPostDecoder = new MyHttpPostDecoder(request1);
        HashMap<String, Object> paraMap = myHttpPostDecoder.httpPostDecode();
        logger.info("获得参数:  " + paraMap);
        UserDao userDao = new UserDao(paraMap);


        return null;
    }


    public String registerUser(FullHttpRequest request1) {
        logger.info("开始进行用户注册: " + request1.uri());
        //开始进行注册,进行http解码获取user信息,并发送邮件进行验证
        MyHttpPostDecoder myHttpPostDecoder = new MyHttpPostDecoder(request1);
        HashMap<String, Object> paraMap = myHttpPostDecoder.httpPostDecode();
        logger.info("获得参数:  " + paraMap);
        EMAIL_TO = (String) paraMap.get(USER_EMAIL);
        logger.info("将要的注册邮箱： " + EMAIL_TO);
        //redis缓存用户注册信息数据,并设置k失效
        UserDao user = new UserDao(paraMap);
        if (elOpt.isAccountExists(user)) {
            response_json = "{\"status\":\"该用户已存在,不能重复注册！\"}";
        } else {
            OptUserJedis optUser = new OptUserJedis();
            optUser.setUserDao(user);
            myEmail = new MyEmail(EMAIL_TO);
            FutureTask<String> future = new FutureTask(myEmail);
            Thread sendMsgThread = new Thread(future);
            sendMsgThread.start();
            response_json = "{\"status\":\"ok\"}";
            try {
                response_json = future.get();
            } catch (InterruptedException e) {
                logger.error("Method: register", e);
                response_json = "{\"status\":\"fail\"}";
            } catch (ExecutionException e) {
                logger.error("Method: register", e);
                response_json = "{\"status\":\"fail\"}";
            }
        }
        logger.info("response_json:\t" + response_json + "\t第一次验证完成");
        return response_json;
    }

    /*用户登出*/
    public void logOut(UserDao user) {
        Jedis jedis = jedisPool.getResource();
        jedis.select(1);
        jedis.del(user.getEmail());
        jedis.select(0);
        jedis.close();
    }

    public static String getMailCode() {

        /*
         * 返回长度为【strLength】的随机数，在前面补0
         */

        int strLength = 6;

        Random rm = new Random();

        // 获得随机数
        double pross = (1 + rm.nextDouble()) * Math.pow(10, strLength);

        // 将获得的获得随机数转化为字符串
        String fixLenthString = String.valueOf(pross);
        String mailCode = fixLenthString.substring(1, strLength + 1);
        Jedis jedis = jedisPool.getResource();
        jedis.select(2);
        jedis.set(ALTER_EMAIL, mailCode);
        jedis.close();
        // 返回固定的长度的随机数
        return mailCode;
    }

    public static void main(String[] args) {

    }
}
