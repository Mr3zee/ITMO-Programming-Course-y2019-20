public class SumLong {
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
					s += Long.parseLong(args[i].substring(begin, end));	
				}
			}
		}
		System.out.println(s);
	}
}