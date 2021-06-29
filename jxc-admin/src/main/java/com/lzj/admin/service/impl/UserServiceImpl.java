package com.lzj.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lzj.admin.pojo.User;
import com.lzj.admin.mapper.UserMapper;
import com.lzj.admin.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lzj.admin.utils.AssertUtil;
import com.lzj.admin.utils.StringUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author 老李
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {


    //注入Springsecurity中生成密码加密的方法
    @Resource
    private PasswordEncoder passwordEncoder;

    /**
     * 根据用户名来查找，用户名唯一
     * @param userName
     * @return
     */
    @Override
    public User findUserByUserName(String userName) {
        /**
         * 根据wrapper条件查询一条记录
         * 查询用户中没有删除的用户
         * QueryWrapper 是mybatis  plus中实现查询的实体类对象封装操作类
         */

        return this.baseMapper.selectOne(new QueryWrapper<User>().eq("is_del",0).eq("user_name",userName));
    }

    @Override
    //用在接口实现类 Spring AOP的本质决定此注解用在public方法上
    //sql的事务  ropagation.REQUIRED   支持当前事务，如果当前没有事务，就新建一个事务rollbackFor  回调，在出现异常时进行事务回调
    @Transactional(propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    public void updateUserInfo(User user) {
        /**
         * 用户名
         *    非空
         *    唯一
         */
        AssertUtil.isTrue(StringUtil.isEmpty(user.getUsername()),"用户名不能为空!");
        User temp = this.findUserByUserName(user.getUsername());
        AssertUtil.isTrue(null !=temp && !(temp.getId().equals(user.getId())),"用户名已存在!");
        AssertUtil.isTrue(!(this.updateById(user)),"用户信息更新失败!");
    }



    @Override
    @Transactional(propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    public void updateUserPassword(String userName, String oldPassword, String newPassword, String confirmPassword) {
        /**
         * 用户名非空 必须存在
         * 原始密码 新密码 确认密码 均不能为空
         * 原始密码必须正确
         * 新密码 与 确认密码必须一致  并且不能与原始密码相同
         */
        User user=null;
        user = this.findUserByUserName(userName);
        AssertUtil.isTrue(null==user,"用户不存在或未登录!");
        AssertUtil.isTrue(StringUtil.isEmpty(oldPassword),"请输入原始密码!");
        AssertUtil.isTrue(StringUtil.isEmpty(newPassword),"请输入新密码!");
        AssertUtil.isTrue(StringUtil.isEmpty(confirmPassword),"请输入确认密码!");
      //不能使用明文比对  加密的密码比对matches(明文，密文)
        AssertUtil.isTrue(!(passwordEncoder.matches(oldPassword,user.getPassword())),"原始密码输入错误!");
        AssertUtil.isTrue(!(newPassword.equals(confirmPassword)),"新密码输入不一致!");
        AssertUtil.isTrue(newPassword.equals(oldPassword),"新密码与原始密码不能一致!");
        user.setPassword(passwordEncoder.encode(oldPassword));
        AssertUtil.isTrue(!(this.updateById(user)),"用户密码更新失败!");

    }


}
