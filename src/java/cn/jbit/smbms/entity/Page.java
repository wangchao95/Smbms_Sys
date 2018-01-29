package cn.jbit.smbms.entity;

import java.util.List;

/**
 * 页码类
 * @param <T>
 */
public class Page<T> {
    private int pageSize;  //每页显示的数量
    private int totalPageCount; //总页数
    private int totalCount; //总的条数
    private int currPageNo;  //当前页码
    private List<T> newPage;  //每页显示的集合

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        if(pageSize>0){
            this.pageSize = pageSize;
        }
    }
    public int getTotalPageCount() {
        return totalPageCount;
    }

    public void setTotalPageCount(int totalPageCount) {
        if (currPageNo>=totalPageCount){
            this.currPageNo=totalPageCount;
        }else{
            this.totalPageCount = totalPageCount;
        }
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        if(totalCount>=0)
        this.totalCount = totalCount;
        this.totalPageCount=this.totalCount%this.pageSize==0?(this.totalCount/this.pageSize):(this.totalCount/this.pageSize+1);
    }

    public int getCurrPageNo() {
        return currPageNo;
    }

    public void setCurrPageNo(int currPageNo) {
        if(currPageNo>=0){
            this.currPageNo = currPageNo;
        }
    }

    public List<T> getNewPage() {
        return newPage;
    }

    public void setNewPage(List<T> newPage) {
        this.newPage = newPage;
    }
}
