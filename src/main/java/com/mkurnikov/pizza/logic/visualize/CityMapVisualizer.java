package com.mkurnikov.pizza.logic.visualize;

import com.mkurnikov.pizza.logic.paths.models.District;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CityMapVisualizer {
//	private List<mxCell> districtCells = new ArrayList<>();
//	private Map<String, mxCell> districtCells = new HashMap<>();
// 	private List<mxCell> pathCells = new ArrayList<>();
	private mxGraph graph = new mxGraph();
	private Object parent = graph.getDefaultParent();

	public CityMapVisualizer() {
		init();
	}

	public void saveToImage(String fpath) {
		BufferedImage image = mxCellRenderer.createBufferedImage(this.graph, null, 1,
				Color.WHITE, true, null);
		try {
			ImageIO.write(image, "PNG", new File(fpath));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public void addEdge(Path path) {
		graph.getModel().beginUpdate();
		try {
			String id = path.firstDistrict.name + "-" + path.secondDistrict.name;
			Object edge = graph.insertEdge(parent, id, path.travellingTime,
					getCells().get(path.firstDistrict.name), getCells().get(path.secondDistrict.name));
		} finally {
			graph.getModel().endUpdate();
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
