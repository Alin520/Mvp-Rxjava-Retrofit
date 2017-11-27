package com.alin.hourse.common.util;

import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;

/**
 * @创建者 hailin
 * @创建时间 2017/10/16 17:35
 * @描述 ${}.
 */

public class ClassUtil {

    /**
     * 对于getActualTypeArguments的返回类型总结：
     *
     * 一、ParameterizedType  参数化类型
     *   1）表示参数化类型，如 ArrayList<ArrayList<Integer>>等，泛型值确定
     *   2）拥有的方法
     *      1、getActualTypeArguments :获取泛型中的实际类型.无论<>中有几层嵌套(List<Map<String,Integer>)，getActualTypeArguments()
     *                            方法永远都是脱去最外层的<>(也就是List<>)，将括号内的内容（Map<String,Integer>）返回；
     *             注意：无论<>中有几层<>嵌套， 这个方法仅仅脱去最外层的<>之后剩下的内容就作为这个方法的返回值。
     *      2、getRawType ： 泛型中<>前面的那个值，即Map、List
     *      3、getOwnerType ：“拥有者”表示的含义--内部类的“父类”，通过getOwnerType()方法可以获取到内部类的“拥有者”；
     *                  例如： Map  就是 Map.Entry<String,String>的拥有者
     *
     *
     * 二、GenericArrayType  数组类型
     *     1）泛型数组类型，例如List<String>[] 、T[]等
     *     2）拥有的方法
     *          getGenericComponentType：返回泛型数组中元素的Type类型，即List<String>[] 中的 List<String>（ParameterizedTypeImpl）、T[] 中的T（TypeVariableImpl）
     *
     * 三、TypeVariable  类型变量
     *     1）指的是如List<T>、Map<K,V>中的T，K，V等值，泛型值不确定；可以有继承关系，如List<T extend String>
     *        此外，还可以对类型变量加上extend限定，这样会有类型变量对应的上限；
     *     2）拥有的方法
     *        1、getBounds ：获得该类型变量的上限，也就是泛型中extend右边的值；例如 List<T extends Number> ，Number就是类型变量T的上限；
     *           如果我们只是简单的声明了List<T>（无显式定义extends），那么默认为Object；
     *        2、getGenericDeclaration：获取声明该类型变量实体，即泛型值T
     *
     * 四、WildcardType 原始类型
     *      1）指的是如 ArrayList<?>，泛型的值含有通配符"？",可以有继承关系，如ArrayList<? extend Number>，ArrayList<? super Number>
     **     2）拥有的方法
     *        1、getUpperBounds ：获取泛型变量的上边界（extends）
     *        2、getLowerBounds：获取泛型变量的下边界（super）
     *
     * 五、Class
     *      即返回的是一个具体的类，如User，不含有泛型
     */

    /**
     * 获取需要解析的泛型T类型,如CacheResult<List<GwclBean>>
     *
     *     * @params: 需要被解析的class
     * @return: class的泛型值
     * */
    public static <T> Type findNeedClass(Class<T> cls) {
        Type genType = cls.getGenericSuperclass();
        Type findNeedType = null;
        if (genType instanceof ParameterizedType) {
            Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
            Type type = params[0];
            if (params.length > 1) {
                if (!(type instanceof ParameterizedType))
                    throw new IllegalStateException("没有填写泛型参数");
                findNeedType = ((ParameterizedType) type).getActualTypeArguments()[0];
            }else {
                findNeedType = genType;
            }
        }
        return findNeedType;
    }


    /**
     * 获取需要解析的泛型T raw类型,即将泛型擦除,如CacheResult<List<GwclBean>>,擦除后返回的type值List
     *
     * */
    public static <T> Type findRawType(Class<T> cls) {
        Type type = cls.getGenericSuperclass();
        return getGenericType((ParameterizedType)type,0);
    }

    /**
     * 将泛型擦除,如CacheResult<List<GwclBean>>,擦除后返回的值List
     *
     * @param SimpleCallBack<CacheResult<List<GwclBean>>
     * @return  List
     */
    public static Type getGenericType(ParameterizedType type, int index) {  //
        Type genericType = type.getActualTypeArguments()[index];
        if (genericType instanceof ParameterizedType) {     //参数化类型，如 ArrayList<ArrayList<Integer>>
            return ((ParameterizedType) genericType).getRawType();
        }else if (genericType instanceof TypeVariable){     //不确定类型，如List<T extend String>
            return getType(((TypeVariable) genericType).getBounds()[0],0); //如getBounds()[0]:List<String>
        }else if (genericType instanceof GenericArrayType){     //数组型，如List<String>[]
            return ((GenericArrayType) genericType).getGenericComponentType();     //List<String>,无论是几维数组，getGenericComponentType()方法都只会脱去最右边的[]，返回剩下的值；
        }else {     //原始类型，即具体的一个类，如String，bean等
            return genericType;
        }
    }

    public static Type getType(Type type, int index) {
        if (type instanceof ParameterizedType) {
           return getGenericType((ParameterizedType) type,index);
        }else if (type instanceof TypeVariable){
           return getType(((TypeVariable) type).getBounds()[0],0);
        }else {
            return type;
        }
    }

    /**
     * 获取需要解析的泛型T raw类型,即将泛型擦除,如CacheResult<List<GwclBean>>,擦除后返回的class值List
     * @param type  CacheResult<List<GwclBean>>
     * @param index
     * @return 擦除后返回的class值,List
     */
    public static Class<?> getClass(Type type, int index) {     //List<Bean>
        if (type instanceof ParameterizedType) {
            return getGenericClass((ParameterizedType)type,index);      //Bean
        }else if (type instanceof TypeVariable){
            return getClass(((TypeVariable) type).getBounds()[0],0);
        }else {
            return (Class)type;
        }
    }

    /**
     *
     * @param type  List<Bean>
     * @param index
     * @return      Bean
     */
    private static Class getGenericClass(ParameterizedType type, int index) {
        Type genericType = type.getActualTypeArguments()[index];        //Bean
        if (genericType instanceof ParameterizedType) {
            return (Class) ((ParameterizedType) genericType).getRawType();
        }else if (genericType instanceof  GenericArrayType){
            return (Class)((GenericArrayType) genericType).getGenericComponentType();
        }else if (genericType instanceof  TypeVariable){
            return getClass(((TypeVariable) genericType).getBounds()[0],0);
        }else {
            return (Class)genericType;
        }
    }
}