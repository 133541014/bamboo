package pers.fish.bamboo.test.api;

/**
 * д��ʲô��
 *
 * @author WangYichao
 * @date 2019/11/30 17:50
 */
public interface IUserService {

    String saveUser(User user);

    User getUser(int id);
}
