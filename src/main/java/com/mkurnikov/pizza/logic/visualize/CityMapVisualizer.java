package com.mkurnikov.pizza.logic.visualize;

import com.mkurnikov.pizza.logic.paths.models.Path;
import com.mxgraph.model.mxCell;
import com.mxgraph.model.mxGraphModel;
import com.mxgraph.util.mxCellRenderer;
import com.mxgraph.view.mxGraph;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.List;

public class CityMapVisualizer {
	private mxGraph graph = new mxGraph();
	private Object parent = graph.getDefaultParent();
	private Styles styles = new Styles();
	private Set<mxCell> shortestPathEdges = null;
	private Set<mxCell> shortestPathVertices = null;
//	private Map<String, Object> defaultEdgesStyles = graph.getStylesheet().getDefaultEdgeStyle();
//	private Map<String, Object> defaultVerticesStyles = graph.getStylesheet().getDefaultVertexStyle();

	public CityMapVisualizer() {
		init();
	}

	public void setDefaultStyles() {
		for (Object cellObject: getCells().values()) {
			mxCell cell = (mxCell) cellObject;
			if (cell.isEdge()) {
				changeCellStyles(cell, graph.getStylesheet().getDefaultEdgeStyle());
				changeCellStyles(cell, styles.getUnactiveEdges());
			}
			if (cell.isVertex()) {
				changeCellStyles(cell, graph.getStylesheet().getDefaultVertexStyle());
				changeCellStyles(cell, styles.getUnactiveVertices());
			}
		}
		System.out.println("styles defaulted");
	}

	public void setShortestPath(List<Path> shortestPath) {
		graph.getModel().beginUpdate();
		try {
//			cells to change
			shortestPathEdges = new HashSet<>();
			shortestPathVertices = new HashSet<>();
			for (Path edge: shortestPath) {
				shortestPathVertices.add((mxCell) getCells().get(edge.firstDistrict.name));
				shortestPathVertices.add((mxCell) getCells().get(edge.secondDistrict.name));

				String edge_id = edge.firstDistrict.name + "-" + edge.secondDistrict.name;
				shortestPathEdges.add((mxCell) getCells().get(edge_id));
			}

		} finally {
			graph.getModel().endUpdate();
		}
	}

	public void changeCellStyles(Object[] cells, Map<String, Object> styles) {
		for (Map.Entry<String, Object> entry: styles.entrySet()) {
			graph.setCellStyles(entry.getKey(), entry.getValue().toString(), cells);
		}
	}

	public void changeCellStyles(Object cell, Map<String, Object> styles) {
		for (Map.Entry<String, Object> entry: styles.entrySet()) {
			if (entry.getValue() instanceof String) {
				graph.setCellStyles(entry.getKey(), entry.getValue().toString(), new Object[] {cell});
			}
		}
	}

	public void addEdge(Path path) {
		graph.getModel().beginUpdate();
		try {
			String id = path.firstDistrict.name + "-" + path.secondDistrict.name;
			graph.insertEdge(parent, id, path.travellingTime,
					getCells().get(path.firstDistrict.name), getCells().get(path.secondDistrict.name));
		} finally {
			graph.getModel().endUpdate();
		}
	}

	public BufferedImage getAsImage() {
		setDefaultStyles();
		if (shortestPathEdges != null) {
			changeCellStyles(shortestPathEdges.toArray(), styles.getActiveEdges());
		}
		if (shortestPathVertices != null) {
			changeCellStyles(shortestPathVertices.toArray(), styles.getActiveVertices());
		}
		return mxCellRenderer.createBufferedImage(this.graph, null, 1,
				Color.WHITE, true, null);
	}

	public void saveToImage(String fpath) {
		BufferedImage image = getAsImage();
		try {
			ImageIO.write(image, "PNG", new File(fpath));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	private Map<String, Object> getCells() {
		return ((mxGraphModel) graph.getModel()).getCells();
	}

	private void init() {
		graph.getModel().beginUpdate();
		try {
			int x_offset = 10;
			int y_offset = 10;
			int width = 80;
			int height = 50;
			Object v1 = graph.insertVertex(parent, "Красносельский", "Красносельский", 10 + x_offset, 10 + y_offset, width, height);
			Object v2 = graph.insertVertex(parent, "Кронштадский", "Кронштадский", 140 + x_offset, 20 + y_offset, width, height);
			Object v3 = graph.insertVertex(parent, "Курортный", "Курортный", 260 + x_offset, 50 + y_offset, width, height);
			Object v4 = graph.insertVertex(parent, "Московский", "Московский", 400 + x_offset, 10 + y_offset, width, height);
			Object v5 = graph.insertVertex(parent, "Невский", "Невский", 130 + x_offset, 130 + y_offset, width, height);
			Object v6 = graph.insertVertex(parent, "Петроградский", "Петроградский", 10 + x_offset, 150 + y_offset, width, height);
			Object v7 = graph.insertVertex(parent, "Петродворцовый", "Петродворцовый", 30 + x_offset, 280 + y_offset, width, height);
			Object v8 = graph.insertVertex(parent, "Приморский", "Приморский", 170 + x_offset, 230 + y_offset, width, height);
			Object v9 = graph.insertVertex(parent, "Пушкинский", "Пушкинский", 330 + x_offset, 140 + y_offset, width, height);
			Object v10 = graph.insertVertex(parent, "Фрунзенский", "Фрунзенский", 350 + x_offset, 230 + y_offset, width, height);
			Object v11 = graph.insertVertex(parent, "Центральный", "Центральный", 250 + x_offset, 330 + y_offset, width, height);
//			districtCells.add((mxCell) v1);
//			districtCells.add((mxCell) v2);
//			districtCells.add((mxCell) v3);
//			districtCells.add((mxCell) v4);
//			districtCells.add((mxCell) v5);
//			districtCells.add((mxCell) v6);
//			districtCells.add((mxCell) v7);
//			districtCells.add((mxCell) v8);
//			districtCells.add((mxCell) v9);
//			districtCells.add((mxCell) v10);
//			districtCells.add((mxCell) v11);
		} finally {
			graph.getModel().endUpdate();
		}
	}
}
