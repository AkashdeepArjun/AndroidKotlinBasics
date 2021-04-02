package com.example.filteringapp

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

// INPUT HOW MUCH MARGIN YOU NEED AND NO_OF_COLUMS YOU NEED
class GridManagerAssistant(val margin:Int,val No_Of_Columns:Int):RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)

        //GET POSITION OF CHILD
        val position=parent.getChildAdapterPosition(view)

        // CHECK IF ITEM IS AT FIRST ROW
        var isItemAtFirstRow=position<No_Of_Columns
        
        // GETTING CURRENT SUPPLIED ITEMS
        val childcount=state.itemCount
        
        // GETTING ITEMS IN LAST ROW
        val lastrowitemcount=getLastRowItems(childcount)
        
        //CHECKS IF ITEM IN LAST ROW
        val itemIsInBottomRow=position>=(childcount-lastrowitemcount)

        // FOR MANAGING SPACING IN FIRST COLUMMN CHECKNG IF ITEM IS IN FIRST COLUMN
        val itemAtLeftMostColumn=(position%No_Of_Columns==0)
        
        // SIMILARLY FOR CHECKING LAST COLUMN BECAUSE WE WANT TO AVOID DOUBLE MARGINS  
        val itematRightMostColumn=(position%No_Of_Columns==(No_Of_Columns-1))
        
        // CHECK IF ITEM IS AT MIDDLE COLUMNS SOME OF  MARGINS GETS HALF THERE  (DONT WORRY THERE IS STILL LONG BUTTED LOGIC TO MESS YOUR HEAD)
        val itemAtMiddleColumns=!itemAtLeftMostColumn && !itematRightMostColumn
       
        //SETTING TOP MARGIN OF ITEM
        when(isItemAtFirstRow){
            true->{outRect.top=margin}
            else->{outRect.top=margin/2}
        }
        //SETTING BOTTOM MARGIN OF ITEM
        when(itemIsInBottomRow){
            true->{outRect.bottom=margin}
            else->{outRect.bottom=margin/2}
        }
        //SETTING IF ITEM BELONGS IN FIRST COLUMN LEFT MARGIN AND RIGHT MARGIN OF ITEM 
        when(itemAtLeftMostColumn && !itematRightMostColumn && !itemAtMiddleColumns)
        {
            true->{outRect.right=margin/2
                outRect.left=margin
            }
            else->{}
        }
        //SETTING MARGIN IF ITEM IS IN MIDDLE COLUMN
        when(itemAtMiddleColumns && !itemAtLeftMostColumn && !itematRightMostColumn)
        {
            true->{outRect.right=margin/2
                outRect.left=margin/2
            }
            else->{}
        }
        //SETTING MARGINS IF ITEM IS AT RIGHT MOST POSITION
        when(itematRightMostColumn && !itemAtLeftMostColumn && !itemAtMiddleColumns)
        {
            true->{
                outRect.left=margin/2
                outRect.right=margin

            }
        }

    }
    
    // LOGIC: DIVIDE THE CURRENT ITEMS BY COLUMNS IF REMAINDER IS ZERO MEANS NUMBER OF ITEMS ARE SAME AS NUMBER COLUMNS WE WANT IN ADAPTER 
    // IF REMAINDER IS OTHER THAN ZERO ....THAN IT HAS LESSER ITEMS  ..LETS JUST SAY WE HAVE 10 CURRENT ITEMS AND AND WANT 2 COLUMNS .DIVIDE 10 BY 2 WE SEE
    //REMAINDER ZERO MEANS EVERY ROW INCLUDING LAST  MUST HAVE BEEN 2 ITEMS IF WE SUPPLY 11 ITEMS ..REMAINDER WILL BE 1 MEANS LAST ROW HAVE 1 ITEM
    fun  getLastRowItems(itemscount:Int):Int{

        var lastrowItems=itemscount%No_Of_Columns
        if(lastrowItems==0){
            lastrowItems=No_Of_Columns
        }

        return lastrowItems
    }




}
