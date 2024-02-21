package com.sora.service.impl;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryChain;
import com.sora.common.UserConstant;
import com.sora.domain.User;
import com.sora.domain.permissions.SoraMenu;
import com.sora.domain.permissions.SoraMenuRole;
import com.sora.domain.permissions.SoraUserRole;
import com.sora.domain.permissions.table.SoraMenuRoleTableDef;
import com.sora.domain.permissions.table.SoraUserRoleTableDef;
import com.sora.mapper.UserMapper;
import com.sora.mapper.permissions.MenuMapper;
import com.sora.mapper.permissions.MenuRoleMapper;
import com.sora.mapper.permissions.UserRoleMapper;
import com.sora.result.Result;
import com.sora.service.UserService;
import com.sora.utils.JwtUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

import static com.sora.domain.table.UserTableDef.USER;

/**
 * @Classname UserServiceImpl
 * @Description
 * @Date 2023/11/22 10:24
 * @Author by Sora33
 */
@Service
public class UserServiceImpl implements UserService {

    private Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    private final UserMapper userMapper;
    private final UserRoleMapper userRoleMapper;
    private final MenuMapper menuMapper;
    private final MenuRoleMapper menuRoleMapper;
    private final Integer ADMIN_ID = 1;

    public UserServiceImpl(UserMapper userMapper, UserRoleMapper userRoleMapper, MenuMapper menuMapper, MenuRoleMapper menuRoleMapper) {
        this.userMapper = userMapper;
        this.userRoleMapper = userRoleMapper;
        this.menuMapper = menuMapper;
        this.menuRoleMapper = menuRoleMapper;
    }


    /**
     * 查询用户
     * @return
     */
    @Override
    public Result select(String name, Integer pageNum, Integer pageSize) {
        if ("null".equals(name) || StrUtil.isBlank(name)) {
            name = null;
        }
        Page<User> page = QueryChain.of(userMapper)
                .where(USER.NAME.like(name))
                .page(new Page<>(pageNum, pageSize));
        return Result.success(page);
    }


    /**
     * 新增用户
     * @param user
     * @return
     */
    @Override
    public Result insert(User user) {
        // 判断是否重复名字
        List<User> userList = QueryChain.of(userMapper)
                .where(USER.NAME.eq(user.getName()))
                .limit(1)
                .list();
        if (!userList.isEmpty()) {
            return Result.error("用户名重复！");
        }
        // 对用户的密码进行加盐加密
        String pwd = bCryptPasswordEncoder.encode(user.getPassword() + UserConstant.SLAT);
        user.setPassword(pwd);
        user.setId(IdUtil.getSnowflakeNextIdStr());
        user.setCreateTime(new Date());
        return userMapper.insertSelective(user) > 0 ? Result.success(null,"注册成功！") : Result.error("注册失败，请稍后重试！");
    }


    /**
     * 登陆
     * @param name
     * @param password
     * @return
     */
    @Override
    public Result login(String name, String password) {
        // 根据name获取用户
        List<User> userList = QueryChain.of(userMapper)
                .where(USER.NAME.eq(name))
                .limit(1)
                .list();
        if (userList.isEmpty()) {
            return Result.error("用户不存在！");
        }
        // 密码验证
        if (bCryptPasswordEncoder.matches(password + UserConstant.SLAT, userList.get(0).getPassword())) {
            return Result.success(userList.get(0),"登陆成功！");
        }
        return Result.error("密码错误！");
    }

    @Override
    public Result permissionsById(String id) {
        // 获取用户所有角色
        List<Integer> roleIdList = QueryChain.of(userRoleMapper)
                .where(SoraUserRoleTableDef.SORA_USER_ROLE.USER_ID.eq(id))
                .list()
                .stream()
                .map(SoraUserRole::getRoleId)
                .toList();
        if (roleIdList.isEmpty()) {
            return Result.success();
        }
        // 获取全量菜单列表
        List<SoraMenu> menuList = QueryChain.of(menuMapper).list();
        // 判断是否有管理员权限
        boolean match = roleIdList.stream().anyMatch(data -> data.equals(ADMIN_ID));
        if (match) {
            return Result.success(menuList);
        }
        // 获取该用户角色所能查看的菜单列表
        List<Integer> userMenuList = QueryChain.of(menuRoleMapper)
                .where(SoraMenuRoleTableDef.SORA_MENU_ROLE.ROLE_ID.in(roleIdList))
                .list()
                .stream()
                .map(SoraMenuRole::getMenuId)
                .toList();
        // 在全量菜单基础上筛选
        List<SoraMenu> soraMenus = menuList.stream().filter(data -> userMenuList.contains(data.getId())).toList();
        return Result.success(soraMenus);
    }


    /**
     * 获取用户权限
     * @param id
     * @return
     */
    @Override
    public Result permissionsByToken(String id) {
        id = JwtUtils.getUserId(id);
        return this.permissionsById(id);
    }

    /**
     * 根据用户名获取用户
     * @param name
     * @return
     */
    @Override
    public Result selectUserByName(String name) {
        Page<User> page = QueryChain.of(userMapper)
                .where(USER.NAME.eq(name))
                .page(new Page<>(1, 100));
        return Result.success(page);
    }


    @Override
    public Result update(User user) {
        int update = userMapper.update(user);
        return update > 0 ? Result.success("更新用户信息成功") : Result.error("更新用户失败！");
    }
}
