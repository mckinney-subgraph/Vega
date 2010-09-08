package com.subgraph.vega.ui.http.requestviewer;

import java.util.ArrayList;

import java.util.List;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.Viewer;

import com.subgraph.vega.api.events.IEvent;
import com.subgraph.vega.api.events.IEventHandler;
import com.subgraph.vega.api.requestlog.IRequestLog;
import com.subgraph.vega.api.requestlog.IRequestLogChangeEvent;
import com.subgraph.vega.api.requestlog.IRequestLogRecord;

public class HttpViewContentProvider implements IStructuredContentProvider {
	
	private IRequestLog requestLog;
	private Viewer viewer;
	private final IEventHandler requestLogListener;
	private final List<IRequestLogRecord> requestRecords = new ArrayList<IRequestLogRecord>();
	
	public HttpViewContentProvider() {
		requestLogListener = createRequestLogListener();
	}
	
	public Object[] getElements(Object inputElement) {
		return requestRecords.toArray();
	}
	
	public void dispose() {
		
	}
	
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		if(newInput == null)
			setNullModel();
		else if(newInput instanceof IRequestLog) 
			setNewRequestLogAndViewer((IRequestLog) newInput, viewer);
	}
	
	private void setNullModel() {
		requestRecords.clear();
		requestLog = null;
	}
	private void setNewRequestLogAndViewer(IRequestLog newRequestLog, Viewer newViewer) {
		if(newRequestLog != requestLog) {
			if(requestLog != null)
				requestLog.removeChangeListener(requestLogListener);
			requestLog = newRequestLog;
			requestRecords.clear();
			requestLog.addChangeListenerAndPopulate(requestLogListener);
			this.viewer = newViewer;
		}
	}
	
	private IEventHandler createRequestLogListener() {
		return new IEventHandler() {
			@Override
			public void handleEvent(IEvent event) {
				if(event instanceof IRequestLogChangeEvent) 
					handleRequestLogChange((IRequestLogChangeEvent) event);				
			}
		};
	}
	
	private void handleRequestLogChange(IRequestLogChangeEvent event) {
		if(event.isRecordAddEvent() && (event.getRecord() instanceof IRequestLogRecord))
			addRequestRecord((IRequestLogRecord) event.getRecord());
	}
	
	private void addRequestRecord(IRequestLogRecord record) {
		requestRecords.add(record);
		refreshViewer();
	}
	
	private void refreshViewer() {
		if(viewer != null) {
			synchronized(viewer) {
				viewer.getControl().getDisplay().asyncExec(new Runnable() {
					@Override
					public void run() {
						if(!viewer.getControl().isDisposed())
							viewer.refresh();						
					}
				});
			}
		}
	}
	

}
