package de.jakobjarosch.site.js.client;

import static elemental.dom.IterableNodeList.iterator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import elemental.client.Browser;
import elemental.dom.Document;
import elemental.dom.Element;
import elemental.dom.Node;
import elemental.events.Event;
import jsinterop.annotations.JsType;

@JsType(namespace = "jj_js", name = "TagCloud")
public class TagCloud {

	private final Document doc = Browser.getDocument();

	private final List<Element> allArticles = new ArrayList<>();
	private final Map<String, Element> tags = new HashMap<>();
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
					tags.put(tagName, null);
				}
			}
		}
	}

	private void createTagList() {
		int timeout = 0;
		for (String tag : getOrderedTagList()) {
			Browser.getWindow().setTimeout(() -> createTag(tag), timeout += 20); 
		}
	}

	private void createTag(String tag) {
		Element tagCloud = doc.getElementById("tag-cloud");
		Element tagElement = doc.createElement("li");
		tags.put(tag, tagElement);
		tagElement.setInnerText(tag);
		tagElement.addEventListener(Event.CLICK, (e) -> {
			if (tagElement.getAttribute("class") != null) {
				disableFilter();
			} else {
				setFilter(tag);
			}
		} , true);
		tagCloud.appendChild(tagElement);
	}

	private List<String> getOrderedTagList() {
		ArrayList<String> tagList = new ArrayList<>();
		tagList.addAll(tags.keySet());
		Collections.sort(tagList);
		return tagList;
	}

	private void setFilter(String tag) {
		for (Element article : allArticles) {
			if (tagToArticles.get(tag).contains(article)) {
				article.getStyle().setDisplay("block");
			} else {
				article.getStyle().setDisplay("none");
			}
		}
		for (Entry<String, Element> tagg : tags.entrySet()) {
			if (tagg.getKey().equals(tag)) {
				tagg.getValue().setAttribute("class", "active");
			} else {
				tagg.getValue().removeAttribute("class");
			}
		}
	}

	private void disableFilter() {
		for (Entry<String, Element> tagg : tags.entrySet()) {
			tagg.getValue().removeAttribute("class");
		}
		for (Element article : allArticles) {
			article.getStyle().setDisplay("block");
		}
	}

	private List<Element> getArticleList(String tag) {
		if (!tagToArticles.containsKey(tag)) {
			tagToArticles.put(tag, new ArrayList<>());
		}
		return tagToArticles.get(tag);
	}
}
