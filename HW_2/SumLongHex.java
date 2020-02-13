public class SumLongHex {
	public static void main(String[] args) {
		long s = 0;
		for (int i = 0; i < args.length; i++) {
			int j = 0;
			while (j < args[i].length()) {
				while (j < args[i].length() && Character.isWhitespace(args[i].charAt(j))) {
					j++;
				}
				int begin = j;
				while (j < args[i].length() && !Character.isWhitespace(args[i].charAt(j))) {
					j++;
				}

				int end = j;
				if (begin != end) {
					int n = 10, x = 0;
					String str = args[i].substring(begin, end);
					if (str.startsWith("0x") || str.startsWith("0X")) {
						begin += 2;
						n = 16;
					}
					if (str.charAt(0) == '-') {
						s += Long.parseLong(args[i].substring(begin, end), n);	
					} else {
						s += Long.parseUnsignedLong(args[i].substring(begin, end), n);	
					}
				}
			}
		}
		System.out.println(s);
	}
}