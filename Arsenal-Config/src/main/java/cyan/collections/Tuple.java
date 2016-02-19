package cyan.collections;

/**
 * A simple java tuple  
 * 
 * @author DreamInSun  
 */
public class Tuple<A> {
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static <A> Tuple mk(A... args) {
		return new Tuple<A>(args);
	}

	private A[] items;

	private Tuple(A[] items) {
		this.items = items;
	}

	public A _(int index) {
		if (index < 0 || items == null || index > items.length - 1) {
			return null;
		}
		return items[index];
	}

	/*======================  =====================*/
	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		Tuple<String> t = Tuple.mk("argument1", "fantasia@sina.come");
		System.out.println(t._(0));
	}
}
