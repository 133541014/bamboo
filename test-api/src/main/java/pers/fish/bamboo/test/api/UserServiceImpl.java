package pers.fish.bamboo.test.api;

/**
 * д��ʲô��
 *
 * @author WangYichao
 * @date 2019/11/30 17:50
 */
public class UserServiceImpl implements IUserService {

    @Override
    public String saveUser(User user) {
        System.out.println("�û���Ϣ����ɹ�: " + user);
        return "success";
    }

    @Override
    public User getUser(int id) {
        User user = new User();
        user.setId(id);
        user.setName("����");
        user.setSex("1");
        System.out.println("��ѯ���û���Ϣ: " + user);
        return user;
    }
}
