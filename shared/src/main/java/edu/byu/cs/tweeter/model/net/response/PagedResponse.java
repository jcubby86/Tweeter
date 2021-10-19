package edu.byu.cs.tweeter.model.net.response;

import java.util.List;

public abstract class PagedResponse<DATA> extends Response{
    private List<DATA> items;
    private boolean hasMorePages;

    protected PagedResponse() {
    }

    public PagedResponse(String message) {
        super(message);
    }

    public PagedResponse(List<DATA> items, boolean hasMorePages) {
        this.items = items;
        this.hasMorePages = hasMorePages;
    }

    public List<DATA> getItems() {
        return items;
    }

    public void setItems(List<DATA> items) {
        this.items = items;
    }

    public boolean isHasMorePages() {
        return hasMorePages;
    }

    public void setHasMorePages(boolean hasMorePages) {
        this.hasMorePages = hasMorePages;
    }
}
