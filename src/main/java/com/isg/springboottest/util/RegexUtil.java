package com.isg.springboottest.util;

import java.util.regex.Pattern;

public  class RegexUtil {
    public static void main(String[] args) {
        /**  正例 */
          final String intege = "^-?[1-9]\\d*$/";          //整数
        System.out.println(Pattern.matches(intege,"123")); // true
        System.out.println(Pattern.matches(intege,"-123")); // true

        /**   反例 */
        System.out.println(Pattern.matches(intege,"abc")); // false
        System.out.println(Pattern.matches(intege,"0")); // false

          final String intege2 = "^-[1-9]\\d*$/";          //负整数


        // 正例
        System.out.println(Pattern.matches(intege2,"-123")); // true

        // 反例
        System.out.println(Pattern.matches(intege2,"123")); // false
        System.out.println(Pattern.matches(intege2,"0")); // false


        final String num = "^([+-]?)\\d*\\.?\\d+$/";      //数字

        // 正例
        System.out.println(Pattern.matches(num,"123")); // true
        System.out.println(Pattern.matches(num,"0")); // true

        // 反例
        System.out.println(Pattern.matches(num,"a123")); // false

        final String num1 = "^[1-9]\\d*|0$/";          //正数（正整数 + 0）

        // 正例
        System.out.println(Pattern.matches(num1,"123")); // true
        System.out.println(Pattern.matches(num1,"0")); // true

        // 反例
        System.out.println(Pattern.matches(num1,"-123")); // false


        final String num2 = "^-[1-9]\\d*|0$/";          //负数（负整数 + 0）

        // 正例
        System.out.println(Pattern.matches(num2,"-123")); // true
        System.out.println(Pattern.matches(num2,"0")); // true

        // 反例
        System.out.println(Pattern.matches(num2,"123")); // false



        final String decmal1 = "^[1-9]\\d*.\\d*|0.\\d*[1-9]\\d*$"; //正浮点数

        // 正例
        System.out.println(Pattern.matches(decmal1,"0.1")); // true

        // 反例
        System.out.println(Pattern.matches(decmal1,"-0.1")); // false

        final String decmal2 = "^-([1-9]\\d*.\\d*|0.\\d*[1-9]\\d*)$"; //负浮点数

        // 正例
        System.out.println(Pattern.matches(decmal2,"-0.1")); // true

        // 反例
        System.out.println(Pattern.matches(decmal2,"0.1")); // false
        final String decmal3 = "^-?([1-9]\\d*.\\d*|0.\\d*[1-9]\\d*|0?.0+|0)$";//浮点数

        // 正例
        System.out.println(Pattern.matches(decmal3,"-0.1")); // true
        System.out.println(Pattern.matches(decmal3,"0.1")); // true

        // 反例
        System.out.println(Pattern.matches(decmal3,"a.b")); // false


        final String decmal4 = "^[1-9]\\d*.\\d*|0.\\d*[1-9]\\d*|0?.0+|0$";  //非负浮点数（正浮点数 + 0）

        // 正例
        System.out.println(Pattern.matches(decmal4,"0.1")); // true

        // 反例
        System.out.println(Pattern.matches(decmal4,"-0.1")); // false


        final String decmal5 = "^(-([1-9]\\d*.\\d*|0.\\d*[1-9]\\d*))|0?.0+|0$"; //非正浮点数（负浮点数 + 0）

        // 正例
        System.out.println(Pattern.matches(decmal5,"-0.1")); // true

        // 反例
        System.out.println(Pattern.matches(decmal5,"0.1")); // false



        final String email = "^\\w+((-\\w+)|(\\.\\w+))*\\@[A-Za-z0-9]+((\\.|-)[A-Za-z0-9]+)*\\.[A-Za-z0-9]+$"; //邮件

        // 正例
        System.out.println(Pattern.matches(email,"tom@gupaoedu.com")); // true

        // 反例
        System.out.println(Pattern.matches(email,"tom.gupaoedu.com")); // fals


        final String color = "^[a-fA-F0-9]{6}$";        //颜色

        // 正例
        System.out.println(Pattern.matches(color,"ffffff")); // true
        System.out.println(Pattern.matches(color,"FFFFFF")); // true

        // 反例
        System.out.println(Pattern.matches(color,"#FFFFFF")); // false
        System.out.println(Pattern.matches(color,"white")); // false

        final String url = "^http[s]?:\\/\\/([\\w-]+\\.)+[\\w-]+([\\w-.\\/?%&=]*)?$";  //url

        // 正例
        System.out.println(Pattern.matches(url,"http://www.xxx.com")); // true
        System.out.println(Pattern.matches(url,"https://www.xxx.com")); // true
        System.out.println(Pattern.matches(url,"www.xxx.com")); // true

        // 反例
        System.out.println(Pattern.matches(url,"abcd")); // false

        final String chinese = "^[\\u4E00-\\u9FA5\\uF900-\\uFA2D]+$";          //仅中文

        // 正例
        System.out.println(Pattern.matches(chinese,"汤姆弹架构")); // true

        // 反例
        System.out.println(Pattern.matches(chinese,"Tom弹架构")); // false

        final String ascii = "^[\\x00-\\xFF]+$";        //仅ACSII字符

        // 正例
        System.out.println(Pattern.matches(ascii,"abc123")); // true

        // 反例
        System.out.println(Pattern.matches(ascii,"にそ①②③")); // false

        final String zipcode = "^\\d{6}$";            //邮编

        // 正例
        System.out.println(Pattern.matches(zipcode,"100000")); // true

        // 反例
        System.out.println(Pattern.matches(zipcode,"1000000")); // false
        final String mobile = "^(13|15|16|17|18)[0-9]{9}$";        //手机

        // 正例
        System.out.println(Pattern.matches(zipcode,"13800138000")); // true

        // 反例
        System.out.println(Pattern.matches(zipcode,"19900010002")); // false


        final String ip4 = "^(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)\\.(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)\\.(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)\\.(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)$";  //ip地址

        // 正例
        System.out.println(Pattern.matches(zipcode,"127.0.0.1")); // true

        // 反例
        System.out.println(Pattern.matches(zipcode,"aa.bb.cc.dd")); // false
        final String notempty = "^\\S+$";            //非空

        // 正例
        System.out.println(Pattern.matches(notempty,"  abc ")); // true

        // 反例
        System.out.println(Pattern.matches(notempty,"")); // false
        final String picture = "(.*)\\.(jpg|bmp|gif|ico|pcx|jpeg|tif|png|raw|tga|JPG|BMP|GIF|ICO|PCX|JPEG|TIF|PNG|RAW|TGA)$";  //图片

        // 正例
        System.out.println(Pattern.matches(picture,"tom.jpg")); // true

        // 反例
        System.out.println(Pattern.matches(picture,"tom.txt")); // false

        final String audio = "(.*)\\.(mp3|wma|mid|midi|wav|vqf|MP3|WMA|MID|MIDI|WAV|VQF)$";  //音频

        // 正例
        System.out.println(Pattern.matches(audio,"tom.mp3")); // true

        // 反例
        System.out.println(Pattern.matches(audio,"tom.txt")); // false
        final String rar = "(.*)\\.(rar|zip|7zip|tgz|RAR|ZIP|7ZIP|TGZ)$";  //压缩文件

        // 正例
        System.out.println(Pattern.matches(rar,"tom.zip")); // true

        // 反例
        System.out.println(Pattern.matches(rar,"tom.txt")); // false


        final String date = "^\\d{4}(\\-|\\/|\\.)\\d{1,2}\\1\\d{1,2}$";  //日期

        // 正例
        System.out.println(Pattern.matches(date,"2024-10-24")); // true
        System.out.println(Pattern.matches(date,"2024/10/24")); // true

        // 反例
        System.out.println(Pattern.matches(date,"2024年10月24日")); // false


        final String datetime = "^\\d{4}(\\-|\\/|\\.)\\d{1,2}\\1\\d{1,2}(\\s\\d{2}:)?(\\d{2}:)?(\\d{2})?$";  //日期和时间

        // 正例
        System.out.println(Pattern.matches(datetime,"2024-10-24 23:59:59")); // true
        System.out.println(Pattern.matches(datetime,"2024/10/24 23:59:59")); // true

        // 反例
        System.out.println(Pattern.matches(datetime,"2024年10月24日 23时59分59秒")); // false


        final String qq = "^[1-9]*[1-9][0-9]*$";    //QQ号码

        // 正例
        System.out.println(Pattern.matches(qq,"123456")); // true

        // 反例
        System.out.println(Pattern.matches(qq,"1234567890")); // false

        final String tel = "^(([0\\+]\\d{2,3}-)?(0\\d{2,3})-)?(\\d{7,8})(-(\\d{3,}))?$";  //电话号码的函数(包括验证国内区号,国际区号,分机号)

        // 正例
        System.out.println(Pattern.matches(tel,"010-1234567")); // true
        System.out.println(Pattern.matches(tel,"0100-12345678")); // true

        // 反例
        System.out.println(Pattern.matches(tel,"13800138000")); // false


        final String username = "^[A-Za-z]\\w{5,}$";  //用来用户注册。匹配由数字、26个英文字母或者下划线组成的字符串

        // 正例
        System.out.println(Pattern.matches(username,"gupaoedutom")); // true

        // 反例
        System.out.println(Pattern.matches(username,"tom@gupaoedu")); // false


        final String allstring = "^\\w+$"; //字母数字组合

        // 正例
        System.out.println(Pattern.matches(allstring,"abc123")); // true

        // 反例
        System.out.println(Pattern.matches(allstring,"abc123%^&")); // false

        final String letter_u = "^[A-Z]+$";          //大写字母

        // 正例
        System.out.println(Pattern.matches(letter_u,"ABC")); // true

        // 反例
        System.out.println(Pattern.matches(letter_u,"abc")); // false

        final String letter_l = "^[a-z]+$";          //小写字母

        // 正例
        System.out.println(Pattern.matches(letter_l,"abc")); // true

        // 反例
        System.out.println(Pattern.matches(letter_l,"ABC")); // false
        final String idcard = "^[1-9]([0-9]{14}|[0-9]{17})$";  //身份证

        // 正例
        System.out.println(Pattern.matches(idcard,"100000201410241024")); // true

        // 反例
        System.out.println(Pattern.matches(idcard,"1000002014102410240")); // false

        final String numOrStr = "^[A-Za-z0-9]+$";//数字或字母

        // 正例
        System.out.println(Pattern.matches(numOrStr,"abc123")); // true
        System.out.println(Pattern.matches(numOrStr,"abc")); // true
        System.out.println(Pattern.matches(numOrStr,"123")); // true

        // 反例
        System.out.println(Pattern.matches(numOrStr,"Tom弹架构")); // false
    }
}
