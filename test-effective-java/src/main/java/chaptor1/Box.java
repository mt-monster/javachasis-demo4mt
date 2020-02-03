package chaptor1;

/**
 * 一句话功能简述
 * 功能详细描述
 * @author m00416667
 * @version [版本号, ]
 * @see  [相关类/方法]
 * @since [产品/模块版本]
 * Package Name:chaptor1
 */

import java.util.HashMap;
import java.util.Map;

public final class Box {
    private int length;

    private int width;

    private int heigth;

    public Box() {

    }

    public Box(int length, int width, int heigth) {
        this.length = length;
        this.width = width;
        this.heigth = heigth;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeigth() {
        return heigth;
    }

    public void setHeigth(int heigth) {
        this.heigth = heigth;
    }

    @Override
    //重写equals方法。
    /**
     * 实现高质量equals方法的诀窍:
     1.使用==操作符检查“参数是否为这个对象的引用”。
     2.使用instanceof操作符检查“参数是否为正确的类型”。
     3.把参数转化为正确的类型。
     4.对于该类的中每个关键域，检查参数中的域是否与该对象中对应的域想匹配。
     */
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof Box)) {
            return false;
        } else {
            Box boxtmp = (Box) o;
            return boxtmp.heigth == this.heigth
                    && boxtmp.width == this.width
                    && boxtmp.length == this.length;
        }
    }

    @Override
    public int hashCode() {
        int result = 0;
        result = result * 31 + length;
        result = result * 31 + heigth;
        result = result * 31 + width;
        return result;
    }

}

class HashCodeAndEqualTest {
    public static void main(String[] args) {
        Map<Box, Integer> map = new HashMap<>();
        Box a = new Box(1, 2, 3);
        Box b = new Box(1, 2, 3);
        System.out.println("a == b :" + (a == b));
        System.out.println("a .equals(b):" + a.equals(b));
        map.put(a, 1);
        System.out.println(map.get(a));
        System.out.println(map.get(b));
    }
}
