package com.qsj.acojbackendserviceclient.service;

import com.qsj.acojbackendcommon.common.ErrorCode;
import com.qsj.acojbackendcommon.exception.BusinessException;
import com.qsj.acojbackendmodel.entity.User;
import com.qsj.acojbackendmodel.enums.UserRoleEnum;
import com.qsj.acojbackendmodel.vo.UserVO;
import org.springframework.beans.BeanUtils;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.List;

import static com.qsj.acojbackendcommon.constant.UserConstant.USER_LOGIN_STATE;

@FeignClient(name = "acoj-backend-user-service", path = "/api/user/inner")
//使用openFeign把这几个接口注册到Nacos注册中心上，这个几个接口公共的调用前缀是path
//主要的目的是，模块需要不同服务的接口（bean），实现跨服务的远程调用
public interface UserFeignClient {


//    user = userService.getById(userId);
//    UserVO userVO = userService.getUserVO(user);
//        userService.isAdmin(loginUser))
//            userService.listByIds(userIdSet).stream()
//          userService.getLoginUser(request);

    /**
     * 根据id获取用户
     *
     * @param userId
     * @return
     */
    @GetMapping("/get/id")
    User getById(@RequestParam("userId") long userId);

    /**
     * 根据id获取用户列表
     *
     * @param idList
     * @return
     */
    @GetMapping("get/ids")
    List<User> listByIds(@RequestParam("idList") Collection<Long> idList);

    /**
     * 获取当前登录用户
     *
     * @param request
     * @return
     */
    default User getLoginUser(HttpServletRequest request) {
        // 先判断是否已登录
        Object userObj = request.getSession().getAttribute(USER_LOGIN_STATE);
        User currentUser = (User) userObj;
        if (currentUser == null || currentUser.getId() == null) {
            throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR);
        }
        return currentUser;
    }

    /**
     * 是否为管理员
     *
     * @param user
     * @return
     */
    default boolean isAdmin(User user) {
        return user != null && UserRoleEnum.ADMIN.getValue().equals(user.getUserRole());

    }


    /**
     * 获取脱敏的用户信息
     *
     * @param user
     * @return
     */
    default UserVO getUserVO(User user) {
        if (user == null) {
            return null;
        }
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(user, userVO);
        return userVO;
    }

}
