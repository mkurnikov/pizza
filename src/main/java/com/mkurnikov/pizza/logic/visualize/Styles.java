package com.mkurnikov.pizza.logic.visualize;

import com.mxgraph.util.mxConstants;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Styles {
	private Map<String, Object> unactiveVertices;
	private Map<String, Object> activeVertices;
	private Map<String, Object> unactiveEdges;
	private Map<String, Object> activeEdges;

	public Styles() {
		unactiveVertices = new HashMap<>();
		activeVertices = new HashMap<>();
		unactiveEdges = new HashMap<>();
		activeEdges = new HashMap<>();
	}

	public Map<String, Object> getUnactiveVertices() {
		return unactiveVertices;
	}

	public Map<String, Object> getActiveVertices() {
		if (activeVertices.size() == 0) {
			activeVertices.put(mxConstants.STYLE_FONTCOLOR, "black");
			activeVertices.put(mxConstants.STYLE_FONTSTYLE, mxConstants.FONT_BOLD);
			activeVertices.put(mxConstants.STYLE_FILLCOLOR, "red");
		}
		return activeVertices;
	}

	public Map<String, Object> getUnactiveEdges() {
		if (unactiveEdges.size() == 0) {
			unactiveEdges.put(mxConstants.STYLE_ENDARROW, mxConstants.NONE);
		}
		return unactiveEdges;
	}

	public Map<String, Object> getActiveEdges() {
		if (activeEdges.size() == 0) {
			activeEdges.put(mxConstants.STYLE_STROKECOLOR, "red");
			activeEdges.put(mxConstants.STYLE_STROKEWIDTH, "5.0");
			activeEdges.put(mxConstants.STYLE_FONTCOLOR, "black");
			activeEdges.put(mxConstants.STYLE_ENDARROW, mxConstants.NONE);
			activeEdges.put(mxConstants.STYLE_FONTSTYLE, mxConstants.FONT_BOLD);
		}
		return activeEdges;
	}
}
