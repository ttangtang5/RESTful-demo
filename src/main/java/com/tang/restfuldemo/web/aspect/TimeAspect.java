package com.tang.restfuldemo.web.aspect;

import com.google.common.base.Stopwatch;
import javassist.*;
import javassist.bytecode.CodeAttribute;
import javassist.bytecode.LocalVariableAttribute;
import javassist.bytecode.MethodInfo;
import org.apache.commons.lang3.ArrayUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @Description aspect 切面, 不可以获取原始的request和response
 * @Author tang
 * @Date 2019-08-21 22:00
 * @Version 1.0
 **/
@Aspect
@Component
public class TimeAspect {

    private Logger logger = LoggerFactory.getLogger(TimeAspect.class);

    /**
     *<li>Before       : 在方法执行前进行切面</li>
     * <li>execution    : 定义切面表达式</li>
     * <p>public * com.eparty.ccp.*.impl..*.*(..)
     *      <li>public :匹配所有目标类的public方法，不写则匹配所有访问权限</li>
     *      <li>第一个* :方法返回值类型，*代表所有类型 </li>
     *      <li>第二个* :包路径的通配符</li>
     *      <li>第三个..* :表示impl这个目录下所有的类，包括子目录的类</li>
     *      <li>第四个*(..) : *表示所有任意方法名,..表示任意参数</li>
     *匹配参考：https://docs.spring.io/spring/docs/4.3.25.RELEASE/spring-framework-reference/htmlsingle/#aop-pointcuts
     */
    @Pointcut(value = "execution(public * com.tang.restfuldemo.web.controller..*.*(..))")
    public void poinCut(){}

    /**
     * 要么用around环绕  不然通过@before和@after分别写方法
     */
    @Around("poinCut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        System.out.println("进入 TimeAspect ===");
        Stopwatch stopwatch = Stopwatch.createStarted();

        /**
         * Signature 包含了方法名、申明类型以及地址等信息
         */
        String class_name = point.getTarget().getClass().getName();
        String method_name = point.getSignature().getName();
        // 获取参数名 不可以直接获取。 interceptor可直接获取。
        String[] fieldsName = getFieldsName(class_name, method_name);

        // 获取参数值
        Object[] args = point.getArgs();

        logParam(fieldsName, args);

        // 调用handler 返回值为handler的结果（controller的返回值）
        Object proceed = point.proceed();

        stopwatch.stop();
        System.out.println("TimeAspect: 耗时=" + stopwatch.toString() + " ms");

        return proceed;
    }

    /**
     * 使用javassist来获取方法参数名称 需依赖javassite
     * Javasssist 用于动态编程 更改原有class：增、删和修改class中的字段或方法；修改类的继承结构 新增class：增加字段、方法、构造方法；注意不能新增接口。 新增接口：只适用于接口 新增注解：用于创建注解
     * @param class_name    类名
     * @param method_name   方法名
     * @return
     * @throws Exception
     */
    private String[] getFieldsName(String class_name, String method_name) throws Exception {
        Class<?> clazz = Class.forName(class_name);
        String clazz_name = clazz.getName();
        ClassPool pool = ClassPool.getDefault();
        ClassClassPath classPath = new ClassClassPath(clazz);
        pool.insertClassPath(classPath);

        CtClass ctClass = pool.get(clazz_name);
        CtMethod ctMethod = ctClass.getDeclaredMethod(method_name);
        MethodInfo methodInfo = ctMethod.getMethodInfo();
        CodeAttribute codeAttribute = methodInfo.getCodeAttribute();
        LocalVariableAttribute attr = (LocalVariableAttribute) codeAttribute.getAttribute(LocalVariableAttribute.tag);
        if(attr == null){
            return null;
        }
        String[] paramsArgsName = new String[ctMethod.getParameterTypes().length];
        int pos = Modifier.isStatic(ctMethod.getModifiers()) ? 0 : 1;
        for (int i=0;i<paramsArgsName.length;i++){
            paramsArgsName[i] = attr.variableName(i + pos);
        }
        return paramsArgsName;
    }


    /**
     * 判断是否为基本类型：包括String
     * @param clazz clazz
     * @return  true：是;     false：不是
     */
    private boolean isPrimite(Class<?> clazz){
        if (clazz.isPrimitive() || clazz == String.class){
            return true;
        }else {
            return false;
        }
    }


    /**
     * 打印方法参数值  基本类型直接打印，非基本类型需要重写toString方法
     * @param paramsArgsName    方法参数名数组
     * @param paramsArgsValue   方法参数值数组
     */
    private void logParam(String[] paramsArgsName,Object[] paramsArgsValue){
        if(ArrayUtils.isEmpty(paramsArgsName) || ArrayUtils.isEmpty(paramsArgsValue)){
            logger.info("该方法没有参数");
            return;
        }
        StringBuffer buffer = new StringBuffer();
        for (int i=0;i<paramsArgsName.length;i++){
            //参数名
            String name = paramsArgsName[i];
            //参数值
            Object value = paramsArgsValue[i];
            buffer.append(name +" = ");
            if(isPrimite(value.getClass())){
                buffer.append(value + "  ,");
            }else {
                buffer.append(value.toString() + "  ,");
            }
        }
        logger.info(buffer.toString());
    }

}
