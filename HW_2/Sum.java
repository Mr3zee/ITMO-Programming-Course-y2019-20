public class SumLong {
	public static void main(String[] args) {
		long s = 0;
		for (int i = 0; i < args.length; i++) {
			long n = 0, minus = 1;
			String str = args[i];
			for (int j = 0; j < str.length(); j++) {
				if (str.charAt(j) != '-') {
					int v = str.charAt(j) - '0';
					//System.out.println(v);
					if (!(v >= 0 && v < 10)) {
						s += minus * n;
						minus = 1;
						n = 0;
					} else {
						n *= 10;
						n += v;
					}
				} else {
					minus = -1;
				}
			}
			s += minus * n;
		}
		System.out.println(s);
	}
}