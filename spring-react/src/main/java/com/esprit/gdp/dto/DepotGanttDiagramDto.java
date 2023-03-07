package com.esprit.gdp.dto;

public class DepotGanttDiagramDto
{
	private String pathGanttDiagram;
	
	public DepotGanttDiagramDto() {}

	public DepotGanttDiagramDto(String pathGanttDiagram)
	{
		this.pathGanttDiagram = pathGanttDiagram;
	}

	public String getPathGanttDiagram() {
		return pathGanttDiagram;
	}

	public void setPathGanttDiagram(String pathGanttDiagram) {
		this.pathGanttDiagram = pathGanttDiagram;
	}

}
