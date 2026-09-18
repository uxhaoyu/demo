public class ExceptionDemo {
    public static void main(String[] args) {
        // 1. try-catch-finally：除零被接住，finally 一定会执行
        try {
            int a = 10 / 0;          // 除零，抛 ArithmeticException
            System.out.println("这行不会执行");
        } catch (ArithmeticException e) {
            System.out.println("出错了：" + e.getMessage());   // 出错了：/ by zero
        } finally {
            System.out.println("不管怎样我都会执行");          // 一定打印
        }

        // 2. 多 catch：从上往下匹配，先具体后兜底
        try {
            int[] arr = {1, 2};
            System.out.println(arr[5]);     // 越界
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("数组越界了");
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println("其他错误：" + e.getMessage());
        }

        // 3. throw：主动抛异常（年龄不能为负数）
        try {
            checkAge(-1);
        } catch (IllegalArgumentException e) {
            System.out.println("校验失败：" + e.getMessage());
        }
        try {
            checkAge(0);
        }catch (IllegalArgumentException e) {
            System.out.println("bug");
        }
    }

    // throws：声明"我这里可能抛异常，调用者处理"
    static void checkAge(int age) throws IllegalArgumentException {
        if (age < 0) {
            throw new IllegalArgumentException("年龄不能为负数");   // 主动抛
        }
        System.out.println("年龄合法：" + age);
    }
}