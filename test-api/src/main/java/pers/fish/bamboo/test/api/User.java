package pers.fish.bamboo.test.api;

/**
 * –¥µ„ ≤√¥∞…
 *
 * @author WangYichao
 * @date 2019/11/30 17:48
 */
public class User {

    private int id;

    private String name;

    private String sex;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", sex='" + sex + '\'' +
                '}';
    }
}
