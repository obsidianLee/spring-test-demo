package com.test.demo;

import org.springframework.util.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 各种证件号码校验工具
 * @version 1.0
 * @author lxy
 */
public class ValidatorUtils {


    /**
     * 邮箱正则表达式
     */
    private static final String EMAIL_REGEX="^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    /**
     * <pre>
     * 省、直辖市代码表：
     *     11 : 北京  12 : 天津  13 : 河北       14 : 山西  15 : 内蒙古
     *     21 : 辽宁  22 : 吉林  23 : 黑龙江  31 : 上海  32 : 江苏
     *     33 : 浙江  34 : 安徽  35 : 福建       36 : 江西  37 : 山东
     *     41 : 河南  42 : 湖北  43 : 湖南       44 : 广东  45 : 广西      46 : 海南
     *     50 : 重庆  51 : 四川  52 : 贵州       53 : 云南  54 : 西藏
     *     61 : 陕西  62 : 甘肃  63 : 青海       64 : 宁夏  65 : 新疆
     *     71 : 台湾
     *     81 : 香港  82 : 澳门
     *     91 : 国外
     * </pre>
     */
    private static String[] cityCode = { "11", "12", "13", "14", "15", "21",
            "22", "23", "31", "32", "33", "34", "35", "36", "37", "41", "42",
            "43", "44", "45", "46", "50", "51", "52", "53", "54", "61", "62",
            "63", "64", "65", "71", "81", "82", "91" };

    /**
     * 每位加权因子
     */
    private static int power[] = { 7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5,
            8, 4, 2 };

    /**
     * 验证所有的身份证的合法性
     * @param idcard 身份证
     * @return 合法返回true，否则返回false
     */
    public static boolean isIdcard(String idcard) {
        if (idcard == null || "".equals(idcard)) {
            return false;
        }
        int s=15;
        if (idcard.length() == s) {
            return validate15IDCard(idcard);
        }
        int s1=18;
        if(idcard.length()==s1) {
            return validate18Idcard(idcard);
        }
        return false;

    }

    /**
     * <p>
     * 判断18位身份证的合法性
     * </p>
     * 根据〖中华人民共和国国家标准GB11643-1999〗中有关公民身份号码的规定，公民身份号码是特征组合码，由十七位数字本体码和一位数字校验码组成。
     * 排列顺序从左至右依次为：六位数字地址码，八位数字出生日期码，三位数字顺序码和一位数字校验码。
     * <p>
     * 顺序码: 表示在同一地址码所标识的区域范围内，对同年、同月、同 日出生的人编定的顺序号，顺序码的奇数分配给男性，偶数分配 给女性。
     * </p>
     * <p>
     * 1.前1、2位数字表示：所在省份的代码； 2.第3、4位数字表示：所在城市的代码； 3.第5、6位数字表示：所在区县的代码；
     * 4.第7~14位数字表示：出生年、月、日； 5.第15、16位数字表示：所在地的派出所的代码；
     * 6.第17位数字表示性别：奇数表示男性，偶数表示女性；
     * 7.第18位数字是校检码：也有的说是个人信息码，一般是随计算机的随机产生，用来检验身份证的正确性。校检码可以是0~9的数字，有时也用x表示。
     * </p>
     * <p>
     * 第十八位数字(校验码)的计算方法为： 1.将前面的身份证号码17位数分别乘以不同的系数。从第一位到第十七位的系数分别为：7 9 10 5 8 4
     * 2 1 6 3 7 9 10 5 8 4 2
     * </p>
     * <p>
     * 2.将这17位数字和系数相乘的结果相加。
     * </p>
     * <p>
     * 3.用加出来和除以11，看余数是多少
     * </p>
     * 4.余数只可能有0 1 2 3 4 5 6 7 8 9 10这11个数字。其分别对应的最后一位身份证的号码为1 0 X 9 8 7 6 5 4 3
     * 2。
     * <p>
     * 5.通过上面得知如果余数是2，就会在身份证的第18位数字上出现罗马数字的Ⅹ。如果余数是10，身份证的最后一位号码就是2。
     * </p>
     *
     * @param idcard
     * @return
     */
    public static boolean validate18Idcard(String idcard) {
        if (idcard == null) {
            return false;
        }

        // 非18位为假
        int s=18;
        if (idcard.length() != s) {
            return false;
        }
        // 获取前17位
        String idcard17 = idcard.substring(0, 17);

        // 前17位全部为数字
        if (!isDigital(idcard17)) {
            return false;
        }

        String provinceid = idcard.substring(0, 2);
        // 校验省份
        if (!checkProvinceid(provinceid)) {
            return false;
        }

        // 校验出生日期
        String birthday = idcard.substring(6, 14);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

        try {
            Date birthDate = sdf.parse(birthday);
            String tmpDate = sdf.format(birthDate);
            // 出生年月日不正确
            if (!tmpDate.equals(birthday)) {
                return false;
            }

        } catch (ParseException e1) {

            return false;
        }

        // 获取第18位
        String idcard18Code = idcard.substring(17, 18);

        char c[] = idcard17.toCharArray();

        int bit[] = converCharToInt(c);

        int sum17 = 0;

        sum17 = getPowerSum(bit);

        // 将和值与11取模得到余数进行校验码判断
        String checkCode = getCheckCodeBySum(sum17);
        if (null == checkCode) {
            return false;
        }
        // 将身份证的第18位与算出来的校码进行匹配，不相等就为假
        if (!idcard18Code.equalsIgnoreCase(checkCode)) {
            return false;
        }
        //System.out.println("正确");
        return true;
    }
    /**
     * 校验15位身份证
     *
     * <pre>
     * 只校验省份和出生年月日
     * </pre>
     *
     * @param idcard
     * @return
     */
    public static boolean validate15IDCard(String idcard) {
        if (idcard == null) {
            return false;
        }
        // 非15位为假
        int s=15;
        if (idcard.length() != s) {
            return false;
        }

        // 15全部为数字
        if (!isDigital(idcard)) {
            return false;
        }

        String provinceid = idcard.substring(0, 2);
        // 校验省份
        if (!checkProvinceid(provinceid)) {
            return false;
        }

        String birthday = idcard.substring(6, 12);

        SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd");

        try {
            Date birthDate = sdf.parse(birthday);
            String tmpDate = sdf.format(birthDate);
            // 身份证日期错误
            if (!tmpDate.equals(birthday)) {
                return false;
            }

        } catch (ParseException e1) {

            return false;
        }

        return true;
    }

