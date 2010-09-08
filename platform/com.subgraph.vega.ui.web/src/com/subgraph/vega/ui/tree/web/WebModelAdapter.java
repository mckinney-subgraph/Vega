package com.subgraph.vega.ui.tree.web;

import com.subgraph.vega.api.model.web.IWebGetTarget;
import com.subgraph.vega.api.model.web.IWebHost;
import com.subgraph.vega.api.model.web.IWebPath;
import com.subgraph.vega.ui.tree.ITreeAdapter;


public class WebModelAdapter {
	
	private final static Object[] EMPTY_OBJECT_ARRAY = new Object[0];
	private final ITreeAdapter<IWebHost> webHostAdapter = new WebHostTreeStrategy();
	private final ITreeAdapter<IWebPath> webPathAdapter = new WebPathTreeStrategy();
	private final ITreeAdapter<IWebGetTarget> webGetTargetAdapter = new WebGetTargetTreeStrategy();
	
	public Object[] getChildren(Object parentElement) {
		if(parentElement instanceof IWebHost) 
			return webHostAdapter.getChildren((IWebHost) parentElement);
		else if(parentElement instanceof IWebPath)
			return webPathAdapter.getChildren((IWebPath) parentElement);
		else if(parentElement instanceof IWebGetTarget)
			return webGetTargetAdapter.getChildren((IWebGetTarget) parentElement);
		else
			return EMPTY_OBJECT_ARRAY;
	}
	
	public Object getParent(Object element) {
		if(element instanceof IWebHost)
			return webHostAdapter.getParent((IWebHost) element);
		else if(element instanceof IWebPath)
			return webPathAdapter.getParent((IWebPath) element);
		else if(element instanceof IWebGetTarget)
			return webGetTargetAdapter.getParent((IWebGetTarget) element);
		else
			return null;
	}
	
	public boolean hasChildren(Object element) {
		return childCount(element) > 0;
	}
	
	private int childCount(Object element) {
		if(element instanceof IWebPath) 
			return webPathAdapter.getChildrenCount((IWebPath) element);
		else if(element instanceof IWebGetTarget)
			return webGetTargetAdapter.getChildrenCount((IWebGetTarget) element);
		else  if(element instanceof IWebHost)
			return webHostAdapter.getChildrenCount((IWebHost) element);
		else
			return 0;
	}
	
	public String getLabel(Object element) {
		if(element instanceof IWebPath)
			return webPathAdapter.getLabel((IWebPath) element);
		if(element instanceof IWebGetTarget)
			return webGetTargetAdapter.getLabel((IWebGetTarget) element);
		if(element instanceof IWebHost)
			return webHostAdapter.getLabel((IWebHost) element);
		else
			return null;
	}
}
