package markup;

import java.util.*;

public class Text implements Mark {
	private String text;

	public Text(String text) {
		this.text = text;
	}

	public void toMarkdown(StringBuilder wrapper) {
		wrapper.append(text);
	}

	public void toHtml(StringBuilder wrapper) {
		wrapper.append(text);
	}
}