    /**
     * 将15位的身份证转成18位身份证
     *
     * @param idcard
     * @return
     */
    public static String convertIdcarBy15bit(String idcard) {
        if (idcard == null) {
            return null;
        }

        // 非15位身份证
        int s=15;
        if (idcard.length() != s) {
            return null;
        }

        // 15全部为数字
        if (!isDigital(idcard)) {
            return null;
        }

        String provinceid = idcard.substring(0, 2);
        // 校验省份
        if (!checkProvinceid(provinceid)) {
            return null;
        }

        String birthday = idcard.substring(6, 12);

        SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd");

        Date birthdate = null;
        try {
            birthdate = sdf.parse(birthday);
            String tmpDate = sdf.format(birthdate);
            // 身份证日期错误
            if (!tmpDate.equals(birthday)) {
                return null;
            }

        } catch (ParseException e1) {
            return null;
        }

        Calendar cday = Calendar.getInstance();
        cday.setTime(birthdate);
        String year = String.valueOf(cday.get(Calendar.YEAR));

        String idcard17 = idcard.substring(0, 6) + year + idcard.substring(8);

        char c[] = idcard17.toCharArray();
        String checkCode = "";

        // 将字符数组转为整型数组
        int bit[] = converCharToInt(c);

        int sum17 = 0;
        sum17 = getPowerSum(bit);

        // 获取和值与11取模得到余数进行校验码
        checkCode = getCheckCodeBySum(sum17);

        // 获取不到校验位
        if (null == checkCode) {
            return null;
        }
        // 将前17位与第18位校验码拼接
        idcard17 += checkCode;
        return idcard17;
    }

