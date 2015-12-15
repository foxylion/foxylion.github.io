package de.jakobjarosch.site.js.client;

import static elemental.dom.IterableNodeList.iterator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import elemental.client.Browser;
import elemental.dom.Document;
import elemental.dom.Element;
import elemental.dom.Node;
import elemental.events.Event;
import jsinterop.annotations.JsType;

@JsType(namespace = "jj", name = "TagCloud")
public class TagCloud {

	private final Document doc = Browser.getDocument();

	private final List<Element> allArticles = new ArrayList<>();
	private final Set<String> tags = new HashSet<>();
	private final Map<String, List<Element>> tagToArticles = new HashMap<>();

	public TagCloud() {
		loadTags();
		createTagList();
	}

	private void loadTags() {
		for (Node tagList : iterator(doc.getElementsByName("tags"))) {
			Element articleNode = tagList.getParentElement().getParentElement();
			allArticles.add(articleNode);
			for (Node tag : iterator(tagList.getChildNodes())) {
				if (tag.getNodeName().equals("LI")) {
					String tagName = ((Element) tag).getInnerText();
					getArticleList(tagName).add(articleNode);
					tags.add(tagName);
				}
			}
		}
	}

	private void createTagList() {
		Element tagCloud = doc.getElementById("tag-cloud");
		for (String tag : tags) {
			Element tagElement = doc.createElement("li");
			tagElement.setInnerText(tag);
			tagElement.addEventListener(Event.CLICK, (e) -> {
				Browser.getWindow().getConsole().log("Hello World!");
			} , false);
			tagCloud.appendChild(tagElement);
		}
	}

	private List<Element> getArticleList(String tag) {
		if (!tagToArticles.containsKey(tag)) {
			tagToArticles.put(tag, new ArrayList<>());
		}
		return tagToArticles.get(tag);
	}
}
