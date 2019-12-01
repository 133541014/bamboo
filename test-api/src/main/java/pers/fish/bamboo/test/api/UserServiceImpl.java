package pers.fish.bamboo.test.api;

/**
 * 写点什么吧
 *
 * @author WangYichao
 * @date 2019/11/30 17:50
 */
public class UserServiceImpl implements IUserService {

    @Override
    public String saveUser(User user) {
        System.out.println("用户信息保存成功: " + user);
        return "success";
    }

    @Override
    public User getUser(int id) {
        User user = new User();
        user.setId(id);
        user.setName("张三");
        user.setSex("1");
        System.out.println("查询到用户信息: " + user);
        return user;
    }
}
