package com.leeef.baseproject.base.baseinterface;

import java.util.List;

//多选
public interface MultipleSelectListener<T> {

    //选中的数据
    List<T> getSelectedPositions();

    //是否是全选
    boolean isAllSelect();

}
