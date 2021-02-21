package up_info_bdda_iz_ha;

import java.nio.ByteBuffer;

import java.util.Date;

/*
 * Class Frame représente un case du bufferpool 
 * 
 * 
 * 
 * @author Aghiles_IZOUAOUEN  
 * 
 * @version 1.0 
 * 
 *
 */

public class Frame {

    private PageId pageId;
    private ByteBuffer byteBuffer;
    private int dirtyFlag;
    private int pin_Count;
    private  Date timePinCountAtZero;


    public Frame() {

        this.byteBuffer=ByteBuffer.allocate(DBParams.pageSize);
        this.pageId = null;
        this.pin_Count = 0;
        this.dirtyFlag = 0;
        this.timePinCountAtZero = null;

    }


    public PageId getPageId() {
        return this.pageId;
    }


    public void setPageId(PageId pageId) {
        this.pageId = pageId;
    }


    public ByteBuffer getByteBuffer() {
        return byteBuffer;
    }


    public void setByteBuffer(ByteBuffer byteBuffer) {
        this.byteBuffer = byteBuffer;
    }


    public int getDirtyFlag() {
        return this.dirtyFlag;
    }


    public void setDirtyFlag(int dirtyFlag) {

        this.dirtyFlag=dirtyFlag;


    }


    public int getPin_Count() {
        return pin_Count;
    }


    public void setPin_Count(int pin_Count) {
        this.pin_Count = pin_Count;
    }

    public void incrementPinCount() {
        this.pin_Count++;
    }

    public void decrementPinCount() {

        this.pin_Count--;
    }


    public Date getTimePinCountAtZero() {
        return timePinCountAtZero;
    }


    public void setTimePinCountAtZero(Date timePinCountAtZero) {
        this.timePinCountAtZero = timePinCountAtZero;
    }

}
