package cn.tom;

/**
 * @author lxl
 */
public class HelloJNI {
    static {
        System.load("/Users/tom/IdeaProjects/myDev/springdemo/springdemo/src/test/java/cn/tom/goodLuck.dll");
        HelloJNI test = new HelloJNI();
    }

    public native static int get();

    public native static void set(int i);

    public static void main(String[] args) {
        HelloJNI test = new HelloJNI();
//        test.set(10);
        System.out.println(test.get());
        System.out.println(System.getProperty("java.library.path"));
    }
}