    /**
     * 校验省份
     *
     * @param provinceid
     * @return 合法返回TRUE，否则返回FALSE
     */
    private static boolean checkProvinceid(String provinceid) {
        for (String id : cityCode) {
            if (id.equals(provinceid)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 数字验证
     *
     * @param str
     * @return
     */
    private static boolean isDigital(String str) {
        return str.matches("^[0-9]*$");
    }

    /**
     * 将身份证的每位和对应位的加权因子相乘之后，再得到和值
     *
     * @param bit
     * @return
     */
    private static int getPowerSum(int[] bit) {

        int sum = 0;

        if (power.length != bit.length) {
            return sum;
        }

        for (int i = 0; i < bit.length; i++) {
            for (int j = 0; j < power.length; j++) {
                if (i == j) {
                    sum = sum + bit[i] * power[j];
                }
            }
        }
        return sum;
    }

    /**
     * 将和值与11取模得到余数进行校验码判断
     *
     * @param sum17
     * @return 校验位
     */
    private static String getCheckCodeBySum(int sum17) {
        String checkCode = null;
        switch (sum17 % 11) {
            case 10:
                checkCode = "2";
                break;
            case 9:
                checkCode = "3";
                break;
            case 8:
                checkCode = "4";
                break;
            case 7:
                checkCode = "5";
                break;
            case 6:
                checkCode = "6";
                break;
            case 5:
                checkCode = "7";
                break;
            case 4:
                checkCode = "8";
                break;
            case 3:
                checkCode = "9";
                break;
            case 2:
                checkCode = "x";
                break;
            case 1:
                checkCode = "0";
                break;
            case 0:
                checkCode = "1";
                break;
            default:
        }
        return checkCode;
    }

    /**
     * 将字符数组转为整型数组
     *
     * @param c
     * @return
     * @throws NumberFormatException
     */
    private static int[] converCharToInt(char[] c) throws NumberFormatException {
        int[] a = new int[c.length];
        int k = 0;
        for (char temp : c) {
            a[k++] = Integer.parseInt(String.valueOf(temp));
        }
        return a;
    }


    /**
     * 手机号验证
     * @param  phone
     * @return 验证通过返回true
     */
    public static boolean isMobile(String phone) {
        if(StringUtils.isEmpty(phone)) {
            return false;
        }

        String regex = "^((13[0-9])|(14[5,7,9])|(15([0-3]|[5-9]))|(166)|(17[0,1,3,5,6,7,8])|(18[0-9])|(19[8|9]))\\d{8}$";
        if(phone.length() != 11) {
            return false;
        }else {
            Pattern p = Pattern.compile(regex);
            Matcher m = p.matcher(phone);
            boolean isMatch = m.matches();
            return isMatch;
        }
    }

    /**
     * 电话号码验证
     * @param phone
     * @return 验证通过返回true
     */
    public static boolean isTelPhone(String phone) {
        Pattern p1 = null, p2 = null;
        Matcher m = null;
        boolean b = false;
        p1 = Pattern.compile("^[0][0-9]{2,3}-?[0-9]{5,10}$");  // 验证带区号的
        p2 = Pattern.compile("^[1-9]{1}[0-9]{5,8}$");         // 验证没有区号的
        if (phone.length() > 9) {
            m = p1.matcher(phone);
            b = m.matches();
        } else {
            m = p2.matcher(phone);
            b = m.matches();
        }
        return b;
    }

    /**
     * 营业执照 统一社会信用代码（15位）
     * @param license
     * @return
     */
    public static boolean isLicense15(String license) {
        if(StringUtils.isEmpty(license)) {
            return false;
        }
        if(license.length() != 15) {
            return false;
        }

        String businesslicensePrex14 = license.substring(0,14);// 获取营业执照注册号前14位数字用来计算校验码
        String businesslicense15 = license.substring(14, license.length());// 获取营业执照号的校验码
        char[] chars = businesslicensePrex14.toCharArray();
        int[] ints = new int[chars.length];
        for(int i=0; i<chars.length;i++) {
            ints[i] = Integer.parseInt(String.valueOf(chars[i]));
        }
        getCheckCode(ints);
        if(businesslicense15.equals(getCheckCode(ints)+"")) {// 比较 填写的营业执照注册号的校验码和计算的校验码是否一致
            return true;
        }
        return false;
    }

    /**
     * 获取 营业执照注册号的校验码
     * @param ints
     * @return
     */
    private static int getCheckCode(int[] ints) {
        if(null != ints && ints.length > 1) {
            int ti = 0;
            int si = 0;// pi|11+ti
            int cj = 0;// （si||10==0？10：si||10）*2
            int pj = 10;// pj=cj|11==0?10:cj|11
            for (int i=0;i<ints.length;i++) {
                ti = ints[i];
                pj = (cj % 11) == 0 ? 10 : (cj % 11);
                si = pj + ti;
                cj = (0 == si % 10 ? 10 : si % 10) * 2;
                if (i == ints.length-1) {
                    pj = (cj % 11) == 0 ? 10 : (cj % 11);
                    return pj == 1 ? 1 : 11 - pj;
                }
            }
        }// end if
        return -1;
    }

    /**
     * 营业执照 统一社会信用代码（18位）
     * @param license
     * @return
     */
    public static boolean isLicense18(String license) {
        if(StringUtils.isEmpty(license)) {
            return false;
        }
        if(license.length() != 18) {
            return false;
        }

        String regex = "^([159Y]{1})([1239]{1})([0-9ABCDEFGHJKLMNPQRTUWXY]{6})([0-9ABCDEFGHJKLMNPQRTUWXY]{9})([0-90-9ABCDEFGHJKLMNPQRTUWXY])$";
        if (!license.matches(regex)) {
            return false;
        }
        String str = "0123456789ABCDEFGHJKLMNPQRTUWXY";
        int[] ws = { 1, 3, 9, 27, 19, 26, 16, 17, 20, 29, 25, 13, 8, 24, 10, 30, 28 };
        String pre17 = license.substring(0, license.length() - 1);//前17位
        String last = license.substring(license.length() - 1);//最后一位校验码
        int sum = 0;
        for (int i = 0; i < pre17.length(); i++) {
            sum += str.indexOf(pre17.charAt(i)) * ws[i];
        }
        int c18 = 31 - (sum % 31);
        if (c18 == 31) {
            c18 = 0;
        }
        if (str.charAt(c18) != last.charAt(0)) {
            return false;
        }
        return true;
    }

    /**
     * 营业执照 统一社会信用代码，不区分位数
     * @param no
     * @return
     */
    public static boolean isUniLicenseNo(String no) {
        return isLicense15(no)||isLicense18(no);
    }

    /**
     * 邮箱格式校验
     * @param email
     * @return
     */
    public static boolean isEmail(String email){
        return Pattern.matches(EMAIL_REGEX, email.trim());
    }
}

