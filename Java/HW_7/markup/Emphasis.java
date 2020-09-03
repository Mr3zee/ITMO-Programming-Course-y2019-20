package markup;

import java.util.*;

public class Emphasis extends AbstractMark {

	public Emphasis(List<Mark> list) {
		super(list);
	}

	@Override
	protected String getHtmlTag() {
		return "em";
	}

	protected String getMarkdownTag() {
		return "*";
	}
}