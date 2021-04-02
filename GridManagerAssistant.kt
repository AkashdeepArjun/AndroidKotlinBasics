package com.example.filteringapp

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class GridManagerAssistant(val margin:Int,val No_Of_Columns:Int):RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)

        val position=parent.getChildAdapterPosition(view)

        var isItemAtFirstRow=position<No_Of_Columns
        val childcount=state.itemCount
        val lastrowitemcount=getLastRowItems(childcount)
        val itemIsInBottomRow=position>=(childcount-lastrowitemcount)

        val itemAtLeftMostColumn=(position%No_Of_Columns==0)
        val itematRightMostColumn=(position%No_Of_Columns==(No_Of_Columns-1))
        val itemAtMiddleColumns=!itemAtLeftMostColumn && !itematRightMostColumn
        when(isItemAtFirstRow){
            true->{outRect.top=margin}
            else->{outRect.top=margin/2}
        }
        when(itemIsInBottomRow){
            true->{outRect.bottom=margin}
            else->{outRect.bottom=margin/2}
        }
        when(itemAtLeftMostColumn && !itematRightMostColumn && !itemAtMiddleColumns)
        {
            true->{outRect.right=margin/2
                outRect.left=margin
            }
            else->{}
        }
        when(itemAtMiddleColumns && !itemAtLeftMostColumn && !itematRightMostColumn)
        {
            true->{outRect.right=margin/2
                outRect.left=margin/2
            }
            else->{}
        }
        when(itematRightMostColumn && !itemAtLeftMostColumn && !itemAtMiddleColumns)
        {
            true->{
                outRect.left=margin/2
                outRect.right=margin

            }
        }

    }
    fun  getLastRowItems(itemscount:Int):Int{

        var lastrowItems=itemscount%No_Of_Columns
        if(lastrowItems==0){
            lastrowItems=No_Of_Columns
        }

        return lastrowItems
    }




}