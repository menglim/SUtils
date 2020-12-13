package com.github.menglim.sutils.common;

import org.springframework.data.domain.Page;
import com.github.menglim.sutils.configuration.CoreConstants;

public class Pager<T> {
    private int buttonsToShow = 5;

    private int startPage;

    private int endPage;

    private Page<T> data;

    public Page<T> getData() {
        return this.data;
    }

    public int[] getAvailablePageSizes() {
        return CoreConstants.PAGE_SIZES;
    }

    public int getCurrentPageSize() {
        int pageSize = this.data.getPageable().getPageSize();
        return pageSize;
    }

    public Pager(int totalPages, int currentPage, int buttonsToShow, Page<T> data) {

        this.data = data;
        setButtonsToShow(buttonsToShow);

        int halfPagesToShow = getButtonsToShow() / 2;

        if (totalPages <= getButtonsToShow()) {
            setStartPage(1);
            setEndPage(totalPages);

        } else if (currentPage - halfPagesToShow <= 0) {
            setStartPage(1);
            setEndPage(getButtonsToShow());

        } else if (currentPage + halfPagesToShow == totalPages) {
            setStartPage(currentPage - halfPagesToShow);
            setEndPage(totalPages);

        } else if (currentPage + halfPagesToShow > totalPages) {
            setStartPage(totalPages - getButtonsToShow() + 1);
            setEndPage(totalPages);

        } else {
            setStartPage(currentPage - halfPagesToShow);
            setEndPage(currentPage + halfPagesToShow);
        }

    }

    public int getButtonsToShow() {
        return buttonsToShow;
    }

    public void setButtonsToShow(int buttonsToShow) {
        if (buttonsToShow % 2 != 0) {
            this.buttonsToShow = buttonsToShow;
        } else {
            throw new IllegalArgumentException("Must be an odd value!");
        }
    }

    public int getStartPage() {
        return startPage;
    }

    public void setStartPage(int startPage) {
        this.startPage = startPage;
    }

    public int getEndPage() {
        return endPage;
    }

    public void setEndPage(int endPage) {
        this.endPage = endPage;
    }

    @Override
    public String toString() {
        return "Pager [currentPage=" + startPage + ", totalPage=" + endPage + "]";
    }
}
