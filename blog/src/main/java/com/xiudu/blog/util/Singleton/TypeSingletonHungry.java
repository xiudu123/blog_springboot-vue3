package com.xiudu.blog.util.Singleton;

import com.xiudu.blog.pojo.DO.Type;
import com.xiudu.blog.pojo.VO.type.TypeIndexVO;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: 锈渎
 * @date: 2024/2/1 22:18
 * @code: 面向对象面向君， 不负代码不负卿。
 * @description: 单例模式： 用于 分类 类的 pojo 转换
 */
public class TypeSingletonHungry {
    private static final TypeSingletonHungry instance = new TypeSingletonHungry();

    private TypeSingletonHungry() {}
    public static TypeSingletonHungry getInstance() {
        return instance;
    }

    public List<TypeIndexVO> TypeToIndex(List<Type> types) {
        List<TypeIndexVO> typeIndexVOS = new ArrayList<>();
        for(Type type : types) {
            typeIndexVOS.add(new TypeIndexVO()
                    .setId(type.getId())
                    .setName(type.getName())
                    .setCount(type.getCount()));
        }
        return typeIndexVOS;
    }

}
