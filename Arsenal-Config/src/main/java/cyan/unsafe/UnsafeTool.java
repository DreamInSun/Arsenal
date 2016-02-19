package cyan.unsafe;

import sun.misc.Unsafe;
import java.lang.reflect.Field;

@SuppressWarnings("restriction")
public class UnsafeTool {

	public static Unsafe getUnsafeInstance() throws SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
		Field theUnsafeInstance = Unsafe.class.getDeclaredField("theUnsafe");
		theUnsafeInstance.setAccessible(true);
		return (Unsafe) theUnsafeInstance.get(Unsafe.class);
	}

}
