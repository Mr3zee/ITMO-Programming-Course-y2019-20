package markup;

import java.util.*;

public class Strikeout extends AbstractMark {

	public Strikeout(List<Mark> list) {
		super(list);
	}

	@Override
	protected String getHtmlTag() {
		return "s";
	}

	protected String getMarkdownTag() {
		return "~";
	}
}