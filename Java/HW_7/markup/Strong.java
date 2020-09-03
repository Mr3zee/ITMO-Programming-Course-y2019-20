package markup;

import java.util.*;

public class Strong extends AbstractMark {

	public Strong(List<Mark> list) {
		super(list);
	}

	@Override
	protected String getHtmlTag() {
		return "strong";
	}

	protected String getMarkdownTag() {
		return "__";
	}
}